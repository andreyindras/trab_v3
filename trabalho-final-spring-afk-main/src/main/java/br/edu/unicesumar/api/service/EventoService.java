package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.dto.EventoCadastroDTO;
import br.edu.unicesumar.api.dto.EventoDTO;
import br.edu.unicesumar.api.entity.Aluno;
import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.entity.Evento;
import br.edu.unicesumar.api.entity.Palestrante;
import br.edu.unicesumar.api.exception.BusinessException;
import br.edu.unicesumar.api.exception.ResourceNotFoundException;
import br.edu.unicesumar.api.repository.DepartamentoRepository;
import br.edu.unicesumar.api.repository.EventoRepository;
import br.edu.unicesumar.api.repository.InscricaoRepository;
import br.edu.unicesumar.api.repository.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private PalestranteRepository palestranteRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public List<EventoDTO> listarTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventoDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
        return convertToDTO(evento);
    }

    @Transactional
    public EventoDTO criar(EventoCadastroDTO dto) {
        if (dto.getIdsPalestrantes() == null || dto.getIdsPalestrantes().isEmpty()) {
            throw new BusinessException("Cada evento deve ter no mínimo um palestrante vinculado");
        }

        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + dto.getIdDepartamento()));

        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getIdsPalestrantes());
        if (palestrantes.size() != dto.getIdsPalestrantes().size()) {
            throw new ResourceNotFoundException("Alguns palestrantes não foram encontrados");
        }

        Evento evento = new Evento();
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(departamento);
        evento.setPalestrantes(palestrantes);

        evento = eventoRepository.save(evento);
        return convertToDTO(evento);
    }

    @Transactional
    public EventoDTO atualizar(Long id, EventoCadastroDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));

        if (dto.getIdsPalestrantes() == null || dto.getIdsPalestrantes().isEmpty()) {
            throw new BusinessException("Cada evento deve ter no mínimo um palestrante vinculado");
        }

        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + dto.getIdDepartamento()));

        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getIdsPalestrantes());
        if (palestrantes.size() != dto.getIdsPalestrantes().size()) {
            throw new ResourceNotFoundException("Alguns palestrantes não foram encontrados");
        }

        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(departamento);
        evento.setPalestrantes(palestrantes);

        evento = eventoRepository.save(evento);
        return convertToDTO(evento);
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));

        eventoRepository.delete(evento);
    }

    private EventoDTO convertToDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setNome(evento.getNome());
        dto.setDescricao(evento.getDescricao());
        dto.setData(evento.getData());
        dto.setLimiteParticipantes(evento.getLimiteParticipantes());
        dto.setIdDepartamento(evento.getDepartamento().getId());

        if (evento.getPalestrantes() != null) {
            dto.setIdsPalestrantes(evento.getPalestrantes().stream()
                    .map(Palestrante::getId)
                    .collect(Collectors.toList()));
        }

        Long numeroInscritos = inscricaoRepository.countInscricoesAtivasByEventoId(evento.getId());
        dto.setNumeroInscritos(numeroInscritos);
        dto.setLotado(numeroInscritos >= evento.getLimiteParticipantes());

        return dto;
    }
}
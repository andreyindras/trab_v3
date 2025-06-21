package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.dto.DepartamentoResponseDTO;
import br.edu.unicesumar.api.dto.EventoRequestDTO;
import br.edu.unicesumar.api.dto.EventoResponseDTO;
import br.edu.unicesumar.api.dto.EventoUpdateDTO;
import br.edu.unicesumar.api.dto.PalestranteResponseDTO;
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

    public List<EventoResponseDTO> listarTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
        return convertToResponseDTO(evento);
    }

    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        // Validar se tem pelo menos um palestrante
        if (dto.getIdsPalestrantes() == null || dto.getIdsPalestrantes().isEmpty()) {
            throw new BusinessException("Cada evento deve ter no mínimo um palestrante vinculado");
        }

        // Buscar departamento
        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + dto.getIdDepartamento()));

        // Buscar palestrantes
        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getIdsPalestrantes());
        if (palestrantes.size() != dto.getIdsPalestrantes().size()) {
            throw new ResourceNotFoundException("Alguns palestrantes não foram encontrados");
        }

        // Criar evento
        Evento evento = new Evento();
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(departamento);
        evento.setPalestrantes(palestrantes);

        evento = eventoRepository.save(evento);
        return convertToResponseDTO(evento);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoUpdateDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));

        // Validar se tem pelo menos um palestrante
        if (dto.getIdsPalestrantes() == null || dto.getIdsPalestrantes().isEmpty()) {
            throw new BusinessException("Cada evento deve ter no mínimo um palestrante vinculado");
        }

        // Buscar departamento
        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + dto.getIdDepartamento()));

        // Buscar palestrantes
        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getIdsPalestrantes());
        if (palestrantes.size() != dto.getIdsPalestrantes().size()) {
            throw new ResourceNotFoundException("Alguns palestrantes não foram encontrados");
        }

        // Atualizar evento
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(departamento);
        evento.setPalestrantes(palestrantes);

        evento = eventoRepository.save(evento);
        return convertToResponseDTO(evento);
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));

        eventoRepository.delete(evento);
    }

    private EventoResponseDTO convertToResponseDTO(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getId());
        dto.setNome(evento.getNome());
        dto.setDescricao(evento.getDescricao());
        dto.setData(evento.getData());
        dto.setLimiteParticipantes(evento.getLimiteParticipantes());

        // Converter departamento
        if (evento.getDepartamento() != null) {
            DepartamentoResponseDTO departamentoDTO = new DepartamentoResponseDTO();
            departamentoDTO.setId(evento.getDepartamento().getId());
            departamentoDTO.setNome(evento.getDepartamento().getNome());
            departamentoDTO.setSigla(evento.getDepartamento().getSigla());
            departamentoDTO.setResponsavel(evento.getDepartamento().getResponsavel());
            dto.setDepartamento(departamentoDTO);
        }

        // Converter palestrantes
        if (evento.getPalestrantes() != null) {
            List<PalestranteResponseDTO> palestrantesDTO = evento.getPalestrantes().stream()
                    .map(palestrante -> {
                        PalestranteResponseDTO palestranteDTO = new PalestranteResponseDTO();
                        palestranteDTO.setId(palestrante.getId());
                        palestranteDTO.setNome(palestrante.getNome());
                        palestranteDTO.setMiniCurriculo(palestrante.getMiniCurriculo());
                        palestranteDTO.setInstituicao(palestrante.getInstituicao());
                        return palestranteDTO;
                    })
                    .collect(Collectors.toList());
            dto.setPalestrantes(palestrantesDTO);
        }

        // Calcular número de inscritos e status de vagas
        Integer numeroInscritos = inscricaoRepository.countInscricoesAtivasByEventoId(evento.getId()).intValue();
        dto.setNumeroInscritos(numeroInscritos);
        
        if (numeroInscritos >= evento.getLimiteParticipantes()) {
            dto.setStatusVagas("LOTADO");
        } else {
            int vagasRestantes = evento.getLimiteParticipantes() - numeroInscritos;
            dto.setStatusVagas(vagasRestantes + " vagas disponíveis");
        }

        return dto;
    }
}
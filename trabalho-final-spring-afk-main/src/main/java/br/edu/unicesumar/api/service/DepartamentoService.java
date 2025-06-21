package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.dto.DepartamentoRelatorioDTO;
import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.exception.ResourceNotFoundException;
import br.edu.unicesumar.api.repository.DepartamentoRepository;
import br.edu.unicesumar.api.repository.EventoRepository;
import br.edu.unicesumar.api.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
    }

    @Transactional
    public Departamento criar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Transactional
    public Departamento atualizar(Long id, Departamento departamento) {
        Departamento departamentoExistente = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        departamentoExistente.setNome(departamento.getNome());
        departamentoExistente.setSigla(departamento.getSigla());
        departamentoExistente.setResponsavel(departamento.getResponsavel());

        return departamentoRepository.save(departamentoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        departamentoRepository.delete(departamento);
    }

    public DepartamentoRelatorioDTO gerarRelatorio(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        DepartamentoRelatorioDTO relatorio = new DepartamentoRelatorioDTO();
        relatorio.setId(departamento.getId());
        relatorio.setNome(departamento.getNome());
        relatorio.setSigla(departamento.getSigla());
        relatorio.setResponsavel(departamento.getResponsavel());

        // Contar total de eventos do departamento
        Long totalEventos = (long) eventoRepository.findByDepartamentoId(id).size();
        relatorio.setTotalEventos(totalEventos);

        // Contar total de inscrições ativas nos eventos do departamento
        Long totalInscritos = inscricaoRepository.countInscricoesByDepartamentoId(id);
        relatorio.setTotalInscritos(totalInscritos);

        return relatorio;
    }
}
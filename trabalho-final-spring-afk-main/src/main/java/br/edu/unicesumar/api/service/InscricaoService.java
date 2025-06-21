package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.entity.Aluno;
import br.edu.unicesumar.api.entity.Evento;
import br.edu.unicesumar.api.entity.Inscricao;
import br.edu.unicesumar.api.enums.StatusInscricao;
import br.edu.unicesumar.api.exception.BusinessException;
import br.edu.unicesumar.api.exception.ResourceNotFoundException;
import br.edu.unicesumar.api.repository.AlunoRepository;
import br.edu.unicesumar.api.repository.EventoRepository;
import br.edu.unicesumar.api.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public void inscreverAluno(Long eventoId, Long alunoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + eventoId));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + alunoId));

        if (evento.getData().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é possível se inscrever em eventos com data já passada");
        }

        List<Inscricao> inscricaoExistente = inscricaoRepository.findInscricaoAtivaByAlunoAndEvento(alunoId, eventoId);
        if (!inscricaoExistente.isEmpty()) {
            throw new BusinessException("O aluno já está inscrito neste evento");
        }

        Long inscricoesAtivas = inscricaoRepository.countInscricoesAtivasByEventoId(eventoId);
        if (inscricoesAtivas >= evento.getLimiteParticipantes()) {
            throw new BusinessException("Não há vagas disponíveis para este evento");
        }

        LocalDateTime inicioEvento = evento.getData();
        LocalDateTime fimEvento = evento.getData().plusHours(3);

        List<Inscricao> inscricoesConflitantes = inscricaoRepository.findInscricoesAtivasNoIntervalo(
                alunoId, inicioEvento, fimEvento);

        if (!inscricoesConflitantes.isEmpty()) {
            throw new BusinessException("O aluno já possui inscrição em evento com horário conflitante");
        }

        Inscricao inscricao = new Inscricao(evento, aluno, LocalDateTime.now());
        inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarInscricoesPorAluno(Long alunoId) {
        alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + alunoId));

        return inscricaoRepository.findInscricoesAtivasByAlunoId(alunoId);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada com ID: " + inscricaoId));

        if (inscricao.getStatus() == StatusInscricao.CANCELADA) {
            throw new BusinessException("Esta inscrição já foi cancelada");
        }

        inscricao.setStatus(StatusInscricao.CANCELADA);
        inscricaoRepository.save(inscricao);
    }
}
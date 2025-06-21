package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.entity.Aluno;
import br.edu.unicesumar.api.exception.BusinessException;
import br.edu.unicesumar.api.exception.ResourceNotFoundException;
import br.edu.unicesumar.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));
    }

    @Transactional
    public Aluno criar(Aluno aluno) {

        if (alunoRepository.existsByMatricula(aluno.getMatricula())) {
            throw new BusinessException("Já existe um aluno cadastrado com esta matrícula: " + aluno.getMatricula());
        }

        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno atualizar(Long id, Aluno aluno) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));

        if (alunoRepository.existsMatriculaExcludingId(aluno.getMatricula(), id)) {
            throw new BusinessException("Já existe outro aluno cadastrado com esta matrícula: " + aluno.getMatricula());
        }

        alunoExistente.setNome(aluno.getNome());
        alunoExistente.setMatricula(aluno.getMatricula());
        alunoExistente.setCurso(aluno.getCurso());

        return alunoRepository.save(alunoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));

        alunoRepository.delete(aluno);
    }
}
package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.InscricaoCadastroDTO;
import br.edu.unicesumar.api.entity.Aluno;
import br.edu.unicesumar.api.entity.Inscricao;
import br.edu.unicesumar.api.service.AlunoService;
import br.edu.unicesumar.api.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.criar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idAluno}/registrations")
    public ResponseEntity<List<InscricaoCadastroDTO>> listarInscricoes(@PathVariable Long idAluno) {
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorAluno(idAluno);
        
        List<InscricaoCadastroDTO> inscricoesDTO = inscricoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(inscricoesDTO);
    }

    private InscricaoCadastroDTO convertToDTO(Inscricao inscricao) {
        InscricaoCadastroDTO dto = new InscricaoCadastroDTO();
        dto.setIdAluno(inscricao.getAluno().getId());
        dto.setEventoId(inscricao.getEvento().getId());
        dto.setEventoNome(inscricao.getEvento().getNome());
        dto.setEventoDescricao(inscricao.getEvento().getDescricao());
        dto.setEventoData(inscricao.getEvento().getData());
        dto.setDepartamentoNome(inscricao.getEvento().getDepartamento().getNome());
        dto.setDataInscricao(inscricao.getDataInscricao());
        dto.setStatus(inscricao.getStatus());
        return dto;
    }
}
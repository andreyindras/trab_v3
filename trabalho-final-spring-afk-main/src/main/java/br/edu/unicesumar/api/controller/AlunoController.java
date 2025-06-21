package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.AlunoRequestDTO;
import br.edu.unicesumar.api.dto.AlunoResponseDTO;
import br.edu.unicesumar.api.dto.AlunoUpdateDTO;
import br.edu.unicesumar.api.dto.EventoResponseDTO;
import br.edu.unicesumar.api.dto.InscricaoResponseDTO;
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
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos() {
        List<Aluno> alunos = alunoService.listarTodos();
        List<AlunoResponseDTO> alunosDTO = alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarAluno(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        AlunoResponseDTO alunoDTO = convertToResponseDTO(aluno);
        return ResponseEntity.ok(alunoDTO);
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criarAluno(@RequestBody AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = convertToEntity(alunoRequestDTO);
        Aluno novoAluno = alunoService.criar(aluno);
        AlunoResponseDTO responseDTO = convertToResponseDTO(novoAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(@PathVariable Long id, 
                                                          @RequestBody AlunoUpdateDTO alunoUpdateDTO) {
        Aluno aluno = convertToEntity(alunoUpdateDTO);
        Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
        AlunoResponseDTO responseDTO = convertToResponseDTO(alunoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idAluno}/registrations")
    public ResponseEntity<List<InscricaoResponseDTO>> listarInscricoes(@PathVariable Long idAluno) {
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorAluno(idAluno);
        List<InscricaoResponseDTO> inscricoesDTO = inscricoes.stream()
                .map(this::convertInscricaoToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscricoesDTO);
    }

    // Métodos de conversão
    private AlunoResponseDTO convertToResponseDTO(Aluno aluno) {
        AlunoResponseDTO dto = new AlunoResponseDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setMatricula(aluno.getMatricula());
        dto.setCurso(aluno.getCurso());
        return dto;
    }

    private Aluno convertToEntity(AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setMatricula(dto.getMatricula());
        aluno.setCurso(dto.getCurso());
        return aluno;
    }

    private Aluno convertToEntity(AlunoUpdateDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setMatricula(dto.getMatricula());
        aluno.setCurso(dto.getCurso());
        return aluno;
    }

    private InscricaoResponseDTO convertInscricaoToDTO(Inscricao inscricao) {
        InscricaoResponseDTO dto = new InscricaoResponseDTO();
        dto.setId(inscricao.getId());
        dto.setDataInscricao(inscricao.getDataInscricao());
        dto.setStatus(inscricao.getStatus());
        
        // Converter evento para EventoResponseDTO
        EventoResponseDTO eventoDTO = new EventoResponseDTO();
        eventoDTO.setId(inscricao.getEvento().getId());
        eventoDTO.setNome(inscricao.getEvento().getNome());
        eventoDTO.setDescricao(inscricao.getEvento().getDescricao());
        eventoDTO.setData(inscricao.getEvento().getData());
        eventoDTO.setLimiteParticipantes(inscricao.getEvento().getLimiteParticipantes());
        dto.setEvento(eventoDTO);
        
        // Converter aluno para AlunoResponseDTO
        dto.setAluno(convertToResponseDTO(inscricao.getAluno()));
        
        return dto;
    }
}
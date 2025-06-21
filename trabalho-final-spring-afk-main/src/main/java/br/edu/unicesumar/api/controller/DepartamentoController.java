package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.DepartamentoRequestDTO;
import br.edu.unicesumar.api.dto.DepartamentoResponseDTO;
import br.edu.unicesumar.api.dto.DepartamentoUpdateDTO;
import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> listarDepartamentos() {
        List<Departamento> departamentos = departamentoService.listarTodos();
        List<DepartamentoResponseDTO> departamentosDTO = departamentos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departamentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> buscarDepartamento(@PathVariable Long id) {
        Departamento departamento = departamentoService.buscarPorId(id);
        DepartamentoResponseDTO departamentoDTO = convertToResponseDTO(departamento);
        return ResponseEntity.ok(departamentoDTO);
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> criarDepartamento(@RequestBody DepartamentoRequestDTO departamentoRequestDTO) {
        Departamento departamento = convertToEntity(departamentoRequestDTO);
        Departamento novoDepartamento = departamentoService.criar(departamento);
        DepartamentoResponseDTO responseDTO = convertToResponseDTO(novoDepartamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> atualizarDepartamento(@PathVariable Long id, 
                                                                       @RequestBody DepartamentoUpdateDTO departamentoUpdateDTO) {
        Departamento departamento = convertToEntity(departamentoUpdateDTO);
        Departamento departamentoAtualizado = departamentoService.atualizar(id, departamento);
        DepartamentoResponseDTO responseDTO = convertToResponseDTO(departamentoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable Long id) {
        departamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos de conversão
    private DepartamentoResponseDTO convertToResponseDTO(Departamento departamento) {
        DepartamentoResponseDTO dto = new DepartamentoResponseDTO();
        dto.setId(departamento.getId());
        dto.setNome(departamento.getNome());
        dto.setSigla(departamento.getSigla());
        dto.setResponsavel(departamento.getResponsavel());
        return dto;
    }

    private Departamento convertToEntity(DepartamentoRequestDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setNome(dto.getNome());
        departamento.setSigla(dto.getSigla());
        departamento.setResponsavel(dto.getResponsavel());
        return departamento;
    }

    private Departamento convertToEntity(DepartamentoUpdateDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setNome(dto.getNome());
        departamento.setSigla(dto.getSigla());
        departamento.setResponsavel(dto.getResponsavel());
        return departamento;
    }
}
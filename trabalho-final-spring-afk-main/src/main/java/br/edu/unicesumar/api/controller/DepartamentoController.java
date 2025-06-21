package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.DepartamentoDTO;
import br.edu.unicesumar.api.dto.DepartamentoRelatorioDTO;
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
    public ResponseEntity<List<DepartamentoDTO>> listarDepartamentos() {
        List<Departamento> departamentos = departamentoService.listarTodos();
        List<DepartamentoDTO> departamentosDTO = departamentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departamentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> buscarDepartamento(@PathVariable Long id) {
        Departamento departamento = departamentoService.buscarPorId(id);
        DepartamentoDTO departamentoDTO = convertToDTO(departamento);
        return ResponseEntity.ok(departamentoDTO);
    }

    @PostMapping
    public ResponseEntity<DepartamentoDTO> criarDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        Departamento departamento = convertToEntity(departamentoDTO);
        Departamento novoDepartamento = departamentoService.criar(departamento);
        DepartamentoDTO novoDTO = convertToDTO(novoDepartamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> atualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        Departamento departamento = convertToEntity(departamentoDTO);
        Departamento departamentoAtualizado = departamentoService.atualizar(id, departamento);
        DepartamentoDTO dtoAtualizado = convertToDTO(departamentoAtualizado);
        return ResponseEntity.ok(dtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable Long id) {
        departamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<DepartamentoRelatorioDTO> gerarRelatorio(@PathVariable Long id) {
        DepartamentoRelatorioDTO relatorio = departamentoService.gerarRelatorio(id);
        return ResponseEntity.ok(relatorio);
    }

    private DepartamentoDTO convertToDTO(Departamento departamento) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(departamento.getId());
        dto.setNome(departamento.getNome());
        dto.setSigla(departamento.getSigla());
        dto.setResponsavel(departamento.getResponsavel());
        return dto;
    }

    private Departamento convertToEntity(DepartamentoDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setId(dto.getId());
        departamento.setNome(dto.getNome());
        departamento.setSigla(dto.getSigla());
        departamento.setResponsavel(dto.getResponsavel());
        return departamento;
    }
}
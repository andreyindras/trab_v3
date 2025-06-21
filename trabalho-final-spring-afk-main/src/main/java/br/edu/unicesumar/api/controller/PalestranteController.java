package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.PalestranteRequestDTO;
import br.edu.unicesumar.api.dto.PalestranteResponseDTO;
import br.edu.unicesumar.api.dto.PalestranteUpdateDTO;
import br.edu.unicesumar.api.entity.Palestrante;
import br.edu.unicesumar.api.service.PalestranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/speakers")
public class PalestranteController {

    @Autowired
    private PalestranteService palestranteService;

    @GetMapping
    public ResponseEntity<List<PalestranteResponseDTO>> listarPalestrantes() {
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        List<PalestranteResponseDTO> palestrantesDTO = palestrantes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(palestrantesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalestranteResponseDTO> buscarPalestrante(@PathVariable Long id) {
        Palestrante palestrante = palestranteService.buscarPorId(id);
        PalestranteResponseDTO palestranteDTO = convertToResponseDTO(palestrante);
        return ResponseEntity.ok(palestranteDTO);
    }

    @PostMapping
    public ResponseEntity<PalestranteResponseDTO> criarPalestrante(@RequestBody PalestranteRequestDTO palestranteRequestDTO) {
        Palestrante palestrante = convertToEntity(palestranteRequestDTO);
        Palestrante novoPalestrante = palestranteService.criar(palestrante);
        PalestranteResponseDTO responseDTO = convertToResponseDTO(novoPalestrante);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PalestranteResponseDTO> atualizarPalestrante(@PathVariable Long id, 
                                                                     @RequestBody PalestranteUpdateDTO palestranteUpdateDTO) {
        Palestrante palestrante = convertToEntity(palestranteUpdateDTO);
        Palestrante palestranteAtualizado = palestranteService.atualizar(id, palestrante);
        PalestranteResponseDTO responseDTO = convertToResponseDTO(palestranteAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPalestrante(@PathVariable Long id) {
        palestranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos de conversão
    private PalestranteResponseDTO convertToResponseDTO(Palestrante palestrante) {
        PalestranteResponseDTO dto = new PalestranteResponseDTO();
        dto.setId(palestrante.getId());
        dto.setNome(palestrante.getNome());
        dto.setMiniCurriculo(palestrante.getMiniCurriculo());
        dto.setInstituicao(palestrante.getInstituicao());
        return dto;
    }

    private Palestrante convertToEntity(PalestranteRequestDTO dto) {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome(dto.getNome());
        palestrante.setMiniCurriculo(dto.getMiniCurriculo());
        palestrante.setInstituicao(dto.getInstituicao());
        return palestrante;
    }

    private Palestrante convertToEntity(PalestranteUpdateDTO dto) {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome(dto.getNome());
        palestrante.setMiniCurriculo(dto.getMiniCurriculo());
        palestrante.setInstituicao(dto.getInstituicao());
        return palestrante;
    }
}
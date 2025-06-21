package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.entity.Palestrante;
import br.edu.unicesumar.api.service.PalestranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
public class PalestranteController {

    @Autowired
    private PalestranteService palestranteService;

    @GetMapping
    public ResponseEntity<List<Palestrante>> listarPalestrantes() {
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        return ResponseEntity.ok(palestrantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Palestrante> buscarPalestrante(@PathVariable Long id) {
        Palestrante palestrante = palestranteService.buscarPorId(id);
        return ResponseEntity.ok(palestrante);
    }

    @PostMapping
    public ResponseEntity<Palestrante> criarPalestrante(@RequestBody Palestrante palestrante) {
        Palestrante novoPalestrante = palestranteService.criar(palestrante);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPalestrante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Palestrante> atualizarPalestrante(@PathVariable Long id, @RequestBody Palestrante palestrante) {
        Palestrante palestranteAtualizado = palestranteService.atualizar(id, palestrante);
        return ResponseEntity.ok(palestranteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPalestrante(@PathVariable Long id) {
        palestranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
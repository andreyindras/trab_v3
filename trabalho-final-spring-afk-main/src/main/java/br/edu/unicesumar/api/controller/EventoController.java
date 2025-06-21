package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.EventoCadastroDTO;
import br.edu.unicesumar.api.dto.EventoDTO;
import br.edu.unicesumar.api.dto.InscricaoCadastroDTO;
import br.edu.unicesumar.api.service.EventoService;
import br.edu.unicesumar.api.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarEvento(@PathVariable Long id) {
        EventoDTO evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoDTO> criarEvento(@RequestBody EventoCadastroDTO dto) {
        EventoDTO evento = eventoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoCadastroDTO dto) {
        EventoDTO evento = eventoService.atualizar(id, dto);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvento}/registrations")
    public ResponseEntity<Void> inscreverAluno(@PathVariable Long idEvento, @RequestBody InscricaoCadastroDTO dto) {
        inscricaoService.inscreverAluno(idEvento, dto.getIdAluno());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
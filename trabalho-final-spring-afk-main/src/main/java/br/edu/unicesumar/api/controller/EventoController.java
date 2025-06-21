package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.EventoRequestDTO;
import br.edu.unicesumar.api.dto.EventoResponseDTO;
import br.edu.unicesumar.api.dto.EventoUpdateDTO;
import br.edu.unicesumar.api.dto.InscricaoRequestDTO;
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
    public ResponseEntity<List<EventoResponseDTO>> listarEventos() {
        List<EventoResponseDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEvento(@PathVariable Long id) {
        EventoResponseDTO evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(@RequestBody EventoRequestDTO eventoRequestDTO) {
        EventoResponseDTO evento = eventoService.criar(eventoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, 
                                                           @RequestBody EventoUpdateDTO eventoUpdateDTO) {
        EventoResponseDTO evento = eventoService.atualizar(id, eventoUpdateDTO);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvento}/registrations")
    public ResponseEntity<Void> inscreverAluno(@PathVariable Long idEvento, 
                                            @RequestBody InscricaoRequestDTO inscricaoRequestDTO) {
        inscricaoService.inscreverAluno(idEvento, inscricaoRequestDTO.getIdAluno());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
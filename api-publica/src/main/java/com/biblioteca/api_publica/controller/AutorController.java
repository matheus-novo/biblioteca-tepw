package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.AutorDTO;
import com.biblioteca.api_publica.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@Tag(name = "Autores", description = "Gerenciamento de autores da biblioteca")
public class AutorController {

    @Autowired
    private AutorService service;

    @PostMapping
    @Operation(summary = "Cadastra um novo autor")
    public ResponseEntity<AutorDTO> create(@Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todos os autores")
    public ResponseEntity<List<AutorDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um autor pelo ID")
    public ResponseEntity<AutorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um autor")
    public ResponseEntity<AutorDTO> update(@PathVariable Long id, @Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um autor do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ranking")
    @Operation(summary = "Ranking de autores baseado na satisfação dos leitores")
    public ResponseEntity<List<AutorDTO>> getRanking() {
        return ResponseEntity.ok(service.getRankingAutores());
    }
}
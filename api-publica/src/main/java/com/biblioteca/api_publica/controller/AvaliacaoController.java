package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.AvaliacaoDTO;
import com.biblioteca.api_publica.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avaliacoes")
@Tag(name = "Avaliações", description = "CRUD de feedback dos alunos sobre o acervo")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @PostMapping
    @Operation(summary = "Cria ou atualiza uma avaliação (Upsert)")
    public ResponseEntity<AvaliacaoDTO> save(@Valid @RequestBody AvaliacaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as avaliações")
    public ResponseEntity<List<AvaliacaoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma avaliação específica")
    public ResponseEntity<AvaliacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza manualmente uma avaliação")
    public ResponseEntity<AvaliacaoDTO> update(@PathVariable Long id, @Valid @RequestBody AvaliacaoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma avaliação")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
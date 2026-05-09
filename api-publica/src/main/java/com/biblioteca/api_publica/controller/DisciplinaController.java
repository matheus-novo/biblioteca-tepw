package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.DisciplinaDTO;
import com.biblioteca.api_publica.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disciplinas")
@Tag(name = "Disciplinas", description = "Endpoints para gestão de disciplinas e recomendações")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova disciplina")
    public ResponseEntity<DisciplinaDTO> create(@Valid @RequestBody DisciplinaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca disciplina por ID")
    public ResponseEntity<DisciplinaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma disciplina")
    public ResponseEntity<DisciplinaDTO> update(@PathVariable Long id, @Valid @RequestBody DisciplinaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma disciplina")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
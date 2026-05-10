package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.AlunoDTO;
import com.biblioteca.api_publica.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.api_publica.domain.dto.LivroDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alunos")
@Tag(name = "Alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @PostMapping
    @Operation(summary = "Cadastra um novo aluno")
    public ResponseEntity<AlunoDTO> create(@Valid @RequestBody AlunoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Busca todos os alunos")
    public ResponseEntity<List<AlunoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um aluno pelo Id")
    public ResponseEntity<AlunoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno")
    public ResponseEntity<AlunoDTO> update(@PathVariable Long id, @Valid @RequestBody AlunoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recomendacoes")
    @Operation(summary = "Gera uma lista de leitura baseada nas disciplinas matriculadas do aluno")
    @ApiResponse(responseCode = "200", description = "Lista de recomendações gerada com sucesso")
    public ResponseEntity<List<LivroDTO>> getRecomendacoes(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRecomendacoes(id));
    }
}
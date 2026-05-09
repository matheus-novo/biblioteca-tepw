package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.LivroDTO;
import com.biblioteca.api_publica.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros") 
@Tag(name = "Livros", description = "Gestão do acervo da biblioteca digital")
public class LivroController {

    @Autowired
    private LivroService service;

    @Operation(summary = "Cadastra um novo livro digital", description = "Requer EditoraID e uma lista de AutorIDs")
    @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso")
    @PostMapping
    public ResponseEntity<LivroDTO> create(@Valid @RequestBody LivroDTO dto) {
        // Recebimento de DTO via Body
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Lista todos os livros do acervo")
    @GetMapping
    public ResponseEntity<List<LivroDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Busca um livro pelo ID")
    @ApiResponse(responseCode = "200", description = "Livro localizado")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualiza os dados de um livro")
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
        // O Service cuidará de verificar se o ID existe antes de editar
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Remove um livro do acervo")
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Funcionalidade Extra: Busca por palavra-chave no resumo")
    @GetMapping("/pesquisa")
    public ResponseEntity<List<LivroDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchByResumo(keyword));
    }
}
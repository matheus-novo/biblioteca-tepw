package com.biblioteca.api_publica.controller;

import com.biblioteca.api_publica.domain.dto.EditoraDTO;
import com.biblioteca.api_publica.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/v1/editoras")
@Tag(name = "Editoras", description = "Endpoints para gestão de editoras")
public class EditoraController {

    @Autowired
    private EditoraService service; 

    @PostMapping
    @Operation(summary = "Cadastra uma nova editora") 
    public ResponseEntity<EditoraDTO> create(@Valid @RequestBody EditoraDTO dto) { 
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as editoras")
    public ResponseEntity<List<EditoraDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca editora por ID")
    public ResponseEntity<EditoraDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma editora")
    public ResponseEntity<EditoraDTO> update(@PathVariable Long id, @Valid @RequestBody EditoraDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma editora")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
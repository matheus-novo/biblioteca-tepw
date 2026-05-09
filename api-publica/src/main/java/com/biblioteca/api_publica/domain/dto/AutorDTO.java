package com.biblioteca.api_publica.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AutorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome do autor é obrigatório")
    private String nome;

    private String titulacao;
    private String biografia;
    private String nacionalidade;
}
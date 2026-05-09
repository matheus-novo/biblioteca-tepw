package com.biblioteca.api_publica.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditoraDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da editora é obrigatório")
    private String nome; 

    private String cnpj; 
    private String cidade;
    private String emailContato;
    private String siteOficial;
}
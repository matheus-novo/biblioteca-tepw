package com.biblioteca.api_publica.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class DisciplinaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da disciplina é obrigatório")
    private String nome;

    @NotBlank(message = "O código da disciplina é obrigatório")
    private String codigo;

    private String curso;
    private Integer semestre;
    private String ementa;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> livroIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> nomesLivrosRecomendados;
}
package com.biblioteca.api_publica.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AlunoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A matrícula é obrigatória")
    private String matricula;

    private String curso;
    private String email;
    private LocalDate dataIngresso;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> disciplinaIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> nomesDisciplinas;
}
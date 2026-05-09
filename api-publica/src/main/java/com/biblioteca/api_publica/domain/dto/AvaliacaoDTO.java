package com.biblioteca.api_publica.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AvaliacaoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "A nota é obrigatória")
    @Min(0) @Max(10)
    private Integer nota;

    private String comentario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dataAvaliacao;

    private Boolean recomenda;

    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "O ID do livro é obrigatório")
    private Long livroId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "O ID do aluno é obrigatório")
    private Long alunoId;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String livroTitulo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String alunoNome;
}
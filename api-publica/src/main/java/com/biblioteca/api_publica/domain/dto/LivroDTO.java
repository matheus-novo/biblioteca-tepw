package com.biblioteca.api_publica.domain.dto;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String isbn;
    private String resumo;
    private Integer anoPublicacao;
    private String urlDownload;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long editoraId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> autorIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nomeEditora;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> nomesAutores;
}

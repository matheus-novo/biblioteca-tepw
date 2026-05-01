package com.biblioteca.api_publica.domain.dto;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // Campos para IDs (usados no cadastro/Request)
    private Long editoraId;
    private List<Long> autorIds;

    // Campos para nomes (usados na Resposta/Response)
    private String nomeEditora;
    private List<String> nomesAutores;
}

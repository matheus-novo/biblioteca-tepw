package com.biblioteca.api_publica.domain.model;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    public enum AreaConhecimento {
        TECNOLOGIA, BIOLOGIA, DIREITO, FILOSOFIA, ENGENHARIA, SAUDE, AGRONOMIA, MATEMATICA, LINGUAGENS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String isbn;
    private String titulo;
    private String resumo;
    private Integer anoPublicacao;

    @Enumerated(EnumType.STRING)
    private AreaConhecimento area;

    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;
}

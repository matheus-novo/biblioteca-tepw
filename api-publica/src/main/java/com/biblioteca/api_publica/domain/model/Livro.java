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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String resumo;

    private Integer anoPublicacao;

    @Column(length = 255)
    private String urlDownload;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @OneToMany(mappedBy = "livro", fetch = FetchType.EAGER)
    private List<Avaliacao> avaliacoes;

    @ManyToMany
    @JoinTable(name = "livro_autor", joinColumns = @JoinColumn(name = "livro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores;

    @ManyToMany(mappedBy = "livrosRecomendados")
    private List<Disciplina> disciplinas;
}

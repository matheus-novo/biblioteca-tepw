package com.biblioteca.api_publica.domain.model;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disciplinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(unique = true, length = 50)
    private String codigo;

    @Column(length = 100)
    private String curso;

    private Integer semestre;

    @Column(columnDefinition = "TEXT")
    private String ementa;

    @ManyToMany
    @JoinTable(
        name = "disciplina_livro",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livrosRecomendados;
}

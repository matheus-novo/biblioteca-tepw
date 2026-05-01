package com.biblioteca.api_publica.domain.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avaliacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer nota;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_avaliacao", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate dataAvaliacao;

    private Boolean recomenda;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
}
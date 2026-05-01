package com.biblioteca.api_publica.domain.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "editoras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(unique = true, length = 20)
    private String cnpj;

    @Column(length = 100)
    private String cidade;

    @Column(length = 150)
    private String emailContato;

    @Column(length = 255)
    private String siteOficial;
}
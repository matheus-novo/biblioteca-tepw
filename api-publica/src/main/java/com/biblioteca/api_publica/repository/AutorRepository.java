package com.biblioteca.api_publica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a " +
            "JOIN a.livros l " +
            "JOIN l.avaliacoes av " +
            "GROUP BY a.id " +
            "ORDER BY AVG(av.nota) DESC")
    List<Autor> findTopRatedAuthors();
}

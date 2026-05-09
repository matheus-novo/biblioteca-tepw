package com.biblioteca.api_publica.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Livro;
import org.springframework.data.domain.Pageable;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.resumo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Livro> searchByResumo(String keyword);

    @Query("SELECT l FROM Livro l JOIN l.avaliacoes a GROUP BY l.id ORDER BY AVG(a.nota) DESC")
    List<Livro> findTopRatedBooks(Pageable pageable);
}

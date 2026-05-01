package com.biblioteca.api_publica.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.resumo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Livro> searchByResumo(String keyword);
    
    @Query("SELECT l FROM Livro l JOIN Acesso a ON a.livro.id = l.id GROUP BY l.id ORDER BY COUNT(a.id) DESC")
    List<Livro> findTop5MostAccessed();
}

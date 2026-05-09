package com.biblioteca.api_publica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Avaliacao;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Busca a avaliação existente para o par Aluno/Livro
    Optional<Avaliacao> findByAlunoIdAndLivroId(Long alunoId, Long livroId);
    
    // Lista todas as avaliações de um livro específico
    List<Avaliacao> findByLivroId(Long livroId);
}
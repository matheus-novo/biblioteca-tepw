package com.biblioteca.api_publica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Aluno;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
}
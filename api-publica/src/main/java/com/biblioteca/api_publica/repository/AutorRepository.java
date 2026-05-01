package com.biblioteca.api_publica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.api_publica.domain.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long>{

}

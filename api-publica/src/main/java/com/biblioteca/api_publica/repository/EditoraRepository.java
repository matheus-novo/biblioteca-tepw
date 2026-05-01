package com.biblioteca.api_publica.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.api_publica.domain.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {


}

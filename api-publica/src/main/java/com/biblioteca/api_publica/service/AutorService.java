package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.AutorDTO;
import com.biblioteca.api_publica.domain.model.Autor;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.AutorRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository repository;

    public AutorDTO create(AutorDTO dto) {
        Autor model = MapperUtil.parseObject(dto, Autor.class);
        return MapperUtil.parseObject(repository.save(model), AutorDTO.class);
    }

    public List<AutorDTO> findAll() {
        List<Autor> autores = repository.findAll();
        return MapperUtil.parseListObjects(autores, AutorDTO.class);
    }

    public AutorDTO findById(Long id) {
        Autor model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "autor.notfound", "Autor não encontrado"));
        return MapperUtil.parseObject(model, AutorDTO.class);
    }

    public AutorDTO update(Long id, AutorDTO dto) {
        // Busca o registro existente
        Autor modelExistente = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "autor.notfound",
                        "Autor não encontrado para atualização"));

        // Atualiza os dados (Mantendo o ID original do banco)
        modelExistente.setNome(dto.getNome());
        modelExistente.setTitulacao(dto.getTitulacao());
        modelExistente.setBiografia(dto.getBiografia());
        modelExistente.setNacionalidade(dto.getNacionalidade());

        // O save aqui faz o "merge" no banco
        return MapperUtil.parseObject(repository.save(modelExistente), AutorDTO.class);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "autor.notfound", "Não é possível excluir: Autor inexistente");
        }
        repository.deleteById(id);
    }

    public List<AutorDTO> getRankingAutores() {
        List<Autor> autores = repository.findTopRatedAuthors();
        return MapperUtil.parseListObjects(autores, AutorDTO.class);
    }
}
package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.EditoraDTO;
import com.biblioteca.api_publica.domain.model.Editora;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.EditoraRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EditoraService { 

    @Autowired
    private EditoraRepository repository; 

    public EditoraDTO create(EditoraDTO dto) {
        Editora model = MapperUtil.parseObject(dto, Editora.class); 
        return MapperUtil.parseObject(repository.save(model), EditoraDTO.class); 
    }

    public List<EditoraDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), EditoraDTO.class); 
    }

    public EditoraDTO findById(Long id) {
        Editora model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "editora.notfound", "Editora não encontrada")); 
        return MapperUtil.parseObject(model, EditoraDTO.class);
    }

    public EditoraDTO update(Long id, EditoraDTO dto) {
        Editora modelExistente = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "editora.notfound", "Editora inexistente"));

        modelExistente.setNome(dto.getNome());
        modelExistente.setCnpj(dto.getCnpj());
        modelExistente.setCidade(dto.getCidade());
        modelExistente.setEmailContato(dto.getEmailContato());
        modelExistente.setSiteOficial(dto.getSiteOficial());

        return MapperUtil.parseObject(repository.save(modelExistente), EditoraDTO.class);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "editora.notfound", "Editora não encontrada para exclusão");
        }
        repository.deleteById(id);
    }
}
package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.DisciplinaDTO;
import com.biblioteca.api_publica.domain.model.Disciplina;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.DisciplinaRepository;
import com.biblioteca.api_publica.repository.LivroRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    public DisciplinaDTO create(DisciplinaDTO dto) {
        if (repository.existsByCodigo(dto.getCodigo())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "disciplina.codigo.duplicado", "Já existe uma disciplina com este código");
        }

        Disciplina model = MapperUtil.parseObject(dto, Disciplina.class);
        
        // Associa os livros recomendados se houver IDs no DTO
        if (dto.getLivroIds() != null) {
            model.setLivrosRecomendados(livroRepository.findAllById(dto.getLivroIds()));
        }

        return MapperUtil.parseObject(repository.save(model), DisciplinaDTO.class);
    }

    public List<DisciplinaDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), DisciplinaDTO.class);
    }

    public DisciplinaDTO findById(Long id) {
        Disciplina model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "disciplina.notfound", "Disciplina não encontrada"));
        return MapperUtil.parseObject(model, DisciplinaDTO.class);
    }

    public DisciplinaDTO update(Long id, DisciplinaDTO dto) {
        Disciplina modelExistente = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "disciplina.notfound", "Disciplina não encontrada"));

        modelExistente.setNome(dto.getNome());
        modelExistente.setCodigo(dto.getCodigo());
        modelExistente.setCurso(dto.getCurso());
        modelExistente.setSemestre(dto.getSemestre());
        modelExistente.setEmenta(dto.getEmenta());

        if (dto.getLivroIds() != null) {
            modelExistente.setLivrosRecomendados(livroRepository.findAllById(dto.getLivroIds()));
        }

        return MapperUtil.parseObject(repository.save(modelExistente), DisciplinaDTO.class);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "disciplina.notfound", "Não foi possível excluir a disciplina");
        }
        repository.deleteById(id);
    }
}
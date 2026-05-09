package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.AlunoDTO;
import com.biblioteca.api_publica.domain.model.Aluno;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.AlunoRepository;
import com.biblioteca.api_publica.domain.model.Livro;
import com.biblioteca.api_publica.domain.dto.LivroDTO;
import com.biblioteca.api_publica.repository.DisciplinaRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public AlunoDTO create(AlunoDTO dto) {
        Aluno model = MapperUtil.parseObject(dto, Aluno.class);

        if (dto.getDisciplinaIds() != null) {
            model.setDisciplinas(disciplinaRepository.findAllById(dto.getDisciplinaIds()));
        }

        return MapperUtil.parseObject(repository.save(model), AlunoDTO.class);
    }

    public List<AlunoDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), AlunoDTO.class);
    }

    public AlunoDTO findById(Long id) {
        Aluno model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não encontrado"));
        return MapperUtil.parseObject(model, AlunoDTO.class);
    }

    public AlunoDTO update(Long id, AlunoDTO dto) {
        Aluno existente = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não encontrado"));

        existente.setNome(dto.getNome());
        existente.setCurso(dto.getCurso());
        existente.setEmail(dto.getEmail());

        if (dto.getDisciplinaIds() != null) {
            existente.setDisciplinas(disciplinaRepository.findAllById(dto.getDisciplinaIds()));
        }

        return MapperUtil.parseObject(repository.save(existente), AlunoDTO.class);
    }

    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "ID inválido");
        repository.deleteById(id);
    }

    public List<LivroDTO> getRecomendacoes(Long alunoId) {
        Aluno aluno = repository.findById(alunoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não encontrado"));

        // 2. Navega: Aluno -> Disciplinas -> Livros Recomendados
        List<Livro> livrosRecomendados = aluno.getDisciplinas().stream()
                .flatMap(disciplina -> disciplina.getLivrosRecomendados().stream())
                .distinct()
                .toList();

        // 3. Converte para DTO
        return MapperUtil.parseListObjects(livrosRecomendados, LivroDTO.class);
    }
}
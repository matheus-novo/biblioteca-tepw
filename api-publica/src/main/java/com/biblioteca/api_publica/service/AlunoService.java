package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.AlunoDTO;
import com.biblioteca.api_publica.domain.model.Aluno;
import com.biblioteca.api_publica.domain.model.Autor;
import com.biblioteca.api_publica.domain.model.Avaliacao;
import com.biblioteca.api_publica.domain.model.Disciplina;
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

        AlunoDTO dtoRetorno = MapperUtil.parseObject(repository.save(model), AlunoDTO.class);

        if (model.getDisciplinas() != null) {
            dtoRetorno.setNomesDisciplinas(model.getDisciplinas().stream().map(Disciplina::getNome).toList());
        }

        return dtoRetorno;
    }

    public List<AlunoDTO> findAll() {
        List<Aluno> alunos = repository.findAll();

        return alunos.stream().map(aluno -> {
            AlunoDTO dto = MapperUtil.parseObject(aluno, AlunoDTO.class);

            // Ponte manual: de List<Disciplina> para List<String>
            if (aluno.getDisciplinas() != null) {
                dto.setNomesDisciplinas(aluno.getDisciplinas().stream()
                        .map(Disciplina::getNome)
                        .toList());
            }

            return dto;
        }).toList();
    }

    public AlunoDTO findById(Long id) {
        Aluno model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não encontrado"));
        AlunoDTO dtoRetorno = MapperUtil.parseObject(model, AlunoDTO.class);

        if (model.getDisciplinas() != null) {
            dtoRetorno.setNomesDisciplinas(model.getDisciplinas().stream().map(Disciplina::getNome).toList());
        }

        return dtoRetorno;
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

        AlunoDTO dtoRetorno = MapperUtil.parseObject(repository.save(existente), AlunoDTO.class);

        if (existente.getDisciplinas() != null) {
            dtoRetorno.setNomesDisciplinas(existente.getDisciplinas().stream().map(Disciplina::getNome).toList());
        }

        return dtoRetorno;
    }

    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "ID inválido");
        repository.deleteById(id);
    }

    public List<LivroDTO> getRecomendacoes(Long alunoId) {
        // 1. Localiza o aluno
        Aluno aluno = repository.findById(alunoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não encontrado"));

        // 2. Coleta os livros das disciplinas
        List<Livro> livrosRecomendados = aluno.getDisciplinas().stream()
                .flatMap(disciplina -> disciplina.getLivrosRecomendados().stream())
                .distinct()
                .toList();

        // 3. Converte para DTO com o mapeamento manual dos nomes
        return livrosRecomendados.stream().map(livro -> {
            LivroDTO dto = MapperUtil.parseObject(livro, LivroDTO.class);

            // Mapeamento manual de Autores
            if (livro.getAutores() != null) {
                dto.setNomesAutores(livro.getAutores().stream()
                        .map(Autor::getNome)
                        .toList());
            }

            // Mapeamento manual da Editora
            if (livro.getEditora() != null) {
                dto.setNomeEditora(livro.getEditora().getNome());
            }

            // Mapeamento 
            if (livro.getAvaliacoes() != null && !livro.getAvaliacoes().isEmpty()) {
                double media = livro.getAvaliacoes().stream()
                        .mapToInt(Avaliacao::getNota)
                        .average()
                        .orElse(0.0);

                dto.setMediaAvaliacao(Math.round(media * 10.0) / 10.0);
            } else {
                dto.setMediaAvaliacao(0.0);
            }

            return dto;
        }).toList();
    }
}
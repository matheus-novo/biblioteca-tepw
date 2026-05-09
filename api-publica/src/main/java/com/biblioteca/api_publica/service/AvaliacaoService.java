package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.AvaliacaoDTO;
import com.biblioteca.api_publica.domain.model.Avaliacao;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.AvaliacaoRepository;
import com.biblioteca.api_publica.repository.LivroRepository;
import com.biblioteca.api_publica.repository.AlunoRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;

    public AvaliacaoDTO save(AvaliacaoDTO dto) {
        // Verifica se o aluno já avaliou este livro (Lógica de Upsert)
        Optional<Avaliacao> avaliacaoExistente = repository.findByAlunoIdAndLivroId(dto.getAlunoId(), dto.getLivroId());

        if (avaliacaoExistente.isPresent()) {
            return update(avaliacaoExistente.get().getId(), dto);
        }

        Avaliacao novoModel = MapperUtil.parseObject(dto, Avaliacao.class);
        validarERelacionar(novoModel, dto);
        
        return MapperUtil.parseObject(repository.save(novoModel), AvaliacaoDTO.class);
    }

    public AvaliacaoDTO update(Long id, AvaliacaoDTO dto) {
        Avaliacao model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "avaliacao.notfound", "Avaliação não encontrada"));

        model.setNota(dto.getNota());
        model.setComentario(dto.getComentario());
        model.setRecomenda(dto.getRecomenda());
        model.setDataAvaliacao(java.time.LocalDate.now());

        return MapperUtil.parseObject(repository.save(model), AvaliacaoDTO.class);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "avaliacao.notfound", "ID inválido");
        }
        repository.deleteById(id);
    }

    public List<AvaliacaoDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), AvaliacaoDTO.class);
    }

    public AvaliacaoDTO findById(Long id) {
        Avaliacao model = repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "avaliacao.notfound", "Avaliação inexistente"));
        return MapperUtil.parseObject(model, AvaliacaoDTO.class);
    }

    // Método auxiliar para evitar repetição de código (Clean Code)
    private void validarERelacionar(Avaliacao model, AvaliacaoDTO dto) {
        var livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "livro.notfound", "Livro não existe"));
        var aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "aluno.notfound", "Aluno não existe"));
        
        model.setLivro(livro);
        model.setAluno(aluno);
    }
}
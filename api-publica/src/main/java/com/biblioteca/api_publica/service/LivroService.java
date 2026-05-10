package com.biblioteca.api_publica.service;

import com.biblioteca.api_publica.domain.dto.LivroDTO;
import com.biblioteca.api_publica.domain.model.Autor;
import com.biblioteca.api_publica.domain.model.Avaliacao;
import com.biblioteca.api_publica.domain.model.Livro;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.LivroRepository;
import com.biblioteca.api_publica.service.external.GoogleBooksService;
import com.biblioteca.api_publica.service.external.OpenLibraryService;
import com.biblioteca.api_publica.repository.EditoraRepository;
import com.biblioteca.api_publica.repository.AutorRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private GoogleBooksService googleBooksService;

    @Autowired
    private OpenLibraryService openLibraryService;

    public LivroDTO create(LivroDTO dto) {

        if (dto.getIsbn() != null && (dto.getTitulo() == null || dto.getTitulo().isBlank())) {
            preencherMetadadosExternos(dto);
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "livro.titulo.obrigatorio",
                    "Não foi possível obter o título automaticamente. Por favor, informe o título manualmente.");
        }

        // Converte DTO para Model
        Livro model = MapperUtil.parseObject(dto, Livro.class);

        // Lógica de Relacionamento (Tratando os IDs que vêm no DTO)
        var editora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RuntimeException("Editora não encontrada"));
        var autores = autorRepository.findAllById(dto.getAutorIds());

        model.setEditora(editora);
        model.setAutores(autores);

        // Persistência
        var modelPersist = repository.save(model);

        // Retorna convertido para DTO
        return complementarDTO(modelPersist);
    }

    public List<LivroDTO> findAll() {
        // List<Livro> livros = repository.findAll();
        return repository.findAll().stream().map(this::complementarDTO).toList();
    }

    public LivroDTO findById(Long id) {
        var model = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return complementarDTO(model);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Funcionalidade Extra: Busca por resumo
    public List<LivroDTO> searchByResumo(String keyword) {
        // var livros = repository.searchByResumo(keyword);
        return repository.searchByResumo(keyword).stream().map(this::complementarDTO).toList();
    }

    public LivroDTO update(Long id, LivroDTO dto) {
        Livro livroExistente = repository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "biblioteca.service.livro.notfound",
                        "Livro de ID " + id + " não localizado"));

        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setIsbn(dto.getIsbn());
        livroExistente.setResumo(dto.getResumo());
        livroExistente.setAnoPublicacao(dto.getAnoPublicacao());
        livroExistente.setUrlDownload(dto.getUrlDownload());

        var novaEditora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RuntimeException("Editora não encontrada"));
        var novosAutores = autorRepository.findAllById(dto.getAutorIds());

        livroExistente.setEditora(novaEditora);
        livroExistente.setAutores(novosAutores);

        Livro livroAtualizado = repository.save(livroExistente);

        return complementarDTO(livroAtualizado);
    }

    public List<LivroDTO> getTop10Books() {
        // PageRequest.of(página, tamanho)
        Pageable topTen = PageRequest.of(0, 10);
        // List<Livro> livros = repository.findTopRatedBooks(topTen);
        return repository.findTopRatedBooks(topTen).stream().map(this::complementarDTO).toList();
    }

    public List<LivroDTO> getPopular(int limit) {
        Pageable topN = PageRequest.of(0, limit);
        return repository.findPopularBooks(topN).stream()
                .map(this::complementarDTO)
                .toList();
    }

    public List<LivroDTO> findByDisciplina(Long id) {
        return repository.findByDisciplinasId(id).stream()
                .map(this::complementarDTO)
                .toList();
    }

    public List<LivroDTO> findByAutor(Long id) {
        return repository.findByAutoresId(id).stream()
                .map(this::complementarDTO)
                .toList();
    }

    public List<LivroDTO> findByEditora(Long id) {
        return repository.findByEditoraId(id).stream()
                .map(this::complementarDTO)
                .toList();
    }

    private LivroDTO complementarDTO(Livro livro) {
        LivroDTO dto = MapperUtil.parseObject(livro, LivroDTO.class);

        if (livro.getAutores() != null) {
            dto.setNomesAutores(livro.getAutores().stream().map(Autor::getNome).toList());
        }
        if (livro.getEditora() != null) {
            dto.setNomeEditora(livro.getEditora().getNome());
        }

        // 2. Cálculo da Média de Avaliação
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
    }

    private void preencherMetadadosExternos(LivroDTO dto) {
        // 1. TENTATIVA GOOGLE
        var infoGoogle = googleBooksService.buscarDadosPorIsbn(dto.getIsbn());
        if (infoGoogle != null) {
            if (dto.getTitulo() == null)
                dto.setTitulo(infoGoogle.getTitle());
            if (dto.getResumo() == null)
                dto.setResumo(infoGoogle.getDescription());
            if (dto.getAnoPublicacao() == null) {
                dto.setAnoPublicacao(extrairAno(infoGoogle.getPublishedDate()));
            }
            return;
        }

        // 2. TENTATIVA OPEN LIBRARY (PLANO B)
        System.out.println("Google falhou. Tentando Open Library...");
        String[] infoOL = openLibraryService.buscarDadosCompletos(dto.getIsbn());
        if (infoOL != null) {
            if (dto.getTitulo() == null)
                dto.setTitulo(infoOL[0]);
            if (dto.getResumo() == null)
                dto.setResumo(infoOL[1]);
            if (dto.getAnoPublicacao() == null) {
                dto.setAnoPublicacao(extrairAno(infoOL[2]));
            }
        }
    }

    /**
     * Utilitário para extrair 4 dígitos consecutivos de uma string (o ano).
     * Ex: "Apr 10, 2019" -> 2019 ou "2019-05-10" -> 2019
     */
    private Integer extrairAno(String data) {
        if (data == null || data.isBlank())
            return null;

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d{4}");
        java.util.regex.Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return null;
    }
}
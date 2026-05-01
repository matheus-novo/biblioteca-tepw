package com.biblioteca.api_publica.service;
import com.biblioteca.api_publica.domain.dto.LivroDTO;
import com.biblioteca.api_publica.domain.model.Livro;
import com.biblioteca.api_publica.exceptions.ApiException;
import com.biblioteca.api_publica.repository.LivroRepository;
import com.biblioteca.api_publica.repository.EditoraRepository;
import com.biblioteca.api_publica.repository.AutorRepository;
import com.biblioteca.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private AutorRepository autorRepository;

    public LivroDTO create(LivroDTO dto) {
        // 1. Converte DTO para Model
        Livro model = MapperUtil.parseObject(dto, Livro.class);

        // 2. Lógica de Relacionamento (Tratando os IDs que vêm no DTO)
        var editora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RuntimeException("Editora não encontrada"));
        var autores = autorRepository.findAllById(dto.getAutorIds());

        model.setEditora(editora);
        model.setAutores(autores);

        // 3. Persistência
        var modelPersist = repository.save(model);

        // 4. Retorna convertido para DTO 
        return MapperUtil.parseObject(modelPersist, LivroDTO.class);
    }

    public List<LivroDTO> findAll() {
        var livros = repository.findAll();
        return MapperUtil.parseListObjects(livros, LivroDTO.class);
    }

    public LivroDTO findById(Long id) {
        var model = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return MapperUtil.parseObject(model, LivroDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Funcionalidade Extra: Busca por resumo
    public List<LivroDTO> searchByResumo(String keyword) {
        var livros = repository.searchByResumo(keyword);
        return MapperUtil.parseListObjects(livros, LivroDTO.class);
    }

    public LivroDTO update(Long id, LivroDTO dto) {
        // 1. Verifica se o livro existe no banco
        Livro livroExistente = repository.findById(id)
            .orElseThrow(() -> new ApiException(
            HttpStatus.NOT_FOUND, 
            "biblioteca.service.livro.notfound", 
            "Livro de ID " + id + " não localizado"
        ));

        // 2. Atualiza os campos simples
        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setIsbn(dto.getIsbn());
        livroExistente.setResumo(dto.getResumo());
        livroExistente.setAnoPublicacao(dto.getAnoPublicacao());
        livroExistente.setUrlDownload(dto.getUrlDownload());

        // 3. Atualiza os relacionamentos (Busca as novas entidades pelos IDs do DTO)
        var novaEditora = editoraRepository.findById(dto.getEditoraId())
            .orElseThrow(() -> new RuntimeException("Editora não encontrada"));
        var novosAutores = autorRepository.findAllById(dto.getAutorIds());

        livroExistente.setEditora(novaEditora);
        livroExistente.setAutores(novosAutores);

        // 4. Salva a entidade atualizada
        Livro livroAtualizado = repository.save(livroExistente);

        // 5. Retorna o DTO convertido via MapperUtil 
        return MapperUtil.parseObject(livroAtualizado, LivroDTO.class);
    }
}
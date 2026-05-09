-- 1. Tabelas Independentes (Folhas)
CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    titulacao VARCHAR(100),
    biografia TEXT,
    nacionalidade VARCHAR(100)
);

CREATE TABLE editoras (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) UNIQUE,
    cidade VARCHAR(100),
    email_contato VARCHAR(150),
    site_oficial VARCHAR(255)
);

CREATE TABLE alunos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) UNIQUE NOT NULL,
    curso VARCHAR(100),
    email VARCHAR(150),
    data_ingresso DATE
);

CREATE TABLE disciplinas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    curso VARCHAR(100),
    semestre INTEGER,
    ementa TEXT
);

-- 2. Tabelas com Dependências (Chaves Estrangeiras)
CREATE TABLE livros (
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    resumo TEXT,
    ano_publicacao INTEGER,
    url_download VARCHAR(255),
    editora_id BIGINT NOT NULL,
    CONSTRAINT fk_livro_editora FOREIGN KEY (editora_id) REFERENCES editoras(id)
);

CREATE TABLE avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    nota INTEGER NOT NULL CHECK (nota >= 0 AND nota <= 10),
    comentario TEXT,
    data_avaliacao DATE DEFAULT CURRENT_DATE,
    recomenda BOOLEAN,
    livro_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    CONSTRAINT fk_avaliacao_livro FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacao_aluno FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE CASCADE,
    CONSTRAINT uk_aluno_livro UNIQUE (aluno_id, livro_id) -- Garante a regra de um voto por aluno/livro
);

-- 3. Tabelas Associativas (Relacionamentos Many-to-Many)
CREATE TABLE livro_autor (
    livro_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY (livro_id, autor_id),
    CONSTRAINT fk_livro_autor_livro FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE,
    CONSTRAINT fk_livro_autor_autor FOREIGN KEY (autor_id) REFERENCES autores(id) ON DELETE CASCADE
);

CREATE TABLE disciplina_livro (
    disciplina_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    PRIMARY KEY (disciplina_id, livro_id),
    CONSTRAINT fk_disciplina_livro_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id) ON DELETE CASCADE,
    CONSTRAINT fk_disciplina_livro_livro FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE
);

CREATE TABLE aluno_disciplina (
    aluno_id BIGINT NOT NULL,
    disciplina_id BIGINT NOT NULL,
    PRIMARY KEY (aluno_id, disciplina_id),
    CONSTRAINT fk_aluno_disciplina_aluno FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE CASCADE,
    CONSTRAINT fk_aluno_disciplina_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id) ON DELETE CASCADE
);
CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
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

CREATE TABLE livros (
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    resumo TEXT,
    area VARCHAR(50),
    ano_publicacao INTEGER,
    url_download VARCHAR(255),
    editora_id BIGINT REFERENCES editoras(id)
);

CREATE TABLE acessos (
    id BIGSERIAL PRIMARY KEY,
    data_acesso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_origem VARCHAR(50),
    dispositivo VARCHAR(50),
    duracao_segundos INTEGER,
    livro_id BIGINT REFERENCES livros(id)
);

CREATE TABLE avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    nota INTEGER CHECK (nota >= 1 AND nota <= 5),
    comentario TEXT,
    data_avaliacao DATE DEFAULT CURRENT_DATE,
    recomenda BOOLEAN,
    livro_id BIGINT REFERENCES livros(id)
);

CREATE TABLE livro_autor (
    livro_id BIGINT REFERENCES livros(id),
    autor_id BIGINT REFERENCES autores(id),
    PRIMARY KEY (livro_id, autor_id)
);
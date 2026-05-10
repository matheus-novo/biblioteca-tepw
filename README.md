# 📚 Biblioteca Inteligente API (TEPW)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Maven](https://img.shields.io/badge/Maven-3.8+-red)

Uma API RESTful avançada para gestão de bibliotecas acadêmicas, focada em **Sustentabilidade de Código**, **Arquitetura Limpa** e **Resiliência**. O diferencial deste projeto é a integração inteligente com APIs externas e motores de recomendação personalizados para alunos.

---

## 🚀 Diferenciais Técnicos

### 🧠 Inteligência de Dados
* **Recomendações Personalizadas:** Algoritmo que cruza as disciplinas em que o aluno está matriculado com a bibliografia sugerida pelos professores, gerando uma lista de leitura sob medida.
* **Rankings Dinâmicos:** Endpoints de agregação que calculam em tempo real os **Top 10 Livros** (melhor média de nota) e os **Mais Populares** (maior volume de avaliações).
* **Dashboard de Autores:** Ranking de autores baseado na satisfação dos leitores em todo o seu acervo publicado.

### 🛡️ Resiliência e Integração Externa (Fallback)
O sistema implementa uma **Cascata de Resiliência** para o cadastro automático de livros via ISBN. Caso uma fonte falhe, o sistema tenta a próxima:
1.  **Google Books API:** Primeira escolha para metadados ricos.
2.  **Open Library API:** Plano B (Fallback) caso o Google atinja limites de quota ou esteja instável.
3.  **Entrada Manual:** Se ambas falharem, o sistema solicita os dados ao usuário, garantindo a integridade do banco de dados.

---

## 🏗️ Arquitetura do Sistema

O projeto segue os princípios da **Arquitetura em Camadas** e **Clean Code**:

* **Controller:** Exposição de endpoints REST com documentação Swagger/OpenAPI.
* **Service:** Camada de regras de negócio e orquestração de integrações externas.
* **Domain (Model/DTO):** Separação clara entre as entidades do banco e os objetos de transferência de dados (DTOs) usando `MapperUtil`.
* **Repository:** Consultas otimizadas usando Spring Data JPA e JPQL para agregações complexas.
* **ExceptionHandler:** Tratamento centralizado de erros com respostas semânticas.

---

## 🛠️ Tecnologias Utilizadas

* **Framework:** Spring Boot 3.x
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL (Hospedado no Supabase)
* **Migrações:** Flyway DB
* **Documentação:** SpringDoc OpenAPI (Swagger)
* **Ferramentas:** Lombok, ModelMapper, RestTemplate

---

## 📋 Como Executar o Projeto

### Pré-requisitos
* Java 17 ou superior.
* Maven 3.8.x instalado.
* Acesso à internet (para integração com as APIs de Livros).

### Passos
1. Clone o repositório.
2. Configure as credenciais do banco de dados no arquivo `src/main/resources/application.properties`.
3. Execute o comando:
   ```bash
   ./mvnw clean spring-boot:run
4. Acesse a documentação interativa da API (Swagger) no endereço:
`http://localhost:8080/swagger-ui.html`

---

## 📈 Endpoints Principais (Highlights)

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/v1/livros/top10` | Lista os 10 melhores livros baseados na média de notas. |
| `GET` | `/api/v1/livros/popular` | Lista os livros com maior número de avaliações. |
| `GET` | `/api/v1/alunos/{id}/recomendacoes` | Sugere livros baseados na grade curricular do aluno. |
| `POST` | `/api/v1/livros` | Cria um livro buscando metadados automáticos via ISBN. |
| `GET` | `/api/v1/autores/ranking` | Ranking de autores por satisfação geral dos alunos. |

---

**Projeto desenvolvido para a NP2 da disciplina de Tópicos Especiais em Programação Web.**
# 🏃‍♂️ HábitoPlus — Sprint 3: SOA e WebServices

**Integrantes:**
- Micael Santos Azarias | RM552699
- Felipe Megumi Nakama | RM552821
- Carolina Cavalli Machado | RM552925
- Nathan da Silveira Uflacker | RM553264

---

## 📋 Descrição do Projeto

O **HábitoPlus** é o backend de um módulo de **Saúde e Bem-estar Corporativo**, desenvolvido para incentivar, registrar e recompensar comportamentos saudáveis dos colaboradores por meio de gamificação (**sistema de Milhas de Saúde**).

Este sprint implementa a API REST completa seguindo os princípios de **SOA (Arquitetura Orientada a Serviços)**, com CRUD completo, migrações de banco de dados, tratamento de erros padronizado e uso correto de DTOs, VOs e enums.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 (LTS) | Linguagem principal |
| Spring Boot | 3.2.3 | Framework web |
| Spring Data JPA | 3.2.3 | Persistência e ORM |
| Spring Validation | 3.2.3 | Validação de entradas |
| Flyway | — | Migrações de banco de dados |
| MySQL | 8+ | Banco de dados relacional |
| Lombok | — | Redução de boilerplate |
| Maven | — | Gerenciamento de dependências |

---

## 📂 Estrutura do Projeto

```
src/main/java/br/com/habitoplus/
├── controller/
│   └── HabitoController.java       # Endpoints REST (CRUD completo)
├── dto/
│   ├── HabitoRequest.java          # DTO de entrada (com validações)
│   ├── HabitoResponse.java         # VO de saída
│   └── ErroResponse.java           # VO padronizado de erros
├── enums/
│   └── TipoAtividade.java          # Enum com multiplicadores de pontos
├── exception/
│   ├── GlobalExceptionHandler.java # @RestControllerAdvice global
│   └── RecursoNaoEncontradoException.java
├── model/
│   ├── RegistroHabito.java         # Entidade principal
│   └── Usuario.java                # Entidade de colaborador
├── repository/
│   ├── HabitoRepository.java       # Acesso a dados de hábitos
│   └── UsuarioRepository.java      # Acesso a dados de usuários
├── service/
│   └── HabitoService.java          # Regras de negócio e pontuação
└── HabitoplusApplication.java      # Classe principal

src/main/resources/
├── application.properties
└── db/migration/
    ├── V1__criar_tabelas_iniciais.sql   # Criação das tabelas
    └── V2__inserir_dados_iniciais.sql   # Dados de seed
```

---

## 🗄️ Configuração e Execução

### 1. Pré-requisitos
- Java 21
- MySQL 8+
- Maven

### 2. Configurar o banco de dados
```sql
CREATE DATABASE habitoplus;
```

### 3. Ajustar credenciais
Edite `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

### 4. Executar a aplicação
```bash
./mvnw spring-boot:run
```
A API estará disponível em: `http://localhost:8080`

O **Flyway** executará automaticamente as migrações `V1` e `V2` na primeira inicialização.

---

## 🧪 Endpoints e Exemplos de Requisições

### ✅ POST `/api/v1/habitos` — Registrar hábito
```json
// Request Body
{
  "colaboradorId": "COL-001",
  "tipoAtividade": "CORRIDA",
  "descricao": "Corrida matinal no parque",
  "minutosDuracao": 45
}

// Response 201 Created
{
  "id": 1,
  "colaboradorId": "COL-001",
  "tipoAtividade": "CORRIDA",
  "descricaoAtividade": "Corrida ou caminhada acelerada",
  "descricao": "Corrida matinal no parque",
  "minutosDuracao": 45,
  "pontosGerados": 450,
  "dataRegistro": "2025-11-28T10:30:00"
}
```

### ✅ GET `/api/v1/habitos` — Listar todos
```json
// Response 200 OK
[
  { "id": 1, "colaboradorId": "COL-001", "tipoAtividade": "CORRIDA", ... }
]
```

### ✅ GET `/api/v1/habitos/{id}` — Buscar por ID
```
GET /api/v1/habitos/1
Response 200 OK → objeto do hábito
Response 404 → { "status": 404, "erro": "Recurso Não Encontrado", ... }
```

### ✅ GET `/api/v1/habitos/colaborador/{colaboradorId}` — Listar por colaborador
```
GET /api/v1/habitos/colaborador/COL-001
Response 200 OK → lista de hábitos do colaborador
```

### ✅ PUT `/api/v1/habitos/{id}` — Atualizar hábito
```json
// Request Body
{
  "colaboradorId": "COL-001",
  "tipoAtividade": "MUSCULACAO",
  "descricao": "Treino de força",
  "minutosDuracao": 60
}
// Response 200 OK → hábito atualizado
```

### ✅ DELETE `/api/v1/habitos/{id}` — Remover hábito
```
DELETE /api/v1/habitos/1
Response 204 No Content
```

### ❌ Exemplo de erro de validação
```json
// POST com minutosDuracao = 5 (abaixo do mínimo)
// Response 400 Bad Request
{
  "status": 400,
  "erro": "Erro de Validação",
  "mensagem": "A atividade deve ter no mínimo 10 minutos",
  "timestamp": "2025-11-28T10:30:00"
}
```

---

## 📊 Regra de Pontuação (Milhas de Saúde)

| Atividade | Multiplicador | Exemplo (30 min) |
|---|---|---|
| CORRIDA | 10x | 300 pontos |
| NATACAO | 10x | 300 pontos |
| CICLISMO | 9x | 270 pontos |
| MUSCULACAO | 8x | 240 pontos |
| YOGA | 6x | 180 pontos |
| CAMINHADA | 5x | 150 pontos |
| MEDITACAO | 4x | 120 pontos |
| OUTROS | 3x | 90 pontos |

**Fórmula:** `pontos = minutosDuracao × multiplicadorDoTipo`

---

## 🏗️ Diagrama de Arquitetura (Camadas)

```
Cliente (Postman / Frontend)
        │
        ▼
  [ Controller ]       ← Recebe requisições HTTP, valida DTOs, retorna ResponseEntity
        │
        ▼
  [   Service  ]       ← Regras de negócio, cálculo de pontos
        │
        ▼
  [ Repository ]       ← Acesso ao banco de dados via JPA
        │
        ▼
  [   MySQL    ]       ← Persistência gerenciada pelo Flyway
```

## 🗃️ Diagrama de Entidades (ER)

```
┌─────────────────────────┐         ┌──────────────────────────────┐
│         USUARIO          │         │       REGISTRO_HABITO         │
├─────────────────────────┤         ├──────────────────────────────┤
│ id (PK)                 │         │ id (PK)                      │
│ colaborador_id (UNIQUE) │◄────────│ colaborador_id (FK)          │
│ nome                    │         │ tipo_atividade (ENUM)        │
│ email (UNIQUE)          │         │ descricao                    │
│ data_cadastro           │         │ minutos_duracao              │
└─────────────────────────┘         │ pontos_gerados               │
                                    │ data_registro                │
                                    └──────────────────────────────┘
```

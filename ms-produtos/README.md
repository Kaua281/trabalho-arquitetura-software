# Microsserviço de Produtos

Serviço REST responsável por cadastrar, consultar, atualizar e excluir produtos. É uma aplicação Spring Boot independente, executada na porta `8082`.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2
- Bean Validation
- Springdoc OpenAPI
- Maven

## Executar

No PowerShell, na raiz do repositório:

```powershell
cd ms-produtos
.\mvnw.cmd spring-boot:run
```

Também é possível usar Maven instalado:

```powershell
mvn spring-boot:run
```

Com o serviço iniciado:

- API base: `http://localhost:8082/api/produtos`
- Swagger UI: `http://localhost:8082/swagger-ui.html`
- Console H2: `http://localhost:8082/h2-console`

Para acessar o H2 Console, use a URL JDBC `jdbc:h2:mem:produtos`, usuário `sa` e senha em branco.

## Modelo de dados e validação

Um produto possui:

| Campo | Tipo JSON | Regras |
| --- | --- | --- |
| `id` | número | Gerado pelo banco; não deve ser enviado na criação ou atualização. |
| `nome` | texto | Obrigatório, não pode ser vazio ou só espaços; máximo de 255 caracteres. |
| `descricao` | texto ou `null` | Opcional; máximo de 255 caracteres. |
| `preco` | número decimal | Obrigatório; mínimo `0.01`, até 10 dígitos inteiros e 2 casas decimais. |

### Rotas implementadas

| Método | Rota | Resultado esperado |
| --- | --- | --- |
| `GET` | `/api/produtos` | Lista os recursos (`200 OK`). |
| `GET` | `/api/produtos/{id}` | Consulta um recurso (`200 OK`, ou `404 Not Found`). |
| `POST` | `/api/produtos` | Cria um recurso (`201 Created`). |
| `PUT` | `/api/produtos/{id}` | Atualiza um recurso (`200 OK`, ou `404 Not Found`). |
| `DELETE` | `/api/produtos/{id}` | Exclui um recurso (`204 No Content`, ou `404 Not Found`). |

### Exemplo de Produto

```json
{
  "nome": "Armadura Mark V",
  "descricao": "Armadura tecnologica portatil desenvolvida por Tony Stark.",
  "preco": 8500000.00
}
```

## Respostas de erro

Os erros tratados pela API usam este formato:

```json
{
  "dataHora": "2026-11-20T19:00:00Z",
  "status": 400,
  "erro": "Erro nos dados da requisição.",
  "mensagem": "detalhe da validação ou do erro",
  "caminho": "/api/produtos"
}
```

Principais códigos:

| Código | Quando ocorre |
| --- | --- |
| `400 Bad Request` | Campos inválidos, valores fora das regras ou corpo JSON inválido/malformado. |
| `404 Not Found` | Produto não encontrado para consulta, atualização ou exclusão. |
| `409 Conflict` | Conflito de entidade, quando aplicável. |
| `500 Internal Server Error` | Erro inesperado; a resposta não expõe detalhes internos. |

## Persistência e dados de demonstração

O serviço usa H2 em memória (`jdbc:h2:mem:produtos`). Ao iniciar, carrega produtos de demonstração se o banco estiver vazio. Os dados são perdidos quando o processo é encerrado e são carregados novamente na próxima inicialização.

## Organização do código

- `controller`: endpoints REST e códigos HTTP.
- `service`: operações de negócio e conversão para DTOs.
- `repository`: acesso aos dados via Spring Data JPA.
- `model`: entidade persistida `Produto`.
- `dto.request` e `dto.response`: contratos de entrada e saída da API.
- `exception`: tratamento global e formato das respostas de erro.
- `config`: carga inicial de dados de demonstração.

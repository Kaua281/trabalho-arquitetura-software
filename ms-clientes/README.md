# Microsserviço de Clientes

Serviço REST responsável por cadastrar, consultar, atualizar e excluir clientes pessoa física (PF) e pessoa jurídica (PJ). É uma aplicação Spring Boot independente, executada na porta `8084`.

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
cd ms-clientes
.\mvnw.cmd spring-boot:run
```

Também é possível usar Maven instalado:

```powershell
mvn spring-boot:run
```

Com o serviço iniciado:

- API base: `http://localhost:8084/api/clientes`
- Swagger UI: `http://localhost:8084/swagger-ui.html`
- Console H2: `http://localhost:8084/h2-console`

Para acessar o H2 Console, use a URL JDBC `jdbc:h2:mem:cliente`, usuário `sa` e senha em branco.

## Modelo de dados e validação

O cadastro usa um DTO único para PF e PJ. Informe `tipo` e os campos correspondentes:

| Campo | Tipo JSON | Regras |
| --- | --- | --- |
| `id` | número | Gerado pelo banco; não faz parte do DTO de entrada. |
| `tipo` | texto | `PESSOA_FISICA` ou `PESSOA_JURIDICA`. |
| `email` | texto | Obrigatório no modelo persistido. |
| `telefone` | texto ou `null` | Opcional; máximo de 20 caracteres. |
| `nomeCompleto` | texto | Usado em PF; obrigatório no modelo de PF; máximo de 150 caracteres. |
| `cpf` | texto | Usado em PF; obrigatório, único e não pode ser alterado após o cadastro. |
| `dataNascimento` | texto ou `null` | Usado em PF; opcional; formato `YYYY-MM-DD`. |
| `razaoSocial` | texto | Usado em PJ; obrigatório no modelo de PJ; máximo de 150 caracteres. |
| `nomeFantasia` | texto ou `null` | Usado em PJ; opcional; máximo de 150 caracteres. |
| `cnpj` | texto | Usado em PJ; obrigatório, único, máximo de 18 caracteres e não pode ser alterado. |
| `inscricaoEstadual` | texto ou `null` | Usado em PJ; opcional; máximo de 14 caracteres. |

### Rotas implementadas

| Método | Rota | Resultado esperado |
| --- | --- | --- |
| `GET` | `/api/clientes` | Lista os clientes (`200 OK`). |
| `GET` | `/api/clientes/{id}` | Consulta um cliente (`200 OK`, ou `404 Not Found`). |
| `POST` | `/api/clientes` | Cria PF ou PJ (`201 Created`). |
| `PUT` | `/api/clientes/{id}` | Atualiza os dados permitidos (`200 OK`, `404 Not Found` ou `400 Bad Request`). |
| `DELETE` | `/api/clientes/{id}` | Exclui um cliente (`204 No Content`, ou `404 Not Found`). |

### Exemplo de Cliente PF

```json
{
  "tipo": "PESSOA_FISICA",
  "email": "ironman@starkindustries.com",
  "telefone": "555323232",
  "nomeCompleto": "Tony Stark",
  "cpf": "22211133345",
  "dataNascimento": "1972-12-03"
}
```

### Exemplo de Cliente PJ

```json
{
  "tipo": "PESSOA_JURIDICA",
  "email": "ceo@starkindustries.com",
  "telefone": "555323232",
  "razaoSocial": "Howard Stark and Son Company",
  "nomeFantasia": "Stark Industries",
  "cnpj": "22233344400001",
  "inscricaoEstadual": "112233445"
}
```

Para PF, `nome` representa `nomeCompleto` e `idReceitaFederal` representa CPF. Para PJ, representam `razaoSocial` e CNPJ.

## Respostas de erro

Os erros tratados pela API usam este formato:

```json
{
  "dataHora": "2026-11-20T19:00:00Z",
  "status": 404,
  "erro": "Entidade não encontrada.",
  "mensagem": "Cliente não localizado com o id 99",
  "caminho": "/api/clientes/99"
}
```

Principais códigos:

| Código | Quando ocorre |
| --- | --- |
| `400 Bad Request` | Tipo de cliente incompatível ou tentativa de alterar CPF, CNPJ ou tipo. |
| `404 Not Found` | Cliente não encontrado. |
| `409 Conflict` | CPF ou CNPJ já cadastrado. |
| `500 Internal Server Error` | Erro inesperado; a resposta não expõe detalhes internos. |

## Persistência e dados de demonstração

O serviço usa H2 em memória (`jdbc:h2:mem:cliente`). Ao iniciar, carrega clientes de demonstração se o banco estiver vazio. Os dados são perdidos quando o processo é encerrado e são carregados novamente na próxima inicialização.

## Organização do código

- `controller`: endpoints REST.
- `service`: operações de negócio e regras de PF/PJ.
- `repository`: persistência via Spring Data JPA.
- `model`: entidade base `Cliente`, entidades `ClientePF` e `ClientePJ` e fábrica de clientes.
- `dto.request` e `dto.response`: contratos de entrada e saída.
- `exception`: tratamento global e formato das respostas de erro.
- `config`: carga inicial de dados de demonstração.

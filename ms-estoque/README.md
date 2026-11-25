# Microsserviço de Estoque

Aplicação Spring Boot independente responsável pelo gerenciamento das quantidades disponíveis dos produtos. Permite consultar registros de estoque, cadastrar e atualizar quantidades, além de registrar entradas e saídas de produtos.

O serviço é executado na porta `8081` e possui banco de dados próprio, utilizando H2 em memória.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 Database
- Springdoc OpenAPI
- Maven

## Executar

No PowerShell, na raiz do repositório:

```powershell
cd ms-estoque
.\mvnw.cmd spring-boot:run
```

Também é possível utilizar o Maven instalado:

```powershell
mvn spring-boot:run
```

Com o serviço iniciado:

- API base: `http://localhost:8081`
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- Console H2: `http://localhost:8081/h2-console`

Para acessar o H2 Console, utilize:

| Configuração | Valor |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:estoque` |
| Usuário | `sa` |
| Senha | Em branco |

## Modelo de dados e validação

O serviço utiliza a entidade `Estoque`, responsável por armazenar a quantidade disponível de cada produto.

### Entidade Estoque

| Campo | Tipo | Descrição |
| --- | --- | --- |
| `id` | Long | Identificador único do registro, gerado automaticamente. |
| `produtoId` | Long | Identificador do produto associado ao estoque. |
| `quantidade` | Integer | Quantidade disponível do produto. |

O campo `produtoId` possui restrição de unicidade, impedindo que existam dois registros de estoque para o mesmo produto.

### DTOs de entrada

**EstoqueRequestDTO**

Utilizado no cadastro e na atualização de registros.

| Campo | Tipo JSON | Regra |
| --- | --- | --- |
| `produtoId` | Number | Obrigatório e maior que zero. |
| `quantidade` | Number | Obrigatória e maior ou igual a zero. |

**MovimentacaoRequestDTO**

Utilizado nas operações de entrada e saída.

| Campo | Tipo JSON | Regra |
| --- | --- | --- |
| `produtoId` | Number | Obrigatório e maior que zero. |
| `quantidade` | Number | Obrigatória e maior que zero. |

### DTO de resposta

As consultas e operações retornam os seguintes campos:

| Campo | Tipo JSON | Descrição |
| --- | --- | --- |
| `id` | Number | Identificador do registro de estoque. |
| `produtoId` | Number | Identificador do produto. |
| `quantidade` | Number | Quantidade atual disponível. |

## Rotas implementadas

Todas as rotas possuem o prefixo `/estoque`.

| Método | Rota | Descrição | Resposta de sucesso |
| --- | --- | --- | --- |
| GET | `/estoque` | Lista todos os registros de estoque. | 200 OK |
| GET | `/estoque/{id}` | Busca um registro pelo seu identificador. | 200 OK |
| GET | `/estoque/produto/{produtoId}` | Busca o estoque associado a um produto. | 200 OK |
| POST | `/estoque` | Cadastra um novo registro de estoque. | 201 Created |
| PUT | `/estoque/{id}` | Atualiza o produto associado e sua quantidade. | 200 OK |
| DELETE | `/estoque/{id}` | Remove um registro de estoque. | 204 No Content |
| POST | `/estoque/entradas` | Registra uma entrada de produtos. | 200 OK |
| POST | `/estoque/saidas` | Registra uma saída de produtos. | 200 OK |

### Exemplos de requisições

**Cadastrar estoque**

`POST /estoque`

```json
{
  "produtoId": 4,
  "quantidade": 30
}
```

**Atualizar estoque**

`PUT /estoque/1`

```json
{
  "produtoId": 1,
  "quantidade": 100
}
```

**Registrar entrada**

`POST /estoque/entradas`

```json
{
  "produtoId": 3,
  "quantidade": 10
}
```

A operação acrescenta 10 unidades ao estoque do produto 3. Caso ainda não exista um registro para esse produto, um novo registro é criado com a quantidade informada.

**Registrar saída**

`POST /estoque/saidas`

```json
{
  "produtoId": 3,
  "quantidade": 2
}
```

A operação reduz em 2 unidades a quantidade disponível do produto 3, desde que exista estoque suficiente.

### Exemplo de resposta

```json
{
  "id": 1,
  "produtoId": 1,
  "quantidade": 50
}
```

## Respostas de erro

O serviço possui tratamento global de exceções por meio da classe `GlobalExceptionHandler`.

| Código HTTP | Situação |
| --- | --- |
| 400 Bad Request | Dados de entrada inválidos, como identificadores ou quantidades fora das regras de validação. |
| 404 Not Found | Registro de estoque ou estoque associado ao produto não encontrado. |
| 409 Conflict | Tentativa de cadastrar estoque duplicado ou realizar saída com quantidade insuficiente. |
| 500 Internal Server Error | Erro interno não tratado especificamente pelo serviço. |

As respostas de erro seguem o formato:

```json
{
  "status": 409,
  "erro": "Conflict",
  "mensagem": "Estoque insuficiente para o produto 3",
  "timestamp": "2026-11-24T20:00:00"
}
```

O horário apresentado é apenas ilustrativo.

## Persistência e dados de demonstração

O serviço utiliza o banco H2 em memória, configurado com a URL `jdbc:h2:mem:estoque`.

Os dados são perdidos quando a aplicação é encerrada.

A carga inicial é realizada pelo arquivo `src/main/resources/data.sql`, que insere os seguintes registros:

| Produto ID | Quantidade inicial |
| --- | ---: |
| 1 | 50 |
| 2 | 20 |
| 3 | 5 |

Esses registros são carregados automaticamente na inicialização da aplicação.

## Regras de negócio e integração

O microsserviço de Estoque é responsável exclusivamente pelo controle das quantidades disponíveis dos produtos.

Entre suas principais regras estão:

- Não permitir mais de um registro de estoque para o mesmo produto.
- Não permitir quantidades negativas no cadastro ou na atualização.
- Não permitir entradas ou saídas com quantidades iguais ou inferiores a zero.
- Impedir saídas superiores à quantidade disponível.
- Permitir a criação automática de um registro durante uma operação de entrada, caso ele ainda não exista.
- Utilizar transações e bloqueio pessimista nas operações de movimentação para proteger atualizações concorrentes.

O microsserviço de Vendas utiliza a API de Estoque para solicitar a baixa das quantidades correspondentes às vendas realizadas. Dessa forma, o controle do estoque permanece centralizado neste serviço.

**Observação:** o serviço armazena o identificador do produto, mas não realiza uma consulta ao microsserviço de Produtos para confirmar sua existência.

## Organização do código

A aplicação segue uma organização em camadas:

- `controller`: disponibiliza os endpoints REST.
- `service`: implementa as regras de negócio e operações de estoque.
- `repository`: realiza o acesso aos dados por meio do Spring Data JPA.
- `model`: contém a entidade persistida `Estoque`.
- `dto.request`: contém os objetos utilizados nas requisições.
- `dto.response`: destinado aos objetos de resposta.
- `exception`: contém as exceções específicas e o tratamento global.
- `util`: contém o mapeamento entre entidade e DTO.
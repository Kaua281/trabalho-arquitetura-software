# Microsserviço de Vendas

Serviço REST responsável por registrar e consultar vendas. É uma aplicação Spring Boot independente, executada na porta `8083`.

É o serviço que **orquestra** a operação integrada do sistema: para registrar uma venda, ele consulta os microsserviços de Clientes e Produtos e solicita a baixa ao microsserviço de Estoque, sempre por meio das APIs REST desses serviços.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- RestClient (comunicação HTTP entre microsserviços)
- H2
- Bean Validation
- Lombok
- Springdoc OpenAPI
- Maven

## Executar

No PowerShell, na raiz do repositório:

```powershell
cd ms-vendas
.\mvnw.cmd spring-boot:run
```

Também é possível usar Maven instalado:

```powershell
mvn spring-boot:run
```

Com o serviço iniciado:

- API base: `http://localhost:8083/api/vendas`
- Swagger UI: `http://localhost:8083/swagger-ui.html`
- Console H2: `http://localhost:8083/h2-console`

Para acessar o H2 Console, use a URL JDBC `jdbc:h2:mem:vendas`, usuário `sa` e senha em branco.

> O serviço de Vendas inicia sozinho, mas para **registrar** uma venda os microsserviços de Clientes (`8084`), Produtos (`8082`) e Estoque (`8081`) precisam estar em execução. As consultas (`GET`) funcionam sem eles.

## Integração com outros microsserviços

Os endereços ficam em `application.properties`:

| Propriedade | Valor padrão |
| --- | --- |
| `servicos.clientes.url` | `http://localhost:8084/api/clientes` |
| `servicos.produtos.url` | `http://localhost:8082/api/produtos` |
| `servicos.estoque.url` | `http://localhost:8081/api/estoque` |

Chamadas realizadas pelo serviço de Vendas:

| Serviço | Requisição | Finalidade |
| --- | --- | --- |
| Clientes | `GET /api/clientes/{id}` | Verificar se o cliente existe. |
| Produtos | `GET /api/produtos/{id}` | Verificar se o produto existe e obter o preço atual. |
| Estoque | `POST /api/estoque/saidas` | Solicitar a baixa da quantidade vendida. O Estoque verifica o saldo e responde `409` se for insuficiente. |
| Estoque | `POST /api/estoque/entradas` | Compensação: devolve a quantidade caso a venda não consiga ser gravada após a baixa. |

Corpo enviado ao Estoque (saídas e entradas):

```json
{
  "idProduto": 3,
  "quantidade": 2
}
```

### Fluxo de registro de uma venda

1. O cliente envia `POST /api/vendas`.
2. Vendas valida o corpo da requisição (`400` se inválido).
3. Vendas consulta o Clientes. Se o cliente não existir, o fluxo é interrompido (`404`).
4. Vendas consulta o Produtos. Se o produto não existir, o fluxo é interrompido (`404`). Caso exista, o preço retornado é usado na venda.
5. Vendas solicita a baixa ao Estoque. Se não houver unidades suficientes, o fluxo é interrompido (`409`) e nenhuma venda é gravada.
6. Vendas grava a venda com cliente, produto, quantidade, preço unitário e data. Se a gravação falhar, solicita ao Estoque a devolução das unidades.
7. Vendas responde `201 Created` com os dados da venda e o cabeçalho `Location`.

As consultas a Clientes e Produtos são feitas antes da baixa no Estoque porque não alteram nada: se uma delas falhar, nenhum outro serviço foi modificado.

O preço é **sempre** obtido do serviço de Produtos e armazenado na venda. Assim, a venda preserva o valor praticado no momento da compra, mesmo que o preço do produto mude depois.

## Modelo de dados e validação

Uma venda possui:

| Campo | Tipo | Regras |
| --- | --- | --- |
| `id` | número | Gerado pelo banco; não deve ser enviado. |
| `idCliente` | número | Obrigatório; o cliente deve existir no serviço de Clientes. |
| `idProduto` | número | Obrigatório; o produto deve existir no serviço de Produtos. |
| `quantidade` | número inteiro | Obrigatório; maior que zero; deve haver saldo no Estoque. |
| `precoUnitario` | número decimal | Preenchido pelo serviço com o preço retornado por Produtos; não é enviado na requisição. |
| `dataVenda` | data e hora | Preenchida pelo serviço no momento do registro. |

Na resposta, o campo `total` é calculado como `precoUnitario × quantidade`.

### Rotas implementadas

| Método | Rota | Resultado esperado |
| --- | --- | --- |
| `POST` | `/api/vendas` | Registra uma venda (`201 Created`, `400`, `404`, `409` ou `503`). |
| `GET` | `/api/vendas` | Lista as vendas (`200 OK`). |
| `GET` | `/api/vendas/{id}` | Consulta uma venda (`200 OK`, ou `404 Not Found`). |

Vendas não possuem `PUT` nem `DELETE`: uma venda registrada é um fato histórico e não deve ser alterada.

### Exemplo de Venda

Requisição `POST /api/vendas`:

```json
{
  "idCliente": 1,
  "idProduto": 3,
  "quantidade": 2
}
```

Resposta `201 Created` (cabeçalho `Location: http://localhost:8083/api/vendas/1`):

```json
{
  "id": 1,
  "idCliente": 1,
  "idProduto": 3,
  "quantidade": 2,
  "precoUnitario": 8500000.00,
  "total": 17000000.00
}
```

## Respostas de erro

Os erros tratados pela API usam este formato:

```json
{
  "dataHora": "2026-11-24T19:00:00Z",
  "status": 404,
  "erro": "Recurso não encontrado.",
  "mensagem": "Cliente com id 7 não encontrado.",
  "caminho": "/api/vendas"
}
```

Principais códigos:

| Código | Quando ocorre |
| --- | --- |
| `400 Bad Request` | Campos obrigatórios ausentes, quantidade menor ou igual a zero, JSON malformado ou id inválido na URL (ex.: `/api/vendas/abc`). |
| `404 Not Found` | Cliente, produto ou registro de estoque inexistente ao registrar a venda; venda inexistente na consulta por id. |
| `409 Conflict` | Estoque insuficiente para a quantidade solicitada. |
| `503 Service Unavailable` | Clientes, Produtos ou Estoque fora do ar, com falha, ou com resposta inválida. |
| `500 Internal Server Error` | Erro inesperado; a resposta não expõe detalhes internos (o erro é registrado no log). |

## Persistência e dados de demonstração

O serviço usa H2 em memória (`jdbc:h2:mem:vendas`), com banco próprio: não acessa as tabelas dos outros microsserviços. Os dados são perdidos quando o processo é encerrado.

Por padrão o serviço inicia **sem vendas**. Para testar as consultas, há uma carga de demonstração (`config/Config.java`) ativada apenas com o perfil `dev`:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

No Eclipse/IntelliJ, informe `dev` em *Active profiles* na configuração de execução (ou adicione o argumento `--spring.profiles.active=dev`).

São carregadas 4 vendas, usando ids e preços dos dados de demonstração de Clientes e Produtos:

| Cliente | Produto | Quantidade | Preço unitário |
| --- | --- | --- | --- |
| 1 – Tony Stark | 1 – Armadura Mark V | 1 | 8500000.00 |
| 2 – Natasha Romanov | 2 – Escudo do Capitao America | 2 | 25000.00 |
| 3 – Stark Industries | 3 – Mjolnir | 3 | 120000.00 |
| 2 – Natasha Romanov | 6 – Lanca Teia do Homem-Aranha | 5 | 8500.00 |

> Essas vendas são gravadas direto no banco, **sem passar pelo fluxo de registro**: não consultam Clientes e Produtos e não dão baixa no Estoque. Servem apenas para testar `GET /api/vendas` e `GET /api/vendas/{id}`. Para demonstrar a integração entre os serviços, registre vendas com `POST /api/vendas`.

## Organização do código

- `controller`: endpoint REST `/api/vendas` e códigos HTTP.
- `service`: regra de negócio do registro de venda, orquestração das chamadas aos outros serviços e conversão para DTOs.
- `client`: comunicação HTTP com os microsserviços de Clientes, Produtos e Estoque (`RestClient`), traduzindo respostas de erro em exceções do domínio.
- `repository`: acesso aos dados via Spring Data JPA.
- `model`: entidade persistida `Venda`.
- `dto.request` e `dto.response`: contratos de entrada e saída da API.
- `dto.integracao`: contratos usados na comunicação com os outros microsserviços.
- `exceptions`: exceções do domínio, tratamento global (`CapturadorExcecoes`) e formato das respostas de erro.

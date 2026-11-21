# Microsserviço de Vendas

Aplicação Spring Boot independente destinada ao registro de vendas. É executada na porta `8083`.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2
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

- API base: `http://localhost:8083`
- Swagger UI: `http://localhost:8083/swagger-ui.html`
- Console H2: `http://localhost:8083/h2-console`

Para acessar o H2 Console, use a URL JDBC `jdbc:h2:mem:vendas`, usuário `sa` e senha em branco.

## Modelo de dados e validação

| Campo | Tipo JSON | Regras |
| --- | --- | --- |
| --- | --- | --- |

### Rotas implementadas

| Método | Rota | Resultado esperado |
| --- | --- | --- |
| --- | --- | --- |

### Exemplo de Venda

```json
{
  
}
```

## Respostas de erro

| Código | Quando ocorre |
| --- | --- |
| --- | --- |

## Persistência e dados de demonstração

O serviço está configurado com H2 em memória (`jdbc:h2:mem:vendas`). Os dados são perdidos quando o processo é encerrado. Não há carga de dados de demonstração.

## Organização do código

- `controller`: endpoints REST e códigos HTTP.
- `service`: operações de negócio e conversão para DTOs.
- `repository`: acesso aos dados via Spring Data JPA.
- `model`: entidade persistida.
- `dto.request` e `dto.response`: contratos de entrada e saída da API.
- `exception`: tratamento global e formato das respostas de erro.
- `config`: carga inicial de dados de demonstração.

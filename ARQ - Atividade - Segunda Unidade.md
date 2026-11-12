**Modalidade:** Trabalho prático em grupo  
**Disciplina:** Arquitetura de Software  
**Tipo de avaliação:** Desenvolvimento de aplicação funcional  
**Linguagem de programação:** Java  
**Framework:** Spring Boot  
**Ambiente de desenvolvimento:** Eclipse IDE  
**Valor da atividade:** 10,0 pontos

---

### **1\. Objetivo da atividade**

Desenvolver uma aplicação utilizando os conceitos de **Arquitetura de Software**, com foco na implementação de uma arquitetura baseada em **microserviços e comunicação por APIs RESTful**, conforme os conteúdos estudados em sala de aula.

A atividade tem como objetivo avaliar a capacidade dos alunos de projetar, implementar e integrar serviços independentes, aplicar corretamente os princípios de comunicação RESTful, organizar as responsabilidades dos componentes e utilizar os fundamentos de Programação Orientada a Objetos (POO) na construção de uma solução funcional.

Espera-se que os alunos compreendam não apenas como desenvolver uma API RESTful, mas também como estruturar uma aplicação distribuída em serviços com responsabilidades específicas, capazes de se comunicar e trabalhar de forma integrada.

### **2\. Descrição da atividade**

Cada grupo deverá desenvolver uma **aplicação Java completa, funcional e com finalidade prática**, utilizando obrigatoriamente uma arquitetura baseada em microserviços, com comunicação por meio de APIs RESTful.

O grupo poderá escolher livremente o tema da aplicação, desde que o problema proposto justifique a divisão do sistema em serviços independentes e a comunicação entre eles.

Como exemplos de aplicações, poderão ser desenvolvidos:

* Sistema de gerenciamento de pedidos e produtos.  
* Sistema de biblioteca com gerenciamento de livros e empréstimos.  
* Sistema acadêmico com cadastro de alunos, disciplinas e matrículas.  
* Sistema de agendamento de consultas.  
* Sistema de gerenciamento de estoque e vendas.  
* Sistema de reservas e pagamentos simulados.  
* Outras aplicações que apresentem funcionalidades distribuídas entre diferentes serviços.

A aplicação deverá ser dividida em **no mínimo 3 (três) microserviços independentes**, cada um responsável por um conjunto específico de funcionalidades relacionadas ao problema escolhido.

Os microserviços deverão disponibilizar APIs RESTful e possuir comunicação entre si, demonstrando a integração dos componentes da aplicação.

**Importante:** Não será suficiente criar três projetos Java que funcionem isoladamente. Os microserviços deverão fazer parte de uma mesma solução, possuir responsabilidades bem definidas e realizar operações integradas que demonstrem a comunicação entre eles.

### **3\. Requisitos obrigatórios da aplicação**

#### **3.1. Funcionalidade e coerência**

A aplicação deverá:

* Possuir um objetivo claramente definido e resolver um problema específico.  
* Apresentar funcionalidades relacionadas ao contexto escolhido.  
* Ser dividida em, no mínimo, três microserviços.  
* Permitir a execução independente de cada microserviço.  
* Disponibilizar operações por meio de APIs RESTful.  
* Realizar comunicação entre pelo menos dois microserviços.  
* Apresentar um fluxo de funcionamento coerente e integrado.  
* Tratar adequadamente situações de erro e requisições inválidas.  
* Permitir que o professor execute e teste as funcionalidades implementadas.

A aplicação poderá possuir uma interface gráfica ou ser utilizada diretamente por ferramentas de teste de APIs, como Postman ou Insomnia.

Não será obrigatória a implementação de uma interface gráfica, desde que todas as funcionalidades possam ser executadas e demonstradas.

#### **3.2. Arquitetura de microserviços**

A aplicação deverá implementar uma arquitetura distribuída, respeitando os princípios fundamentais de microserviços.

Cada microserviço deverá:

**A. Possuir responsabilidade específica**

Cada serviço deverá representar uma funcionalidade ou domínio de negócio claramente definido.

Exemplo:

Em um sistema de vendas, os serviços poderão ser organizados da seguinte forma:

* **Microserviço de Produtos:** responsável pelo cadastro, consulta, alteração e exclusão de produtos.  
* **Microserviço de Clientes:** responsável pelo gerenciamento das informações dos clientes.  
* **Microserviço de Pedidos:** responsável pelo registro e gerenciamento de pedidos, utilizando informações disponibilizadas pelos outros serviços.

**B. Possuir execução independente**

Cada microserviço deverá:

* Ser implementado como uma aplicação Spring Boot independente.  
* Possuir sua própria configuração.  
* Executar em uma porta específica.  
* Disponibilizar seus próprios endpoints RESTful.  
* Poder ser iniciado e interrompido separadamente dos demais serviços.

**C. Apresentar baixo acoplamento**

Os microserviços deverão possuir responsabilidades bem definidas e evitar dependências desnecessárias.

Não será permitido que um microserviço acesse diretamente as classes internas ou os repositórios de outro microserviço para executar operações de negócio.

A comunicação entre os serviços deverá ocorrer por meio de interfaces de comunicação claramente definidas.

**D. Realizar comunicação entre microserviços**

A aplicação deverá implementar pelo menos uma funcionalidade que exija comunicação entre dois ou mais microserviços.

Essa comunicação poderá ser realizada utilizando recursos do Spring, como `RestClient`, `WebClient` ou outra solução HTTP apropriada.

Exemplo:

Ao cadastrar um pedido, o Microserviço de Pedidos deverá consultar o Microserviço de Produtos para verificar se o produto informado existe.

A operação deverá utilizar uma requisição HTTP para obter as informações necessárias.

**Atenção:** A comunicação deverá ser real e funcional. Não serão aceitos valores fixos utilizados para simular respostas que deveriam ser obtidas de outro microserviço.

### **4\. Implementação de APIs RESTful**

Cada microserviço deverá disponibilizar uma API que respeite os princípios fundamentais da arquitetura REST.

#### **4.1. Utilização dos métodos HTTP**

A aplicação deverá implementar corretamente os métodos HTTP:

| Método | Finalidade | Exemplo |
| :---- | :---- | :---- |
| GET | Consultar recursos | `GET /produtos` |
| GET | Consultar um recurso específico | `GET /produtos/10` |
| POST | Criar um novo recurso | `POST /produtos` |
| PUT | Atualizar um recurso existente | `PUT /produtos/10` |
| DELETE | Excluir um recurso | `DELETE /produtos/10` |

As operações deverão respeitar a finalidade de cada método HTTP.

Não será permitido utilizar requisições GET para realizar cadastros, alterações ou exclusões de informações.

#### **4.2. Organização dos endpoints**

Os endpoints deverão:

* Utilizar URLs claras e representativas dos recursos.  
* Preferencialmente utilizar substantivos para identificar recursos.  
* Utilizar parâmetros de caminho quando necessário.  
* Receber e retornar informações em formato JSON.  
* Manter um padrão consistente de nomenclatura.  
* Respeitar o comportamento esperado dos métodos HTTP.

Exemplos de endpoints adequados:

`GET /clientes`

`GET /clientes/5`

`POST /clientes`

`PUT /clientes/5`

`DELETE /clientes/5`

#### **4.3. Códigos de resposta HTTP**

As APIs deverão utilizar códigos HTTP apropriados para indicar o resultado das operações.

| Código | Significado | Situação esperada |
| :---- | :---- | :---- |
| 200 | OK | Consulta ou operação realizada com sucesso |
| 201 | Created | Recurso criado com sucesso |
| 204 | No Content | Operação concluída sem conteúdo de resposta |
| 400 | Bad Request | Dados inválidos enviados pelo cliente |
| 404 | Not Found | Recurso solicitado não encontrado |
| 409 | Conflict | Conflito com o estado atual do recurso |
| 500 | Internal Server Error | Erro interno inesperado |

A aplicação deverá apresentar respostas coerentes para situações de sucesso e falha, evitando retornar sempre o código HTTP 200, independentemente do resultado.

#### **4.4. Tratamento de erros**

As APIs deverão:

* Validar os dados recebidos.  
* Tratar requisições com informações inválidas.  
* Identificar recursos inexistentes.  
* Tratar falhas de comunicação entre microserviços.  
* Retornar mensagens de erro claras e códigos HTTP adequados.  
* Evitar apresentar detalhes internos desnecessários da implementação nas respostas.

### **5\. Organização interna e fundamentos de POO**

Os microserviços deverão ser desenvolvidos respeitando os fundamentos de Programação Orientada a Objetos e a separação de responsabilidades.

Cada microserviço deverá apresentar uma organização em camadas, contemplando, quando aplicável:

**A. Controller**

Responsável por receber as requisições HTTP, validar os dados de entrada e encaminhar as operações para a camada de serviço.

**B. Service**

Responsável pela implementação das regras de negócio e pela coordenação das operações necessárias ao funcionamento do microserviço.

**C. Repository**

Responsável pelo acesso e pela persistência das informações utilizadas pela aplicação.

**D. Model/Entity**

Responsável por representar os objetos e as entidades pertencentes ao domínio da aplicação.

**E. DTO (Data Transfer Object)**

Responsável pela transferência de informações entre as camadas e pelas requisições e respostas das APIs, quando necessário.

A aplicação deverá respeitar os fundamentos de POO, especialmente:

* Classes e objetos.  
* Atributos, métodos e construtores.  
* Encapsulamento.  
* Abstração.  
* Interfaces e suas implementações.  
* Herança e polimorfismo, quando aplicáveis.  
* Separação de responsabilidades.  
* Baixo acoplamento e alta coesão.

**Importante:** Não serão aceitas aplicações em que todas as regras de negócio estejam concentradas nas classes Controller, sem uma organização adequada das responsabilidades.

### **6\. Persistência de dados**

A aplicação deverá utilizar mecanismos de persistência para armazenar e recuperar as informações necessárias ao funcionamento do sistema.

Cada microserviço deverá possuir autonomia sobre os dados de seu domínio.

Poderão ser utilizados bancos de dados relacionais, como MySQL, PostgreSQL ou H2, ou outras tecnologias adequadas ao projeto.

Os microserviços não deverão acessar diretamente as tabelas ou coleções pertencentes a outros serviços. Quando necessitarem de informações de outro domínio, deverão utilizar a API disponibilizada pelo microserviço responsável.

### **7\. Exemplo de funcionamento esperado**

Considere uma aplicação de gerenciamento de vendas composta por três microserviços:

**Microserviço 1 – Clientes**

Responsável por cadastrar, consultar, atualizar e excluir clientes.

**Microserviço 2 – Produtos**

Responsável por cadastrar, consultar, atualizar e excluir produtos.

**Microserviço 3 – Pedidos**

Responsável por registrar e consultar pedidos realizados pelos clientes.

Ao realizar o cadastro de um pedido, o sistema deverá executar o seguinte fluxo:

1. O usuário envia uma requisição `POST /pedidos`, informando o cliente e os produtos desejados.  
2. O Microserviço de Pedidos recebe a requisição.  
3. O Microserviço de Pedidos consulta a API do Microserviço de Clientes para verificar se o cliente existe.  
4. Em seguida, consulta a API do Microserviço de Produtos para verificar os produtos informados.  
5. Após validar as informações, registra o pedido.  
6. A API retorna uma resposta HTTP adequada, contendo os dados do pedido criado.

Caso o cliente ou algum produto não exista, a aplicação deverá impedir o cadastro e retornar uma resposta de erro apropriada.

Esse exemplo demonstra uma situação em que diferentes microserviços trabalham de forma integrada para executar uma operação de negócio.

O grupo poderá desenvolver outra solução, desde que apresente integração equivalente entre os serviços.

### **8\. Ambiente de desenvolvimento e organização dos projetos**

O desenvolvimento deverá ser realizado **obrigatoriamente em Java, utilizando Spring Boot e a IDE Eclipse**.

Cada microserviço deverá ser organizado como um projeto independente, seguindo a estrutura convencional de projetos Spring Boot no Eclipse.

O trabalho deverá:

* Conter pelo menos três projetos Spring Boot independentes.  
* Utilizar Maven ou Gradle para gerenciamento de dependências.  
* Possuir pacotes organizados conforme as responsabilidades das classes.  
* Implementar endpoints RESTful funcionais.  
* Utilizar portas distintas para cada microserviço.  
* Possuir arquivos de configuração apropriados.  
* Disponibilizar instruções para inicialização de todos os serviços.  
* Estar configurado para importação, compilação e execução no Eclipse.

**Não serão aceitos apenas arquivos Java isolados ou um único projeto monolítico dividido em três pacotes e apresentado como se fossem microserviços.**

### **9\. Entregáveis**

Cada grupo deverá entregar:

**A. Projetos completos**

Todos os projetos Spring Boot desenvolvidos no Eclipse, incluindo código-fonte, configurações e dependências.

**B. Documentação técnica**

Um documento contendo:

1. Nome da aplicação e identificação dos integrantes.  
2. Descrição do problema que a aplicação pretende resolver.  
3. Objetivo e principais funcionalidades.  
4. Descrição de cada microserviço e suas responsabilidades.  
5. Explicação da comunicação entre os microserviços.  
6. Relação dos endpoints implementados, incluindo métodos HTTP e finalidades.  
7. Descrição da organização em camadas.  
8. Tecnologias e bancos de dados utilizados.  
9. Instruções para configurar e executar a aplicação no Eclipse.

**C. Diagrama de arquitetura**

Um diagrama que apresente os microserviços desenvolvidos, suas responsabilidades, os mecanismos de comunicação e os respectivos bancos de dados.

**D. Diagrama de classes UML**

Um diagrama que represente as principais classes e os relacionamentos de pelo menos um dos microserviços.

**E. Evidências de funcionamento**

Uma coleção de requisições do Postman, Insomnia ou ferramenta equivalente, contendo exemplos de operações realizadas e seus respectivos resultados.

### **10\. Apresentação e demonstração**

Cada grupo deverá apresentar a aplicação desenvolvida e realizar uma demonstração prática de seu funcionamento.

Durante a apresentação, os integrantes deverão:

* Explicar o problema escolhido e o objetivo da aplicação.  
* Apresentar a arquitetura e as responsabilidades dos microserviços.  
* Inicializar os serviços de forma independente.  
* Demonstrar os endpoints RESTful implementados.  
* Executar operações de cadastro, consulta, atualização e exclusão.  
* Demonstrar uma operação que envolva comunicação entre microserviços.  
* Apresentar os códigos de resposta HTTP utilizados.  
* Demonstrar o tratamento de erros e requisições inválidas.  
* Explicar a organização das classes e das camadas.  
* Responder a questionamentos técnicos relacionados à arquitetura desenvolvida.

**Todos os integrantes deverão conhecer a estrutura e o funcionamento geral da aplicação**, independentemente da divisão de tarefas realizada durante o desenvolvimento.

### **11\. Critérios de avaliação**

A atividade valerá **10,0 (dez) pontos**, 

**12\. Orientações e regras gerais**

1. A aplicação deverá ser desenvolvida pelo próprio grupo.  
2. O sistema deverá possuir no mínimo três microserviços independentes e funcionais.  
3. Os microserviços deverão possuir responsabilidades claramente definidas.  
4. A comunicação entre serviços deverá ocorrer por meio de APIs RESTful.  
5. As APIs deverão respeitar os métodos e os códigos de resposta HTTP.  
6. A aplicação deverá implementar operações reais de negócio, não sendo suficiente apenas disponibilizar endpoints sem funcionalidades.  
7. Os microserviços deverão utilizar corretamente os fundamentos de POO e a organização em camadas.  
8. Não será permitido utilizar um único projeto monolítico e apresentá-lo como uma arquitetura de microserviços.  
9. A aplicação deverá tratar adequadamente erros de validação e falhas de comunicação.  
10. Os projetos deverão compilar e executar corretamente no Eclipse.  
11. Todos os integrantes deverão participar do desenvolvimento e demonstrar conhecimento sobre a solução apresentada.

### **13\. Resultado esperado**

Ao final da atividade, cada grupo deverá apresentar uma aplicação Java funcional, desenvolvida no Eclipse com Spring Boot, composta por pelo menos três microserviços independentes que se comuniquem por meio de APIs RESTful.

A solução deverá demonstrar a correta distribuição das responsabilidades, a integração entre os serviços, o respeito aos princípios RESTful e a utilização adequada dos fundamentos de Programação Orientada a Objetos.

**O principal objetivo da avaliação não será apenas criar três APIs independentes, mas demonstrar a capacidade de projetar e implementar uma aplicação distribuída, organizada, funcional e tecnicamente coerente, aplicando os conceitos de Arquitetura de Software estudados em sala de aula.**


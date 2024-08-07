# Desafio de Programação em Java Quarkus - Microsserviço de Recebimento e Armazenamento de Pagamento com Cartão de Crédito

**Descrição:** Você foi designado para desenvolver um microsserviço em Java Quarkus que recebe dados de um pagamento fictício realizado com cartão de crédito e armazena essas informações. O microsserviço deve validar se os dados recebidos estão no formato correto e, em seguida, persistir essas informações em um banco de dados relacional. Além disso, o microsserviço deve fornecer um endpoint HTTP para receber os dados do pagamento e um mecanismo para consulta posterior desses dados armazenados.

### Requisitos Técnicos:

Utilize o framework Quarkus para criar o microsserviço Java.

- Crie uma função que receba os dados do pagamento (em formato JSON) e valide se eles estão no formato esperado antes de prosseguir.
- Utilize um banco de dados relacional (por exemplo, PostgreSQL ou MySQL) para armazenar os dados do pagamento. Crie uma tabela adequada para armazenar essas informações.
- Implemente um endpoint HTTP (por exemplo, utilizando JAX-RS) que permita o envio dos dados do pagamento para o microsserviço.
- Forneça uma funcionalidade adicional para consultar os dados de pagamento armazenados no banco de dados.

### Instruções:

- O candidato deve criar um repositório Git para o projeto e fornecer o link quando concluir. (sugestão: GitHub)
- Certifique-se de que os dados de pagamento sejam armazenados corretamente no banco de dados após a validação.

### Opcional:

- O candidato poderá entregar configurações para rodar o microsserviço em container (Dockerfile, docker-compose...)
- O candidato poderá disponibilizar no código a geração de uma métrica Prometheus através de um endpoint com a assinatura `/metrics`. Pode ser qualquer tipo de métrica (contador, histogram, summary ou gauge)  para ser aplicado nos próprios endpoints implementados.

### Avaliação:

- Será avaliado a qualidade do código, a organização, eficiência da solução e boas práticas de programação. Também será observado o cumprimento dos requisitos do desafio.
- Certifique-se de verificar se o microsserviço valida os dados de pagamento, os armazena corretamente no banco de dados e fornece uma maneira de consultar esses dados posteriormente.

___
## Estrutura Geral do Projeto

1. **Classe Cartao:** Representa o modelo de dados.
2. **Classe CartaoRepository:** Interface para operações de persistência com o banco de dados.
3. **Classe CartaoService:**  Contém a lógica de negócios.
4. **Classe CartaoResource:** Define os endpoints HTTP e a interface com o cliente.

## Tecnologias

- Java 17
- Maven
- MySQL
- Quarkus Framework

### Como realizar instalação:

Clonar o repositório:

````
~$ https://github.com/ilamuniz/desafio-pagamentos.git
````
Entrar na pasta raiz:

````
~$ cd desafio-pagamentos
````
Em seguida, executar o comando para gerar o .jar:

````
~$ mvn install quarkus:dev
````
Caso o projeto já esteja instalado basta usar **mvn compile quarkus:dev**.

A aplicação irá inicializar na porta 8080.

_Endpoint:_ http://localhost:8080/pagamentos

## Banco de Dados

Configurações do banco MySQL no arquivo ***src/main/resources/application.properties***.

### Script para criação do banco MySQL

````
CREATE TABLE `Cartao` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nome` VARCHAR(50) NOT NULL,
	`cpf` VARCHAR(14) NOT NULL,
	`pagamento` decimal(6,2) NOT NULL,
	`nome_do_titular` VARCHAR(50) NOT NULL,
	`numero_do_cartao` VARCHAR(16) NOT NULL,
	`data_de_expiracao` datetime NOT NULL,
	`codigo_de_seguranca` int NOT NULL,
	PRIMARY KEY(`id`)) ENGINE=InnoDB;
````

**Obs.:** Está configurado também um banco de dados H2 que será usado para testes.

## Enviando dados para o banco de dados (Métodos HTTP):

_NOTA:_ Teste realizado no Insomnia.

### Cadastrando dados (**POST**)

As requisições enviadas para a API via método _POST_ devem ser em formato _JSON_. Exemplo de requisição enviada para a API:

````
{
  "nome": "João",
  "cpf": "99999999999",
  "pagamento": 250.50,
  "nomeTitular": "João Pedro",
  "numeroDoCartao": "12345678910",
  "dataDeExpiracao": "2028-08-01T00:00:00Z",
  "codigoDeSeguranca": 123
}
````
Como resposta, deve ser retornado o status **201 - Created**. Ao fazer uma requisição _GET_ logo em seguida, ele deve retornar o status **200** e o seguinte resultado:

````
[
	{
		"codigoDeSeguranca": 123,
		"cpf": "99999999999",
		"dataDeExpiracao": "2028-08-01T00:00:00Z[UTC]",
		"id": 1,
		"nome": "João",
		"nomeTitular": "João Pedro",
		"numeroDoCartao": "12345678910",
		"pagamento": 250.50
	}
]
````

### Consultando dados cadastrados (**GET**)

Podemos inserir mais um novo conjunto de dados pelo _POST_ como no exemplo a seguir:

````
{
  "nome": "Maria",
  "cpf": "11111111111",
  "pagamento": 380.00,
  "nomeTitular": "Maria Sousa",
  "numeroDoCartao": "10123456789",
  "dataDeExpiracao": "2032-09-01T00:00:00Z",
  "codigoDeSeguranca": 456
}
````
Retornando com status **201** . Ao fazer uma nova requisição _GET_, o resultado esperado deve ser:

````
[
	{
		"codigoDeSeguranca": 123,
		"cpf": "99999999999",
		"dataDeExpiracao": "2028-08-01T00:00:00Z[UTC]",
		"id": 1,
		"nome": "João",
		"nomeTitular": "João Pedro",
		"numeroDoCartao": "12345678910",
		"pagamento": 250.50
	},
	{
		"codigoDeSeguranca": 456,
		"cpf": "11111111111",
		"dataDeExpiracao": "2032-09-01T00:00:00Z[UTC]",
		"id": 2,
		"nome": "Maria",
		"nomeTitular": "Maria Sousa",
		"numeroDoCartao": "10123456789",
		"pagamento": 380.00
	}
]
````

### Consultando dados por ID (**GET**)

Podemos fazer uma busca por id, passando o endereço do endpoint e incluir no final o ID do pagamento cadastrado que se deseja buscar com o método _GET_. No exemplo, o ID buscado é o 1:

````
http://localhost:8080/pagamentos/1
````
A resposta esperada deve ser semelhante a seguinte:

````
{
	"codigoDeSeguranca": 123,
	"cpf": "99999999999",
	"dataDeExpiracao": "2028-08-01T00:00:00Z[UTC]",
	"id": 1,
	"nome": "João",
	"nomeTitular": "João Pedro",
	"numeroDoCartao": "12345678910",
	"pagamento": 250.50
}
````

### Deletando dados (**DELETE**)

Para deletar um pagamento, fazemos un procedimento parecido para buscar por ID, inserindo a URL http://localhost:8080/pagamentos/{id}, substituindo o {id} pelo ID que se deseja deletar, porém escolhendo o método DELETE, como no exemplo:

````
http://localhost:8080/pagamentos/1
````
Como resposta, retornará o status **204 - No content**. Ao fazer uma nova requisição _GET_, deve retornar todos os dados, exceto o pagamento que foi excluído, como no exemplo a seguir:

````
[
	{
		"codigoDeSeguranca": 456,
		"cpf": "11111111111",
		"dataDeExpiracao": "2032-09-01T00:00:00Z[UTC]",
		"id": 2,
		"nome": "Maria",
		"nomeTitular": "Maria Sousa",
		"numeroDoCartao": "10123456789",
		"pagamento": 380.00
	}
]
````

## Status Codes

Possíveis status esperados como resposta das requisições:

````
200 (OK) = requisição bem sucedida
201 (Created) = requisição bem sucedida e algo foi criado
204 (No Content) = requisição bem sucedida, sem conteúdo no corpo da resposta
400 (Bad Request) = o servidor não entendeu a requisição pois está com uma sintaxe/formato inválido
401 (Unauthorized) = o usuário não está autenticado (logado)
403 (Forbidden) = o usuário não tem permissão de acessar o recurso solicitado
404 (Not Found) = o servidor não pode encontrar o recurso solicitado
500 (Internal Server Error) = erro inesperado do servidor
````
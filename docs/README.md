# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Docker
Para subir a aplicação basta ter o Docker instalado e seguir os procedimentos:
1. No arquivo `application.properties` incluir o usuário e senha do banco:

`spring.datasource.username=**your_user**`

`spring.datasource.password=**your_pass**`

2. No terminal executar o comando:
```sh
MYSQL_ROOT_PASSWORD=**your_pass** docker-compose up
```
Sobre o comando acima:
- Cria a imagem e container da aplicação, gerando o jar atualizado.
- Cria a imagem e container do banco de dados MYSQL.

## Executar a aplicação

Curl de exemplo: Busca de cliente por cpf
```sh
curl --location 'http://localhost:8080/clientes/cpf/111.111.11111'
```

  ![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/blob/develop/docs/assets/Exemplo_imagem_banco.png?raw=true)
  
  ![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/blob/develop/docs/assets/Exemplo_imagem_endpoint.png?raw=true)

## Executar swagger
```sh
curl --location 'http://localhost:8080/swagger-ui/index.html'
```

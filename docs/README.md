# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Docker
Para subir a aplicação basta ter o Docker instalado e seguir os procedimentos abaixo.

No terminal executar o comando:
```sh
MYSQL_ROOT_USERNAME=root MYSQL_ROOT_PASSWORD=your_password MERCADO_PAGO_USER_ID=user_id_mercado_pago MERCADO_PAGO_EXTERNAL_ID=external_id_mercado_pago MERCADO_PAGO_TOKEN=mercado_pago_token docker-compose up --build
```
**Observações:**
Para executar o comando você poderá incluir uma senha de sua preferência, junto ao user root.

Sobre o comando acima:
- Cria a imagem e container da aplicação, gerando o jar atualizado.
- Cria a imagem e container do banco de dados MYSQL.

## Executar a aplicação

Curl de exemplo: Busca de cliente por cpf
```sh
curl --location 'http://localhost:8080/clientes/cpf/111.111.111-11'
```

  ![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/blob/develop/docs/assets/Exemplo_imagem_banco.png?raw=true)
  
  ![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/blob/develop/docs/assets/Exemplo_imagem_endpoint.png?raw=true)

## Executar swagger
```sh
curl --location 'http://localhost:8080/swagger-ui/index.html'
```

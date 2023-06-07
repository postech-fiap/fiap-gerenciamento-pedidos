# fiap-gerenciamento-pedidosx

## Docker
Comandos para criar imagem e subir os containers

**Criar a imagem do banco de dados MYSQL**
```sh
docker build -t gerenciamento-pedidos-db -f Dockerfile.db .
```

**Criar a imagem da aplicação Java**
```sh
docker build -t gerenciamento-pedidos-app -f Dockerfile.app .
```

**Criar o container usando a imagem do MYSQL dentro de uma rede (network) **
```sh
docker container run 
--name mysqldb-container
--network gerenciamento-pedidos-mysql 
-e MYSQL_ROOT_PASSWORD=12345 -e MYSQL_DATABASE=gerenciamento_pedidos_db 
-p 3306:3306 -d gerenciamento-pedidos-db
```

**Criar o container usando a imagem da aplicação Java dentro da mesma rede do MYSQL **
```sh
docker container run 
--name gerenciamento-pedidos-app-container
--network gerenciamento-pedidos-mysql 
-p 8080:8080
-d gerenciamento-pedidos-app
```

## Executar a aplicação
```sh
curl --location 'http://localhost:8080/ping'
```
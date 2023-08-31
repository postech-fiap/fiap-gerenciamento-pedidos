# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Docker
Para subir a aplicação basta ter o Docker instalado e seguir os procedimentos abaixo.

No terminal executar o comando:
```sh
MYSQL_ROOT_USERNAME=root MYSQL_ROOT_PASSWORD=your_password docker-compose up --build
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

## Kubernetes

Com o `kubectl` instalado, execute os seguintes comandos:

```bash
# Criar Secrets
kubectl create secret generic db --from-literal=username=CHANGE_HERE --from-literal=password=CHANGE_HERE
kubectl create secret generic mp --from-literal=user-id=CHANGE_HERE --from-literal=external-id=CHANGE_HERE --from-literal=token=CHANGE_HERE

# Criar Config Maps
kubectl apply -f kubernetes/config/db.yaml
kubectl apply -f kubernetes/config/db-migration.yaml

# Criar Pod com Banco de dados
kubectl apply -f kubernetes/db/pod.yaml

# Criar Service ClusterIP do Banco de dados
kubectl apply -f kubernetes/db/service.yaml

# [Opcional] Criar Service Nodeport do banco de dados
# Caso queira acessar o banco de dados fora do Cluster
kubectl apply -f kubernetes/db/nodeport.yaml

# Criar Deployment da api
kubectl apply -f kubernetes/api/deployment.yaml

# Criar Service Nodeport da api
kubectl apply -f kubernetes/api/service.yaml
```

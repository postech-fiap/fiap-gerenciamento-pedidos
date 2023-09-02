# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Executando com Kubernetes (recomendado)

Com o `kubectl` instalado, execute os seguintes comandos:

```bash
# Criar Secrets
kubectl create secret generic db --from-literal=username=root --from-literal=password=<YOUR_PASSWORD>
kubectl create secret generic mp --from-literal=user-id=<MERCADO_PAGO_USER_ID> --from-literal=external-id=<MERCADO_PAGO_EXTERNAL_ID> --from-literal=token=<MERCADO_PAGO_TOKEN> --from-literal=webhook-url=<MERCADO_PAGO_WEBHOOK_URL>

# Criar Load Balancer
kubectl apply -f kubernetes/api/load-balancer-service.yaml

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

### Variáveis de ambiente

- Para executar o comando você poderá incluir uma senha de sua preferência, junto ao user root.
- As demais variáveis estão presentes no documento que será enviado por anexo.

### Executar swagger
```sh
curl --location 'http://localhost:30000/swagger-ui/index.html'
```

### Executar swagger (load balancer)
```sh
curl --location 'http://localhost:8080/swagger-ui/index.html'
```

## [Integração com Mercado Pago Api](README-MERCADOPAGO.md)

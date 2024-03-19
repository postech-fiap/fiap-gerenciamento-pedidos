# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Padrão SAGA Coreografada
A justificativa da nossa escolha está diretamente relacionada à simplicidade do nosso fluxo, pois não temos um fluxo muito complexo e o padrão coreografado foi mais simples de ser implementado e nossa decisão foi baseada na eficiência do desenvolvimento.
Um outro fator relevante é que nossos microsserviços estão com suas respectivas responsabilidades bem divididas e a implementação de um orquestrador agregaria uma camada de complexidade muitas vezes desnecessária em nosso entendimento. E dessa forma foi mais fácil trabalharmos com o padrão coreografia, pois os microsserviços conseguiram administrar muito bem os fluxos pelos quais eles foram desenvolvidos e em termos de adaptação do código foi bastante tranquilo em substituir os adapter de requisição REST para os de mensageria.

### Desenho da arquitetura
![arquitetura.png](assets%2Farquitetura.png)

## Relatórios OWASP ZAP
Acessar o link no documento compartilhado.

## Executando com Kubernetes (recomendado)

Com o `kubectl` instalado, execute os seguintes comandos:

```bash
# Criar Secrets
kubectl create secret generic db --from-literal=username=root --from-literal=password=<YOUR_PASSWORD>

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
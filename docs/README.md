# fiap-gerenciamento-pedidos

Aplicação responsável pela automatização e gerenciamento de pedidos de uma lanchonete.

## Docker
Para subir a aplicação basta ter o Docker instalado e seguir os procedimentos abaixo.

No terminal executar o comando:
```sh
MYSQL_ROOT_USERNAME=root MYSQL_ROOT_PASSWORD=your_password MERCADO_PAGO_USER_ID=user_id_mercado_pago MERCADO_PAGO_EXTERNAL_ID=external_id_mercado_pago MERCADO_PAGO_TOKEN=mercado_pago_token MERCADO_PAGO_WEBHOOK_URL=webhook_url docker-compose up --build
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

## Integração com Mercado Pago Api

Para criar um pedido e obter o qr_code de pagamento, você precisará gerar uma webhook de testes.

**Obs.:** 
- O endpoint que deve ser chamado na notification_url (webhook) foi desenvolvido nessa api, porém como não o temos produtivo, iremos usar um site gerador de webhooks para acompanhar as notificações.
- As credenciais de acesso do Mercado Pago estarão presentes no documento que será enviado por anexo.

1. Crie uma webhook de teste, usando o https://webhook.site/, inclua a url como valor da variável de ambiente: $MERCADO_PAGO_WEBHOOK_URL
![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/7d6ea464-9dbd-489c-9165-ee017e7c49ac)

2. Crie um pedido com qr code, conforme exemplo abaixo:

```sh
curl --location 'http://localhost:8080/pedidos' \
--header 'accept: application/json' \
--header 'Content-Type: application/json' \
--data '{
"cliente_id": 1,
"produtos": [
{
"produto_id": 1,
"quantidade": 1,
"comentario": "Sem salada"
}
]
}'
```

![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/e20c41da-6340-4418-b845-fce61fc873a1)
![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/ca306308-953c-4c2a-84f9-aaa4663bfa5a)

3. Copie e cole o código qr em um site gerador da imagem, como o [qr-code-generator](https://br.qr-code-generator.com/?gclid=Cj0KCQjw9MCnBhCYARIsAB1WQVWcR0NBJ1ae95E9Tt6s80ivJgKft-fVGP3lRg2gGB2joLjIX1avA84aAsq3EALw_wcB&campaignid=11082198394&adgroupid=108043714225&cpid=77ac2822-3c22-44e6-8a6d-96789d7204a4&gad=1)

![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/d326d98b-47e3-4e65-9b97-42fa8c92116a)
**Lembre-se: O qr code expira em 1 hora**

4. Faça o login com usuário e senha de teste do Mercado Pago e realize o pagamento

**Obs.:** Os acessos estarão no documento em anexo compartilhado

5. Você poderá acompanhar o status do pagamento no próprio site do webhook, como na imagem de exemplo abaixo, onde ao realizar o pagamento, o Mercado Pago enviou a notificação tanto de criação do pedido quanto de pagamento:

![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/cdc13aa8-bd08-48ee-bd43-95ae463f2952)
![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/c1049604-4981-4c81-8f1c-543a2818230c)
![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/25de04bd-742d-4d4c-a115-7255f33735f7)

6. Com o id do pagamento (data.id), que foi gerado pelo Mercado Pago, você poderá chamar o endpoint que criamos para finalizar o pagamento / pedido
**Obs.:** Como mecionado no início, este endpoint é o que seria o configurável como webhook para o próprio Mercado Pago chamar nossa aplicação e atualizar o pagamento / pedido automaticamente.
Ao passar o id do pagamento, é realizada uma busca para validar o status do pagamento e atualizar na nossa base de dados.

**Regra de status:**
Pagamento: PENDENTE -> APROVADO ou REPROVADO
Pedido: PENDENTE -> RECEBIDO ou PENDENTE, caso o pagamento não tenha sido aprovado.

Exemplo:
```sh
curl --location 'http://localhost:8080/pagamentos/finalizar?data.id=62849377001&type=payment' \
--header 'Content-Type: application/json' \
--data '{
"action": "payment.created",
"api_version": "v1",
"data": { "id":"62849377001" },
"date_created": "2023-08-28T18:11:04Z",
"id": 107281993234,
"live_mode": true,
"type": "payment",
"user_id": "1443012156"
}'
```

![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/eecf10bf-d8fb-494c-8340-487cd428f7f0)

Você também pode consultar mais detalhes na api do Mercado Pago pelo id do pagamento

![Alt text](https://github.com/Everton91Almeida/fiap-gerenciamento-pedidos/assets/42806807/c0366b90-0497-4e8f-a39b-64722296af42)

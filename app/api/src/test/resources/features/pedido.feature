# language: pt
Funcionalidade: Pedido

#  Cenario: Criar Pedido
#    Dado o produto escolhido
#    Quando for solicitado a criação de um pedido
#    Entao deve retornar o pedido criado com sucesso

  Cenario: Criar Pedido produto inexistente
    Quando for solicitado a criação de um pedido
    Entao deve retornar erro por não encontrar o produto escolhido
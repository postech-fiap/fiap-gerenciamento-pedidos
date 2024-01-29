# language: pt
Funcionalidade: Pedido

  Cenario: Criar Pedido
    Dado os produtos escolhidos para o pedido
    Quando o pedido for criado
    Entao deve retornar o pedido criado com sucesso

#  Cenario: Alterar status do pedido
#    Dado o produto escolhido
#    Quando for solicitado a criação de um pedido
#    E for solicitado a alteração do estatus de um pedido
#    Entao deve retornar sucesso na alteração do status do pedido

  Cenario: Criar Pedido produto inexistente
    Quando os produtos escolhidos para o pedido
    Entao deve retornar erro por não encontrar o produto escolhido
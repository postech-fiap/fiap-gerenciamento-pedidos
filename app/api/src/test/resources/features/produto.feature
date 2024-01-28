# language: pt
Funcionalidade: Produto

  Cenario: Criar Produto
    Quando for solicitado a criação de um produto
    Entao deve retornar o produto disponível e não excluído

  Cenario: Editar Produto
    Quando for solicitado a edição de um produto
    Entao deve retornar o produto com os dados alterados
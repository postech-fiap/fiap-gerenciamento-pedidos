# language: pt
Funcionalidade: Produto

  Cenario: Criar produto
    Quando for solicitado a criação de um produto
    Entao deve retornar o produto disponível e não excluído

  Cenario: Criar produto sem atributos
    Quando for solicitado a criação de um produto sem atributos
    Entao deve retorar erro de validação

  Cenario: Editar produto
    Quando for solicitado a edição de um produto
    Entao deve retornar o produto com os dados alterados
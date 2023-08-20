package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface CadastrarProdutoUseCase {
    fun executar(request: Produto): Produto
}

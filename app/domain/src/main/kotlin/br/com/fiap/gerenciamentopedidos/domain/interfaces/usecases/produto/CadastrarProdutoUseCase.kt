package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface CadastrarProdutoUseCase {
    fun executar(request: Produto): Produto
}

package br.com.fiap.gerenciamentopedidos.application.produto.interfaces

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface ObterProdutoPorIdUseCase {
    fun executar(id: Long): ProdutoResponse
}

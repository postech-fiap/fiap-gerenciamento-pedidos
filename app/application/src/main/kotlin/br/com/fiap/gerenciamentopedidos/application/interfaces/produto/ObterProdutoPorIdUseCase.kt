package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface ObterProdutoPorIdUseCase {
    fun executar(id: Long): ProdutoResponse
}

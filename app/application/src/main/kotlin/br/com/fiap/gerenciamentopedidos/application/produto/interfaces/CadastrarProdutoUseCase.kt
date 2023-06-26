package br.com.fiap.gerenciamentopedidos.application.produto.interfaces

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface CadastrarProdutoUseCase {
    fun executar(request: CadastrarProdutoRequest): ProdutoResponse
}

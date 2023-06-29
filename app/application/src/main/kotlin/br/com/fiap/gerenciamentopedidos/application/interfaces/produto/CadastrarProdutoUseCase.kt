package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface CadastrarProdutoUseCase {
    fun executar(request: CadastrarProdutoRequest): ProdutoResponse
}

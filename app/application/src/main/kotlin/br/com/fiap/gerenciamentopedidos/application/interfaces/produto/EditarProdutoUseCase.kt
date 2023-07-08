package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface EditarProdutoUseCase {
    fun executar(request: EditarProdutoRequest): ProdutoResponse
}

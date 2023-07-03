package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse

interface EditarProduto {
    fun executar(request: EditarProdutoRequest): ProdutoResponse
}

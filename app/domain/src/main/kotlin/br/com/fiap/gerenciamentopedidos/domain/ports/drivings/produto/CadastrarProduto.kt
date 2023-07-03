package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse

interface CadastrarProduto {
    fun executar(request: CadastrarProdutoRequest): ProdutoResponse
}

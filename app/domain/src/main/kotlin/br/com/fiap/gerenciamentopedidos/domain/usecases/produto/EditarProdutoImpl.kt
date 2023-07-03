package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.EditarProduto

class EditarProdutoImpl(private val produtoPort: ProdutoPort) : EditarProduto {
    override fun executar(request: EditarProdutoRequest) =
        ProdutoResponse(produtoPort.update(ProdutoDto.fromModel(request.toModel())).toModel())
}

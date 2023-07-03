package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.CadastrarProduto

class CadastrarProdutoImpl(private val produtoPort: ProdutoPort) : CadastrarProduto {
    override fun executar(request: CadastrarProdutoRequest) =
        ProdutoResponse(produtoPort.create(ProdutoDto.fromModel(request.toModel())).toModel())
}

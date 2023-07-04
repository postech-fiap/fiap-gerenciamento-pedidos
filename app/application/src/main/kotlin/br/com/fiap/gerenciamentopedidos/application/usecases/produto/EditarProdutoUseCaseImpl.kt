package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class EditarProdutoUseCaseImpl(private val produtoPort: ProdutoRepository) : EditarProdutoUseCase {
    override fun executar(request: EditarProdutoRequest) =
        ProdutoResponse(produtoPort.update(ProdutoDto.fromModel(request.toDomain())))
}

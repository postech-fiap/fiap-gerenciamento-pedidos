package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class CadastrarProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : CadastrarProdutoUseCase {
    override fun executar(request: CadastrarProdutoRequest) =
        ProdutoResponse(produtoRepository.create(ProdutoDto.fromModel(request.toModel())))
}

package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class CadastrarProdutoUseCaseImpl(private val repository: ProdutoRepository) : CadastrarProdutoUseCase {
    override fun executar(request: CadastrarProdutoRequest) = ProdutoResponse(repository.create(request.toDomain()))
}

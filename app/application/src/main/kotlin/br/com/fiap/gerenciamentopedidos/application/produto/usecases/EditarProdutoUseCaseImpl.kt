package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class EditarProdutoUseCaseImpl(private val repository: ProdutoRepository) : EditarProdutoUseCase {
    override fun executar(request: EditarProdutoRequest) = ProdutoResponse(repository.update(request.toDomain()))
}

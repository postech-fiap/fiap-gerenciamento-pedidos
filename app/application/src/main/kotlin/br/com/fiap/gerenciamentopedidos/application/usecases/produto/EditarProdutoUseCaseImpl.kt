package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class EditarProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : EditarProdutoUseCase {
    override fun executar(produto: Produto) = produtoRepository.update(produto)
}

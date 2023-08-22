package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class EditarProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : EditarProdutoUseCase {
    override fun executar(produto: Produto) = produtoRepository.update(produto)
}

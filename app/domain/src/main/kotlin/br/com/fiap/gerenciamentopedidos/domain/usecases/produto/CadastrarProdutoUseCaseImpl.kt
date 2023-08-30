package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class CadastrarProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : CadastrarProdutoUseCase {
    override fun executar(produto: Produto) = produtoRepository.create(produto.valid())
}

package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class CadastrarProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : CadastrarProdutoUseCase {
    override fun executar(produto: Produto) = produtoRepository.create(produto)
}

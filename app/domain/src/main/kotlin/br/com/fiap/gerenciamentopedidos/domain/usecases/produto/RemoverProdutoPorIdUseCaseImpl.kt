package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class RemoverProdutoPorIdUseCaseImpl(private val produtoRepository: ProdutoRepository) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) = produtoRepository.remove(id)
}

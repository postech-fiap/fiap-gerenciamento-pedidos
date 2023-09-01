package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class ObterProdutoPorIdUseCaseImpl(private val produtoRepository: ProdutoRepository) : ObterProdutoPorIdUseCase {
    override fun executar(id: Long) = produtoRepository.get(id)
        .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
}

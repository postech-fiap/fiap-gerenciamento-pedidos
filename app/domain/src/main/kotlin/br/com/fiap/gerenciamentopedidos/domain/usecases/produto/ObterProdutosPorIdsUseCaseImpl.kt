package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class ObterProdutosPorIdsUseCaseImpl(private val produtoRepository: ProdutoRepository) : ObterProdutosPorIdsUseCase {
    override fun executar(ids: List<Long>) = produtoRepository.get(ids)
        .ifEmpty { throw RecursoNaoEncontradoException("Produtos não encontrados ou indisponíveis") }
}

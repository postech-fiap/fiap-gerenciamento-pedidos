package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.ObterProdutoPorIdUseCase

class ObterProdutoPorIdUseCaseImpl(private val produtoPort: ProdutoPort) : ObterProdutoPorIdUseCase {
    override fun executar(id: Long) =
        produtoPort.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
}

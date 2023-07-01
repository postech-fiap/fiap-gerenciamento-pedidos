package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.ProdutoPort

class RemoverProdutoPorIdUseCaseImpl(private val produtoPort: ProdutoPort) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) {
        val produto = produtoPort.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }

        produto.excluir()

        produtoPort.update(produto)
    }
}

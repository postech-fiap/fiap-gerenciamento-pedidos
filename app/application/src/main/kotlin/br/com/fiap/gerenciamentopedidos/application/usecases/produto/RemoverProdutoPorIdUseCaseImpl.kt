package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class RemoverProdutoPorIdUseCaseImpl(private val adapter: ProdutoAdapter) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) {
        val produto = adapter.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }

        produto.excluir()

        adapter.update(produto)
    }
}

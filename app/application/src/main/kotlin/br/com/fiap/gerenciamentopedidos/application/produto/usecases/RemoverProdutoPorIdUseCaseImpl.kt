package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class RemoverProdutoPorIdUseCaseImpl(private val repository: ProdutoRepository) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) {
        val produto = repository.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }

        produto.excluir()

        repository.update(produto)
    }
}

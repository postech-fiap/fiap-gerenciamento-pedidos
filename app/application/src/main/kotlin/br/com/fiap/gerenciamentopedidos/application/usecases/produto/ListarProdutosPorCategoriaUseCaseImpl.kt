package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ListarProdutosPorCategoriaUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class ListarProdutosPorCategoriaUseCaseImpl(private val adapter: ProdutoAdapter) :
    ListarProdutosPorCategoriaUseCase {
    override fun executar(categoria: Categoria) =
        adapter.get(categoria)
            .map { ProdutoResponse(it) }.toList()
            .ifEmpty { throw RecursoNaoEncontradoException("Nenhum produto encontrado") }
}

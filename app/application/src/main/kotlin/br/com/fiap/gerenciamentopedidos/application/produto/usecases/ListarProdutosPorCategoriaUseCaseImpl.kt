package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.ListarProdutosPorCategoriaUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class ListarProdutosPorCategoriaUseCaseImpl(private val repository: ProdutoRepository) :
    ListarProdutosPorCategoriaUseCase {
    override fun executar(categoria: Categoria) =
        repository.get(categoria)
            .map { ProdutoResponse(it) }.toList()
            .ifEmpty { throw RecursoNaoEncontradoException("Nenhum produto encontrado") }
}

package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.ListarProdutosPorCategoriaUseCase

class ListarProdutosPorCategoriaUseCaseImpl(private val produtoPort: ProdutoPort) :
    ListarProdutosPorCategoriaUseCase {
    override fun executar(categoria: Categoria) =
        produtoPort.get(categoria)
            .ifEmpty { throw RecursoNaoEncontradoException("Nenhum produto encontrado") }
}

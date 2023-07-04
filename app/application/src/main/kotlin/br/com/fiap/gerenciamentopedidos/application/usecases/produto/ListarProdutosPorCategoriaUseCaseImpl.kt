package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ListarProdutosPorCategoriaUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class ListarProdutosPorCategoriaUseCaseImpl(private val produtoPort: ProdutoRepository) :
    ListarProdutosPorCategoriaUseCase {
    override fun executar(categoria: Categoria) = produtoPort.get(categoria).map { ProdutoResponse(it) }
}

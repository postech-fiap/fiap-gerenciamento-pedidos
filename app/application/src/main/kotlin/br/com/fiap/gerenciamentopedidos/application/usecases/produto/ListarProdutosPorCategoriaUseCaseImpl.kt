package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ListarProdutosPorCategoriaUseCase
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class ListarProdutosPorCategoriaUseCaseImpl(private val produtoRepository: ProdutoRepository) :
    ListarProdutosPorCategoriaUseCase {
    override fun executar(categoria: Categoria) = produtoRepository.get(categoria)
}

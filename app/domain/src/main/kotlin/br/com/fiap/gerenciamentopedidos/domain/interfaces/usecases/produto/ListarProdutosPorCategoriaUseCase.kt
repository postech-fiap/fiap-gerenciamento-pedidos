package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

fun interface ListarProdutosPorCategoriaUseCase {
    fun executar(categoria: Categoria): List<Produto>
}

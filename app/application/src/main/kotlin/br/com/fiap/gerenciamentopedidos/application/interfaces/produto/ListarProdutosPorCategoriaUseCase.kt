package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ListarProdutosPorCategoriaUseCase {
    fun executar(categoria: Categoria): List<Produto>
}

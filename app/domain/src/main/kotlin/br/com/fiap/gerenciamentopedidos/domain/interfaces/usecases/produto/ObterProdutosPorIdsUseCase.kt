package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

fun interface ObterProdutosPorIdsUseCase {
    fun executar(ids: List<Long>): List<Produto>
}

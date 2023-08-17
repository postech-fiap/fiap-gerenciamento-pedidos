package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ObterProdutosPorIdsUseCase {
    fun executar(ids: List<Long>): List<Produto>
}

package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ObterProdutoPorIdUseCase {
    fun executar(id: Long): Produto
}

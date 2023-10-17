package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

fun interface AlterarDisponibilidadeProdutoUseCase {
    fun executar(id: Long, disponivel: Boolean): Produto
}

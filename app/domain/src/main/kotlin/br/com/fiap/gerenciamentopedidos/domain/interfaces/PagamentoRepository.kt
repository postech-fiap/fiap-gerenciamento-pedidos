package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus

fun interface PagamentoRepository {

    fun alterarStatusPagamento(status: PagamentoStatus, pedidoId: Long)
}

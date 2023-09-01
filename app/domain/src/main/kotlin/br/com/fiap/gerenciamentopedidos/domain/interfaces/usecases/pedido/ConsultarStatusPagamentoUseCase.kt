package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus

interface ConsultarStatusPagamentoUseCase {
    fun executar(pedidoId: Long) : PagamentoStatus
}

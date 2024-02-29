package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus

fun interface AlterarStatusPedidoUseCase {
    fun executar(id: Long, status: PedidoStatus?, pagamentoStatus: PagamentoStatus?)
}
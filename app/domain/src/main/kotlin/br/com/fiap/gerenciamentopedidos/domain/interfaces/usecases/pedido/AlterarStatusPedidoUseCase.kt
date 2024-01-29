package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.util.*

fun interface AlterarStatusPedidoUseCase {
    fun executar(referencia: UUID, status: PagamentoStatus)
}
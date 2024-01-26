package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.util.*

data class AlterarStatusPedidoRequest(
    val referenciaPedido: UUID,
    val idPagamento: Long,
    val statusPagamento: PagamentoStatus,
)
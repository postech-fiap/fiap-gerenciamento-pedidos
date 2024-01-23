package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.util.*

data class AlterarStatusPedidoRequest(
    val referencia: UUID,
    val pagamentoId: Long,
    val status: PagamentoStatus,
)
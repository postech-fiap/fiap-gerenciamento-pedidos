package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

class BuscarPedidosRequest(
    val status: PedidoStatus,
    val dataInicial: OffsetDateTime = OffsetDateTime.now().minusHours(24),
    val dataFinal: OffsetDateTime = OffsetDateTime.now()
) {
    init {
        require(dataInicial.isBefore(OffsetDateTime.now())) { "A data inicial deve ser inferior a data atual" }
        require(dataFinal.isBefore(OffsetDateTime.now())) { "A data final deve ser inferior ou igual a data atual" }
        require(dataInicial.isBefore(dataFinal)) { "A data final deve ser posterior a data inicial" }
    }
}
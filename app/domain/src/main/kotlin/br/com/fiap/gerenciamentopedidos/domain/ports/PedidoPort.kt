package br.com.fiap.gerenciamentopedidos.domain.ports

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

interface PedidoPort {

    fun buscarPedidos(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) : List<Pedido>

}

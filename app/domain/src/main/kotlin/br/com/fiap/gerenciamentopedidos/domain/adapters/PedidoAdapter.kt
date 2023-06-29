package br.com.fiap.gerenciamentopedidos.domain.adapters

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

interface PedidoAdapter {

    fun buscarPedidos(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) : List<Pedido>

}

package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido.BuscarPedidosPort
import java.time.OffsetDateTime

class BuscarUseCaseImpl(val pedidoPort: PedidoPort) : BuscarPedidosPort {
    override fun executar(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) =
        pedidoPort.buscarPedidos(status, dataInicial, dataFinal)
}

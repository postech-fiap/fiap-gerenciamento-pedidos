package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

interface BuscarPedidosPort {

    fun executar(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime): List<PedidoDto>
}

package br.com.fiap.gerenciamentopedidos.domain.ports.drivens

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

interface PedidoPort {

    fun buscarPedidos(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime): List<PedidoDto>
}

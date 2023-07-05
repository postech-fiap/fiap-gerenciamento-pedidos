package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

interface PedidoRepository {

    fun buscarPedidos(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) : List<PedidoDto>
    fun buscarPedidoPorId(id: Long): PedidoDto
    fun alterarStatusPedido(pedidoDto: PedidoDto): PedidoDto

}

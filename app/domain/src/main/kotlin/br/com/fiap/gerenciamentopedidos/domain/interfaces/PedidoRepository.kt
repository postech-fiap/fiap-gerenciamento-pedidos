package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime
import javax.xml.crypto.Data

interface PedidoRepository {

    fun buscarPedidos(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) : List<PedidoDto>
    fun buscarUltimoPedidoDiDia(dia: Int): PedidoDto
    fun salvar(pedido: PedidoDto): PedidoDto

}

package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.PedidoPort
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoJpaRepository
import java.time.OffsetDateTime
import java.util.stream.Collectors

private const val ERROR_MESSAGE_TO_LIST = "Erro ao buscar pedidos na base de dados. Detalhes: %s"

class PedidoMySqlAdapter(private val pedidoJpaRepository: PedidoJpaRepository) : PedidoPort {

    override fun buscarPedidos(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ): List<PedidoDto> {
        try {
            val pedidosEntity = pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
                status,
                dataInicial,
                dataFinal
            )
            return pedidosEntity.stream().map { it.toDto() }.collect(Collectors.toList())
        } catch (ex: Exception) {
            throw BaseDeDadosException(String.format(ERROR_MESSAGE_TO_LIST, ex.message))
        }
    }
}

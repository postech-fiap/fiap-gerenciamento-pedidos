package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import java.time.OffsetDateTime

private const val ERROR_MESSAGE_TO_LIST = "Erro ao buscar pedidos na base de dados. Detalhes: %s"

class PedidoRepositoryImpl(private val pedidoJpaRepository: PedidoJpaRepository) : PedidoRepository {

    override fun buscarPedidos(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ): List<PedidoDto> {
        try {
            return pedidoJpaRepository
                .findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(status, dataInicial, dataFinal)
                .map { it.toDto() }
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_TO_LIST, ex.message)
            )
        }
    }

    override fun obterProximoNumeroPedidoDoDia(): String {
        try {
            return pedidoJpaRepository.obterProximoNumeroPedidoDoDia()
        } catch (ex: Exception) {
            throw RuntimeException(String.format(ERROR_MESSAGE_TO_LIST, ex.message))
        }
    }

    override fun salvar(pedido: PedidoDto): PedidoDto {
        try {
            val entity = PedidoEntity.fromDto(pedido)
            return pedidoJpaRepository.save(entity).toDto()
        } catch (ex: Exception) {
            throw RuntimeException(
                String.format(ERROR_MESSAGE_TO_LIST, ex.message)
            )
        }
    }
}

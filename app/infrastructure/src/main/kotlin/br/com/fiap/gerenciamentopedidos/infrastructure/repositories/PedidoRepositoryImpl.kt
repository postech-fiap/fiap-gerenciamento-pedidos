package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import java.time.OffsetDateTime

private const val ERROR_MESSAGE_GET_BY_CATEGORIA = "Erro ao listar pedidos por categoria. Detalhes: %s"
private const val ERROR_MESSAGE_GET_NEXT_NUMBER = "Erro ao obter próximo número pedido. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar pedido. Detalhes: %s"

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
                String.format(ERROR_MESSAGE_GET_BY_CATEGORIA, ex.message)
            )
        }
    }

    override fun obterUltimoNumeroPedidoDoDia(): String {
        try {
            return pedidoJpaRepository.obterUtimoNumeroPedidoDoDia()
        } catch (ex: Exception) {
            throw BaseDeDadosException(String.format(ERROR_MESSAGE_GET_NEXT_NUMBER, ex.message))
        }
    }

    override fun salvar(pedido: PedidoDto): PedidoDto {
        try {
            val entity = PedidoEntity.fromDto(pedido)
            return pedidoJpaRepository.save(entity).toDto()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_CREATE, ex.message)
            )
        }
    }
}

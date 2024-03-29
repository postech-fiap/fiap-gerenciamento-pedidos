package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import java.util.*

private const val ERROR_MESSAGE_GET_NEXT_NUMBER = "Erro ao obter próximo número pedido. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar pedido. Detalhes: %s"
private const val ERROR_MESSAGE_GET_BY_ID = "Erro ao obter pedido por id. Detalhes: %s"
private const val ERROR_MESSAGE_UPDATE = "Erro ao atualizar Pedido. Detalhes: %s"

class PedidoRepositoryImpl(private val pedidoJpaRepository: PedidoJpaRepository) : PedidoRepository {
    override fun buscarPedidoPorId(id: Long): Optional<Pedido> {
        try {
            return pedidoJpaRepository.findById(id).map { it.toModel() }
        } catch (ex: Exception) {
            throw BaseDeDadosException(String.format(ERROR_MESSAGE_GET_BY_ID, ex.message))
        }
    }

    override fun obterUltimoNumeroPedidoDoDia(): String {
        try {
            return pedidoJpaRepository.obterUltimoNumeroPedidoDoDia()
        } catch (ex: Exception) {
            throw BaseDeDadosException(String.format(ERROR_MESSAGE_GET_NEXT_NUMBER, ex.message))
        }
    }

    override fun salvar(pedido: Pedido): Pedido {
        try {
            val entity = PedidoEntity.fromModel(pedido)
            return pedidoJpaRepository.save(entity).toModel()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_CREATE, ex.message)
            )
        }
    }

    override fun update(pedido: Pedido): Pedido {
        try {
            val entity = pedidoJpaRepository.findById(pedido.id!!)
                .orElseThrow { BaseDeDadosException("Pedido não encontrado") }
                .copy(
                    status = pedido.status,
                    statusPagamento = pedido.statusPagamento,
                )

            return pedidoJpaRepository.save(entity).toModel()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_UPDATE, ex.message)
            )
        }
    }
}

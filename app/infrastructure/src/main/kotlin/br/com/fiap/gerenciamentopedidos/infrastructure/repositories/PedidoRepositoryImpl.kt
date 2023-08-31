package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository

private const val ERROR_MESSAGE_GET_BY_CATEGORIA = "Erro ao listar pedidos por categoria. Detalhes: %s"
private const val ERROR_MESSAGE_GET_NEXT_NUMBER = "Erro ao obter próximo número pedido. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar pedido. Detalhes: %s"
private const val ERROR_MESSAGE_GET_BY_ID = "Erro ao listar pedidos por Id. Detalhes: %s"
private const val ERROR_MESSAGE_UPDATE_STATUS = "Erro ao realizar a atualização do status do pedido. Detalhes: %s"

class PedidoRepositoryImpl(private val pedidoJpaRepository: PedidoJpaRepository) : PedidoRepository {

    override fun buscarPedidos(): List<Pedido> {
        try {
            return pedidoJpaRepository
                .buscarPedidos()
                .map { it.toModel() }
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_CATEGORIA, ex.message)
            )
        }
    }

    override fun buscarPedidoPorId(id: Long): Pedido {
        try {
            return pedidoJpaRepository.findById(id).get().toModel()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_ID, ex.message)
            )
        }
    }

    override fun alterarStatusPedido(status: PedidoStatus, id: Long) {
        try {
            return pedidoJpaRepository.updateStatusById(status, id)
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_UPDATE_STATUS, ex.message)
            )
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
}

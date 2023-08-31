package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PagamentoJpaRepository

private const val ERROR_MESSAGE_UPDATE_STATUS = "Erro ao realizar a atualização do status do pagamento. Detalhes: %s"

class PagamentoRepositoryImpl(private val pagamentoJpaRepository: PagamentoJpaRepository) : PagamentoRepository {

    override fun alterarStatusPagamento(status: PagamentoStatus, pedidoId: Long) {
        try {
            return pagamentoJpaRepository.updateStatusByPedidoId(status, pedidoId)
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_UPDATE_STATUS, ex.message)
            )
        }
    }
}
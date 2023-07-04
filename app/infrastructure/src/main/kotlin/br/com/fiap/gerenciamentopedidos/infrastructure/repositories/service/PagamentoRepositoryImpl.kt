package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.service

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

class PagamentoRepositoryImpl(private val pagamentoRepository: PagamentoRepository) : PagamentoRepository {
    override fun efetuarPagamento(pedido: String): Pagamento {
        return Pagamento(
            id = pedido,
            dataHora = OffsetDateTime.now(),
            status = PagamentoStatus.APROVADO
        )
    }
}


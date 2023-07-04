package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.service

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import java.time.OffsetDateTime

class PagamentoMercadoPagoServiceImpl : PagamentoService {

    override fun efetuarPagamento(numeroPedido: String, valor: Double): Pagamento {
        return Pagamento(
            dataHora = OffsetDateTime.now(),
            status = PagamentoStatus.APROVADO,
        )
    }
}

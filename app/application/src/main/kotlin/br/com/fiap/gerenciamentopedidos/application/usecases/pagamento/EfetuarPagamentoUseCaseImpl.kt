package br.com.fiap.gerenciamentopedidos.application.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.application.interfaces.pagamento.EfetuarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService

class EfetuarPagamentoUseCaseImpl(private val pagamentoService: PagamentoService) : EfetuarPagamentoUseCase {
    override fun executar(numeroPedido: String) = pagamentoService.efetuarPagamento(numeroPedido)
}

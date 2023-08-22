package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.EfetuarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService

class EfetuarPagamentoUseCaseImpl(private val pagamentoService: PagamentoService) : EfetuarPagamentoUseCase {
    override fun executar(numeroPedido: String) = pagamentoService.efetuarPagamento(numeroPedido)
}

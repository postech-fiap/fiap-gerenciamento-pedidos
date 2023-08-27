package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.EfetuarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class EfetuarPagamentoUseCaseImpl(private val pagamentoService: PagamentoService) : EfetuarPagamentoUseCase {
    override fun executar(pedido: Pedido) = pagamentoService.gerarPagamento(pedido)
}

package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.GerarQrCodePagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class GerarQrCodePagamentoUseCaseImpl(private val pagamentoService: PagamentoService) : GerarQrCodePagamentoUseCase {
    override fun executar(pedido: Pedido) = pagamentoService.gerarQrCodePagamento(pedido)
}

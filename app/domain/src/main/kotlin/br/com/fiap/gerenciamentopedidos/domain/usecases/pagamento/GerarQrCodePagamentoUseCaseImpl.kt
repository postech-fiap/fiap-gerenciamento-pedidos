package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.GerarQrCodePagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.GerarQrCodePagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class GerarQrCodePagamentoUseCaseImpl(private val gerarQrCodePagamentoGateway: GerarQrCodePagamentoGateway) : GerarQrCodePagamentoUseCase {
    override fun executar(pedido: Pedido) = gerarQrCodePagamentoGateway.executar(pedido)
}

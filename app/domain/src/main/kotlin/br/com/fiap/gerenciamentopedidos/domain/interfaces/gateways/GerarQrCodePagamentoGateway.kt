package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

fun interface GerarQrCodePagamentoGateway {
    fun executar(pedido: Pedido): Pagamento
}

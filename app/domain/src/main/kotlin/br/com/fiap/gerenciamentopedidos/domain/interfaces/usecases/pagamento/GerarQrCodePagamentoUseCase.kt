package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

fun interface GerarQrCodePagamentoUseCase {
    fun executar(pedido: Pedido): Pagamento
}
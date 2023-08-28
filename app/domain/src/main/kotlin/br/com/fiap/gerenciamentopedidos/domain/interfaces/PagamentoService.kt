package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

interface PagamentoService {
    fun gerarQrCodePagamento(pedido: Pedido): Pagamento
}

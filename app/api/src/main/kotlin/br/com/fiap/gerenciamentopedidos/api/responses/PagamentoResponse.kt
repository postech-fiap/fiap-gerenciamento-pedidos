package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

class PagamentoResponse(pagamento: Pagamento) {
    val id = pagamento.id
    val dataHora = pagamento.dataHora
    val status = pagamento.status
    val qrCode = pagamento.qrCode
    val valorTotal = pagamento.valorTotal
}

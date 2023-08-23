package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto

class PagamentoResponse(pagamento: PagamentoDto) {
    val id = pagamento.id
    val dataHora = pagamento.dataHora
    val status = pagamento.status
    val qrCode = pagamento.qrCode
    val valorTotal = pagamento.valorTotal
}

package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

class PagamentoResponse(pagamento: Pagamento) {
    val id = pagamento.id
    val dataHora = pagamento.dataHora
    val status = pagamento.status
}

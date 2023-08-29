package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento

class DetalhePagamentoResponse(detalhePagamento: DetalhePagamento) {
    val status = detalhePagamento.status
}

package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto

fun interface PagamentoGateway {
    fun criar(pagamento: PagamentoDto)
}
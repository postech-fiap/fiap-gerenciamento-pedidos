package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto

interface PagamentoService {
    fun gerarPagamento(pedido: PedidoDto): PagamentoDto
}

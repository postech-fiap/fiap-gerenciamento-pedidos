package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import java.math.BigDecimal

interface PagamentoService {
    fun efetuarPagamento(numeroPedido: String, valor: BigDecimal): PagamentoDto
}

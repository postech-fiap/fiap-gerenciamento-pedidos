package br.com.fiap.gerenciamentopedidos.domain.dtos

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class PagamentoDto(
    val id: String? = null,
    val referenciaPedido: String?,
    val numeroPedido: String?,
    val dataHora: LocalDateTime?,
    val valorTotal: BigDecimal?,
    val items: List<ItemPagamentoDto>?,
)

data class ItemPagamentoDto(
    val quantidade: Long?,
    val valorPago: BigDecimal?,
    val produto: ProdutoPagamentoDto,
)

data class ProdutoPagamentoDto(
    val id: Long?,
    val nome: String?,
    val descricao: String?,
    val categoria: String?,
    val valor: BigDecimal?,
)
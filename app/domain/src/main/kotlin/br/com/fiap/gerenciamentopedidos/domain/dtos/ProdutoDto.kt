package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.math.BigDecimal

data class ProdutoDto(
    val id: Long? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: BigDecimal? = null,
    val tempoPreparo: Long? = null,
    var disponivel: Boolean? = null,
    var excluido: Boolean? = null,
    val imagem: ImagemDto? = null
) {
    companion object {
        fun fromModel(produto: Produto) = ProdutoDto(
            produto.id,
            produto.nome,
            produto.descricao,
            produto.categoria,
            produto.valor,
            produto.tempoPreparo,
            produto.disponivel,
            produto.excluido,
            produto.imagem?.let { ImagemDto.fromModel(it) }
        )
    }

    fun toModel() =
        Produto(id, nome, descricao, categoria, valor!!, tempoPreparo!!, disponivel!!, excluido!!, imagem?.toModel())
}

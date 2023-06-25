package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

data class ProdutoResponse(
    val nome: String,
    val descricao: String,
    val categoria: Categoria,
    val valor: Double,
    val tempoPreparo: Long,
    val disponivel: Boolean,
    val excluido: Boolean,
    val imagem: String
) {
    companion object {
        fun fromDomain(produto: Produto): ProdutoResponse {
            return ProdutoResponse(
                nome = produto.nome!!,
                descricao = produto.descricao!!,
                categoria = produto.categoria!!,
                valor = produto.valor,
                tempoPreparo = produto.tempoPreparo,
                disponivel = produto.disponivel,
                excluido = produto.excluido,
                imagem = produto.imagem!!
            )
        }
    }
}
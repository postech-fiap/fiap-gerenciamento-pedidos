package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

data class EditarProdutoRequest(
    val id: Long,
    val nome: String,
    val descricao: String?,
    val categoria: Categoria,
    val valor: Double,
    val tempoPreparo: Long,
    val disponivel: Boolean,
    val excluido: Boolean,
    val imagem: String?,
) {
    fun toDomain() = Produto(id, nome, descricao, categoria, valor, tempoPreparo, disponivel, excluido, imagem)
}
package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

data class CadastrarProdutoRequest(
    val nome: String,
    val descricao: String?,
    val categoria: Categoria,
    val valor: Double,
    val tempoPreparo: Long,
    val disponivel: Boolean,
    val excluido: Boolean,
    val imagem: String?,
) {
    fun toDomain() = Produto(
        null,
        nome,
        descricao,
        categoria,
        valor,
        tempoPreparo,
        disponivel,
        excluido,
        imagem.let { Imagem(caminho = it) })
}
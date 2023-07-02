package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

data class EditarProdutoRequest(
    val id: Long? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: Double? = null,
    val tempoPreparo: Long? = null,
    val imagem: EditarImagemRequest? = null,
) {
    fun toDto() = ProdutoDto(
        id = id,
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem?.toDto()
    )
}

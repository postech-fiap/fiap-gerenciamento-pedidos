package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import com.fasterxml.jackson.annotation.JsonProperty

data class EditarProdutoRequest(
    val id: Long? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: Double? = null,
    @JsonProperty("tempo_preparo")
    val tempoPreparo: Long? = null,
    val imagem: EditarImagemRequest? = null,
) {
    fun toDomain() = Produto(
        id = id,
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem?.toDomain()
    )
}

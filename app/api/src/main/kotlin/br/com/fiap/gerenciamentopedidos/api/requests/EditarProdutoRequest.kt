package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.math.BigDecimal

data class EditarProdutoRequest(
    val id: Long?,
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: BigDecimal? = null,
    val tempoPreparo: Long? = null,
    val imagem: EditarImagemRequest? = null,
) {
    init {
        require(id != null) { "Identificador do produto não informado" }
    }

    fun toModel() = Produto(
        id = id,
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem?.toModel()
    )
}

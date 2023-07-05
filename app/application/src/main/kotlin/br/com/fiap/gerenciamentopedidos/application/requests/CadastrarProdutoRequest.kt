package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.math.BigDecimal

data class CadastrarProdutoRequest(
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: BigDecimal? = null,
    val tempoPreparo: Long? = null,
    val imagem: String? = null
) {
    fun toDomain() = Produto(
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem?.let { Imagem(caminho = it) })
}   

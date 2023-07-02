package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.dtos.ImagemDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

data class CadastrarProdutoRequest(
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: Double? = null,
    val tempoPreparo: Long? = null,
    val imagem: String? = null
) {
    fun toDto() = ProdutoDto(
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem.let { ImagemDto(caminho = it) })
}
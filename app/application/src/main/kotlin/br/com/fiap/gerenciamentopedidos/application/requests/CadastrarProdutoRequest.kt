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

    init {
        require(nome.isNullOrEmpty().not()) { "Nome do produto não informado" }
        require(categoria != null) { "Categoria do produto não informada" }
        require(valor != null) { "Valor do produto não informado" }
        require(tempoPreparo != null) { "Tempo de preparo do produto não informado" }
        imagem?.matches(Regex("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))\$)"))
            ?.let { require(it) { "Formato da imagem é inválido!" } }
    }

    fun toModel() = Produto(
        nome = nome,
        descricao = descricao,
        categoria = categoria,
        valor = valor!!,
        tempoPreparo = tempoPreparo!!,
        imagem = imagem?.let { Imagem(caminho = it) })
}

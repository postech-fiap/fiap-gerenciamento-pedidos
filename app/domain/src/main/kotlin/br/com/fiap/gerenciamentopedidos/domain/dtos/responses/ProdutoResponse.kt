package br.com.fiap.gerenciamentopedidos.domain.dtos.responses

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoResponse(produto: Produto) {
    val id: Long?
    val nome: String?
    val descricao: String?
    val categoria: Categoria?
    val valor: Double?
    val tempoPreparo: Long?
    val disponivel: Boolean?
    val excluido: Boolean?
    val imagem: ImagemResponse?

    init {
        id = produto.id
        nome = produto.nome
        descricao = produto.descricao
        categoria = produto.categoria
        valor = produto.valor
        tempoPreparo = produto.tempoPreparo
        disponivel = produto.disponivel
        excluido = produto.excluido
        imagem = if (produto.imagem != null) ImagemResponse(produto.imagem) else null
    }
}
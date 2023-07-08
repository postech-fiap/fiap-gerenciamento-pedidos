package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto

class ProdutoResponse(produto: ProdutoDto) {
    val id = produto.id
    val nome = produto.nome
    val descricao = produto.descricao
    val categoria = produto.categoria
    val valor = produto.valor
    val tempoPreparo = produto.tempoPreparo
    val disponivel = produto.disponivel
    val excluido = produto.excluido
    val imagem = produto.imagem?.let { ImagemResponse(it) }
}
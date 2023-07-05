package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import java.math.BigDecimal

class ProdutoResponse(produto: ProdutoDto) {
    var id: Long? = null
    var nome: String? = null
    var descricao: String? = null
    var categoria: Categoria? = null
    var valor: BigDecimal? = null
    var tempoPreparo: Long? = null
    var disponivel: Boolean? = null
    var excluido: Boolean? = null
    var imagem: ImagemResponse? = null

    init {
        id = produto.id
        nome = produto.nome
        descricao = produto.descricao
        categoria = produto.categoria
        valor = produto.valor
        tempoPreparo = produto.tempoPreparo
        disponivel = produto.disponivel
        excluido = produto.excluido
        if (produto.imagem != null) {
            imagem = ImagemResponse(produto.imagem!!)
        }
    }
}
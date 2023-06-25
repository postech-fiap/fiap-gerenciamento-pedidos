package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model

data class Produto(
    val id: Long? = null,
    val nome: String?,
    val descricao: String?,
    val categoria: Categoria?,
    val valor: Double,
    val tempoPreparo: Long,
    var disponivel: Boolean,
    var excluido: Boolean,
    val imagem: String?,
) : Model {
    init {
        validate()
    }

    override fun validate() {
        if (nome == null || nome.isEmpty()) throw Exception("Nome do produto não informado")
        if (descricao == null || descricao.isEmpty()) throw Exception("Descrição do produto não informada")
        if (categoria == null) throw Exception("Categoria do produto não informada")
    }

    fun alterarDisponibilidade(disponivel: Boolean) {
        if (disponivel) {
            this.disponibilizar()
        } else {
            this.indisponibilizar()
        }
    }

    fun disponibilizar() {
        if (disponivel) throw Exception("Produto já está disponível")
        this.disponivel = true
    }

    fun indisponibilizar() {
        if (!disponivel) throw Exception("Produto já está indisponível")
        this.disponivel = false
    }

    fun excluir() {
        if (excluido) throw Exception("Produto já está excluído")
        this.excluido = true
    }
}
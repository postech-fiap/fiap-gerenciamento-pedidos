package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.ValidationException
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
        if (nome.isNullOrEmpty()) throw ValidationException("Nome do produto não informado")
        if (categoria == null) throw ValidationException("Categoria do produto não informada")
    }

    fun alterarDisponibilidade(disponivel: Boolean) {
        if (disponivel) {
            this.disponibilizar()
        } else {
            this.indisponibilizar()
        }
    }

    fun disponibilizar() {
        if (disponivel) throw BusinessException("Produto já está disponível")
        this.disponivel = true
    }

    fun indisponibilizar() {
        if (!disponivel) throw BusinessException("Produto já está indisponível")
        this.disponivel = false
    }

    fun excluir() {
        if (excluido) throw BusinessException("Produto já está excluído")
        this.excluido = true
    }
}
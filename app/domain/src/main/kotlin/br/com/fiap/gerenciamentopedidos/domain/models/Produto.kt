package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException

data class Produto(
    val id: Long? = null,
    val nome: String?,
    val descricao: String?,
    val categoria: Categoria?,
    val valor: Double,
    val tempoPreparo: Long,
    var disponivel: Boolean = true,
    var excluido: Boolean = false,
    val imagem: Imagem?,
) {
    init {
        require(nome.isNullOrEmpty().not()) { "Nome do produto não informado" }
        require(categoria != null) { "Categoria do produto não informada" }
    }

    fun alterarDisponibilidade(disponivel: Boolean) = if (disponivel) disponibilizar() else indisponibilizar()

    fun disponibilizar(): Produto {
        if (disponivel) throw BusinessException("Produto já está disponível")
        disponivel = true
        return this
    }

    fun indisponibilizar(): Produto {
        if (!disponivel) throw BusinessException("Produto já está indisponível")
        disponivel = false
        return this
    }

    fun excluir(): Produto {
        if (excluido) throw BusinessException("Produto já está excluído")
        excluido = true
        return this
    }
}

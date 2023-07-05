package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import java.math.BigDecimal

data class Produto(
    val id: Long? = null,
    val nome: String?,
    val descricao: String?,
    val categoria: Categoria?,
    val valor: BigDecimal,
    val tempoPreparo: Long,
    var disponivel: Boolean = true,
    var excluido: Boolean = false,
    val imagem: Imagem?,
) {
    init {
        require(nome.isNullOrEmpty().not()) { "Nome do produto não informado" }
        require(categoria != null) { "Categoria do produto não informada" }
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
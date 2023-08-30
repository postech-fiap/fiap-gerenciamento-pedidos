package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model

data class Imagem(
    val id: Long? = null,
    val caminho: String? = null
) : Model {
    override fun valid(): Model {
        require(caminho.isNullOrEmpty().not()) { "Caminho da imagem não informado" }
        caminho?.matches(Regex("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))\$)"))
            ?.let { require(it) { "Formato da imagem é inválido!" } }
        return this
    }
}

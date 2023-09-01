package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

data class EditarImagemRequest(
    val id: Long? = null,
    val caminho: String?,
) {
    init {
        require(caminho.isNullOrEmpty().not()) { "Caminho da imagem não informado" }
    }

    fun toModel() = Imagem(id, caminho)
}
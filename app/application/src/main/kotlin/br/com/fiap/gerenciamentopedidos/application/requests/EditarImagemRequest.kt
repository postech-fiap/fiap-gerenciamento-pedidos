package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

data class EditarImagemRequest(
    val id: Long? = null,
    val caminho: String? = null,
) {
    fun toDomain() = Imagem(id, caminho)
}
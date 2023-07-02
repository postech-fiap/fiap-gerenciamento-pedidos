package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.dtos.ImagemDto

data class EditarImagemRequest(
    val id: Long? = null,
    val caminho: String? = null,
) {
    fun toDto() = ImagemDto(id, caminho)
}
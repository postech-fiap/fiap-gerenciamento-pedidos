package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ImagemDto

class ImagemResponse(imagem: ImagemDto) {
    val id: Long? = imagem.id
    val caminho: String? = imagem.caminho
}
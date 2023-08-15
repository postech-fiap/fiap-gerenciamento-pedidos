package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

class ImagemResponse(imagem: Imagem) {
    val id: Long? = imagem.id
    val caminho: String? = imagem.caminho
}
package br.com.fiap.gerenciamentopedidos.domain.models

data class Imagem(
    val id: Long? = null,
    val caminho: String
) {
    init {
        require(caminho.isEmpty().not()) { "Caminho da imagem não informado" }
    }
}
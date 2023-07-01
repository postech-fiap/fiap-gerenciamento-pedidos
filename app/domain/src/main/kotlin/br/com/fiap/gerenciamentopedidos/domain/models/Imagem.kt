package br.com.fiap.gerenciamentopedidos.domain.models

data class Imagem(
    val id: Long? = null,
    val caminho: String? = null
) {
    init {
        require(caminho.isNullOrEmpty().not()) { "Caminho da imagem não informado" }
    }
}
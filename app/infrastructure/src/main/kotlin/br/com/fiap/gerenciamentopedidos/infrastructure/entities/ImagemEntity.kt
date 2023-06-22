package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import jakarta.persistence.*

class ImagemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(name = "caminho", nullable = true)
    val caminho: String,
    @OneToOne(fetch = FetchType.LAZY)
    val produto: ProdutoEntity
) {
}
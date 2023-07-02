package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import jakarta.persistence.*

@Entity
@Table(name = "cliente")
data class ClienteEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "cpf", nullable = false, length = 11)
    val cpf: String? = null,

    @Column(name = "email", nullable = false, length = 100)
    val email: String? = null,

    @Column(name = "nome", nullable = false, length = 100)
    val nome: String? = null
) {
    companion object {
        fun fromDto(cliente: ClienteDto) =
            ClienteEntity(cliente.id, cliente.cpf?.numero, cliente.email?.endereco, cliente.nome)
    }

    fun toDto(cpf: String) =
        ClienteDto(id, Cpf(cpf), nome!!, Email(email!!))
}

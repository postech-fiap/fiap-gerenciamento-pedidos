package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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
    val nome: String? = null,
) {

    companion object {
        fun fromDomain(clienteDomain: ClienteDomain): ClienteEntity {
            return ClienteEntity(
                cpf = Cpf.removeMascara(clienteDomain.cpf.numero),
                nome = clienteDomain.nome,
                email = clienteDomain.email.endereco
            )
        }
    }

    fun toDomain(cpf: String) =
        ClienteDomain(
            id = id,
            Cpf(cpf),
            nome!!,
            Email(email!!)
        )
}


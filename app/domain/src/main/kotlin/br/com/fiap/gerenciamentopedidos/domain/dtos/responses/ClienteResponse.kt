package br.com.fiap.gerenciamentopedidos.domain.dtos.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class ClienteResponse(cliente: Cliente) {
    val id: Long?
    val cpf: String?
    val nome: String?
    val email: String?

    init {
        id = cliente.id
        cpf = cliente.cpf.numero
        nome = cliente.nome
        email = cliente.email.endereco
    }
}

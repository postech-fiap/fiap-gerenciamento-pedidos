package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class ClienteResponse(cliente: Cliente) {
    val id = cliente.id
    val cpf = cliente.cpf?.numero
    val nome = cliente.nome
    val email = cliente.email?.endereco
}

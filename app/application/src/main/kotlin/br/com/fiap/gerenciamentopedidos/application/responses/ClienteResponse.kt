package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto

class ClienteResponse(cliente: ClienteDto) {
    val id = cliente.id
    val cpf = cliente.cpf?.numero
    val nome = cliente.nome
    val email = cliente.email?.endereco
}

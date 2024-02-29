package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.NotificacaoDto

fun interface NotificacaoGateway {
    fun enviar(notificacao: NotificacaoDto)
}
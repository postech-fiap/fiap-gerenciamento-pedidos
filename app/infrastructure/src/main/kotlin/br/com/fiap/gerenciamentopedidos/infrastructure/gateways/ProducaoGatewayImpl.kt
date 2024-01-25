package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

private const val ERROR_MESSAGE = "Erro de integração para enviar pedido para produção. Detalhes: %s"

class ProducaoGatewayImpl(private val url: String, private val restTemplate: RestTemplate) : ProducaoGateway {
    override fun enviar(pedido: PedidoDto) {
        try {
            val response = restTemplate.postForEntity("${url}/order", pedido, Void::class.java)

            if (response.statusCode != HttpStatus.CREATED)
                throw RuntimeException("[status_code: ${response.statusCode}")
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, "${ex.message}"))
        }
    }
}
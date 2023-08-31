package br.com.fiap.gerenciamentopedidos.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MercadoPagoConfig(
    @Value("\${mercado-pago-api.generate-qrcode}")
    val generateQrcodeEndpoint: String,
    @Value("\${mercado-pago-api.merchant-orders-pagamento}")
    val merchantOrdersPagamentoEndpoint: String,
    @Value("\${secrets.mercado-pago.token}")
    val token: String,
    @Value("\${mercado-pago.webhook-url}")
    val webhookUrl: String,
)

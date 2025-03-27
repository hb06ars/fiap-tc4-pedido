package org.fiap.infra.config.integration;

import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.infra.exceptions.GatewayResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

import static org.fiap.domain.util.UrlConstants.ESTOQUE_BASE_URL;
import static org.fiap.domain.util.UrlConstants.PAGAMENTO_BASE_URL;

@Configuration
public class PagamentoConfiguration {

    @Bean
    public MessageChannel pagamento() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow salvandoPagamento() {
        return IntegrationFlow.from("pagamentoSave")
                .handle(Http.outboundGateway(PAGAMENTO_BASE_URL)
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(PagamentoDTO.class)
                        .extractPayload(true)
                        .errorHandler(new GatewayResponseErrorHandler())
                )
                .log().bridge().get();
    }

}

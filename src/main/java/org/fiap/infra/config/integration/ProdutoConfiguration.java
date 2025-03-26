package org.fiap.infra.config.integration;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.infra.exceptions.GatewayResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

import static org.fiap.domain.util.UrlConstants.CLIENTE_BASE_URL;
import static org.fiap.domain.util.UrlConstants.PRODUTO_BASE_URL;

@Configuration
public class ProdutoConfiguration {

    @Bean
    public MessageChannel produto() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow findByProdutoSku() {
        return IntegrationFlow.from("produtoFindBySku")
                .handle(Http.outboundGateway(m -> PRODUTO_BASE_URL.concat("?sku=" + m.getPayload()))
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(ProdutoDTO.class)
                        .errorHandler(new GatewayResponseErrorHandler())
                )
                .log().bridge().get();
    }

}

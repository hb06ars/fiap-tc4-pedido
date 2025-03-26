package org.fiap.infra.config.integration;

import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.infra.exceptions.GatewayResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

import static org.fiap.domain.util.UrlConstants.ESTOQUE_BASE_URL;

@Configuration
public class EstoqueConfiguration {

    @Bean
    public MessageChannel estoque() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow findByProdutoId() {
        return IntegrationFlow.from("estoqueFindByIdProduto")
                .handle(Http.outboundGateway(m -> ESTOQUE_BASE_URL.concat("?produtoId=" + m.getPayload()))
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(EstoqueDTO.class)
                        .errorHandler(new GatewayResponseErrorHandler())
                )
                .log().bridge().get();
    }

    @Bean
    public IntegrationFlow updateByIdProduto() {
        return IntegrationFlow.from("estoqueUpdateByIdProduto")
                .handle(Http.outboundGateway(m -> ESTOQUE_BASE_URL.concat("?produtoId=" + m.getPayload()))
                        .httpMethod(HttpMethod.PUT)
                        .expectedResponseType(EstoqueDTO.class)
                        .errorHandler(new GatewayResponseErrorHandler())
                )
                .log().bridge().get();
    }

}

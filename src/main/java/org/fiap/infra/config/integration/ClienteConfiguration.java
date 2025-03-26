package org.fiap.infra.config.integration;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.infra.exceptions.ClienteResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ClienteConfiguration {

    @Bean
    public MessageChannel cliente() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow findById() {
        return IntegrationFlow.from("clienteFindById")
                .handle(Http.outboundGateway(m -> "http://localhost:8085/cliente?cpf=" + m.getPayload())
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(ClienteDTO.class)
                        .errorHandler(new ClienteResponseErrorHandler())
                )
                .log().bridge().get();
    }

    @Bean
    public IntegrationFlow save() {
        return IntegrationFlow.from("clienteSave")
                .handle(Http.outboundGateway("http://localhost:8085/cliente")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ClienteDTO.class)
                        .extractPayload(true)
                        .errorHandler(new ClienteResponseErrorHandler())
                )
                .log().bridge().get();
    }

    @Bean
    public IntegrationFlow update() {
        return IntegrationFlow.from("clienteUpdate")
                .handle(Http.outboundGateway("http://localhost:8085/cliente/{id}")
                        .httpMethod(HttpMethod.PUT)
                        .expectedResponseType(ClienteDTO.class)
                        .extractPayload(true)
                        .uriVariable("id", "payload.id")
                        .errorHandler(new ClienteResponseErrorHandler())
                )
                .log().bridge().get();
    }
}

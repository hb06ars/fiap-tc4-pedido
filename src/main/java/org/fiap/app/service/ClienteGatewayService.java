package org.fiap.app.service;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.gateway.ClienteGateway;
import org.fiap.app.rest.request.cliente.ClienteRequest;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.infra.exceptions.GlobalException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@Slf4j
public class ClienteGatewayService {

    private static final String API_INDISPONIVEL = "A API de clientes está indisponível";

    private final ClienteGateway gateway;

    public ClienteGatewayService(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO findByCpf(String cpf) {
        try {
            return gateway.findByCpf(new GenericMessage<>(cpf.trim()));
        } catch (ResourceAccessException ex) {
            throw new GlobalException(API_INDISPONIVEL);
        }
    }

    public ClienteDTO save(ClienteRequest request) {
        try {
            return gateway.save(new GenericMessage<>(request));
        } catch (ResourceAccessException ex) {
            throw new GlobalException(API_INDISPONIVEL);
        }
    }

    public ClienteDTO update(Long id, ClienteRequest request) {
        try {
            request.setId(id);
            return gateway.update(new GenericMessage<>(request));

        } catch (ResourceAccessException ex) {
            throw new GlobalException(API_INDISPONIVEL);
        }
    }
}


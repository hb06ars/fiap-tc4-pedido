package org.fiap.app.gateway;

import org.fiap.app.rest.request.cliente.ClienteRequest;
import org.fiap.domain.dto.ClienteDTO;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface ClienteGateway {

    @Gateway(requestChannel = "clienteFindByCpf", requestTimeout = 5000)
    ClienteDTO findByCpf(Message<String> cpf);

}

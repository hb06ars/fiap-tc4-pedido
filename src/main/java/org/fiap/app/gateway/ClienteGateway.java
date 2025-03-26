package org.fiap.app.gateway;

import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.domain.dto.ClienteDTO;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface ClienteGateway {

    @Gateway(requestChannel = "clienteFindById", requestTimeout = 5000)
    ClienteDTO findByCpf(Message<String> cpf);

    @Gateway(requestChannel = "clienteSave", requestTimeout = 5000)
    ClienteDTO save(Message<ClienteRequest> request);

    @Gateway(requestChannel = "clienteUpdate", requestTimeout = 5000)
    ClienteDTO update(Message<ClienteRequest> request);

}

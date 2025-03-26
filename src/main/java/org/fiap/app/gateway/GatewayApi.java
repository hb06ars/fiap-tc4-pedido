package org.fiap.app.gateway;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface GatewayApi {

    @Gateway(requestChannel = "clienteFindByCpf", requestTimeout = 5000)
    ClienteDTO findByCpf(Message<String> cpf);

    @Gateway(requestChannel = "produtoFindBySku", requestTimeout = 5000)
    ProdutoDTO findBySku(Message<String> sku);
}

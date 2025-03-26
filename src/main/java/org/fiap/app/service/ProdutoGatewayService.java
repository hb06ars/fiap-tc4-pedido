package org.fiap.app.service;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.gateway.GatewayApi;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.infra.exceptions.GlobalException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import static org.fiap.domain.util.StringConstants.API_INDISPONIVEL;

@Service
@Slf4j
public class ProdutoGatewayService {

    private final GatewayApi gateway;

    public ProdutoGatewayService(GatewayApi gateway) {
        this.gateway = gateway;
    }

    public ProdutoDTO findBySku(String sku) {
        try {
            return gateway.findBySku(new GenericMessage<>(sku.trim()));
        } catch (ResourceAccessException ex) {
            log.error(API_INDISPONIVEL);
            return null;
        }
    }
}


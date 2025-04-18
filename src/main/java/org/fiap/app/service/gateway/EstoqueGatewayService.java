package org.fiap.app.service.gateway;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.gateway.GatewayApi;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.infra.exceptions.ObjectNotFoundException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import static org.fiap.domain.util.StringConstants.API_ESTOQUE_INDISPONIVEL;
import static org.fiap.domain.util.StringConstants.API_INDISPONIVEL;

@Service
@Slf4j
public class EstoqueGatewayService {

    private final GatewayApi gateway;

    public EstoqueGatewayService(GatewayApi gateway) {
        this.gateway = gateway;
    }

    public EstoqueDTO findByIdProduto(Long produtoId) {
        try {
            return gateway.estoqueFindByIdProduto(new GenericMessage<>(produtoId));
        } catch (ResourceAccessException ex) {
            log.error(API_INDISPONIVEL);
            throw new ObjectNotFoundException(API_ESTOQUE_INDISPONIVEL);
        }
    }

    public EstoqueDTO updateByIdProduto(EstoqueRequest request) {
        try {
            return gateway.estoqueUpdateByIdProduto(new GenericMessage<>(request));
        } catch (ResourceAccessException ex) {
            log.error(API_ESTOQUE_INDISPONIVEL);
            throw new ObjectNotFoundException(API_ESTOQUE_INDISPONIVEL);
        }
    }
}


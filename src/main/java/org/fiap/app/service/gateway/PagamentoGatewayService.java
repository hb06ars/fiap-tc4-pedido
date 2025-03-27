package org.fiap.app.service.gateway;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.gateway.GatewayApi;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.infra.exceptions.GlobalException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import static org.fiap.domain.util.StringConstants.API_PAGAMENTO_INDISPONIVEL;

@Service
@Slf4j
public class PagamentoGatewayService {

    private final GatewayApi gateway;

    public PagamentoGatewayService(GatewayApi gateway) {
        this.gateway = gateway;
    }

    public PagamentoDTO save(PagamentoDTO request) {
        try {
            log.info("Efetuando requisição para API de Pagamentos.");
            return gateway.pagamentoSave(new GenericMessage<>(request));
        } catch (ResourceAccessException ex) {
            log.error(API_PAGAMENTO_INDISPONIVEL);
            throw new GlobalException(API_PAGAMENTO_INDISPONIVEL);
        }
    }
}


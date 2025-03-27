package org.fiap.app.gateway;

import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface GatewayApi {

    @Gateway(requestChannel = "clienteFindById", requestTimeout = 5000)
    ClienteDTO clienteFindById(Message<Long> id);

    @Gateway(requestChannel = "produtoFindBySku", requestTimeout = 5000)
    ProdutoDTO produtoFindBySku(Message<String> sku);

    @Gateway(requestChannel = "estoqueFindByIdProduto", requestTimeout = 5000)
    EstoqueDTO estoqueFindByIdProduto(Message<Long> idProduto);

    @Gateway(requestChannel = "estoqueUpdateByIdProduto", requestTimeout = 5000)
    EstoqueDTO estoqueUpdateByIdProduto(Message<EstoqueRequest> request);

    @Gateway(requestChannel = "pagamentoSave", requestTimeout = 5000)
    PagamentoDTO pagamentoSave(Message<PagamentoDTO> request);
}

package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessarPedidoUseCaseImpl implements ProcessarPedidoUseCase {

    private final ClienteGatewayService clienteGatewayService;
    private final ProdutoGatewayService produtoGatewayService;

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        var resultado = clienteGatewayService.findByCpf("22553344888");
        var resultadoB = produtoGatewayService.findBySku("123456789");
        System.out.println(resultado.getCpf());
        System.out.println(resultadoB.getSku());
    }
}

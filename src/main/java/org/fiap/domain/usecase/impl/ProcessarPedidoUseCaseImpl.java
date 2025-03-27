package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.app.service.PagamentoGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.CalcularTotalPedidoUseCase;
import org.fiap.domain.usecase.EfetuarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ProcessarPedidoUseCaseImpl implements ProcessarPedidoUseCase {

    private final ClienteGatewayService clienteGatewayService;
    private final ProdutoGatewayService produtoGatewayService;
    private final PagamentoGatewayService pagamentoGatewayService;
    private final ValidarEstoqueUseCase validarEstoqueUseCase;
    private final EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase;
    private final CalcularTotalPedidoUseCase calcularTotalPedidoUseCase;
    private final SalvarPedidoUseCase salvarPedidoUseCase;
    private final PedidoMapper pedidoMapper;

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService, EstoqueGatewayService estoqueGatewayService, PagamentoGatewayService pagamentoGatewayService, ValidarEstoqueUseCase validarEstoqueUseCase, EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase, CalcularTotalPedidoUseCase calcularTotalPedidoUseCase, SalvarPedidoUseCase salvarPedidoUseCase, PedidoMapper pedidoMapper) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
        this.pagamentoGatewayService = pagamentoGatewayService;
        this.validarEstoqueUseCase = validarEstoqueUseCase;
        this.efetuarBaixaEstoqueUseCase = efetuarBaixaEstoqueUseCase;
        this.calcularTotalPedidoUseCase = calcularTotalPedidoUseCase;
        this.salvarPedidoUseCase = salvarPedidoUseCase;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
        BigDecimal totalCompra = calcularTotalPedidoUseCase.execute(pedidoDTO, produtos);
        pedidoDTO.setTotalCompra(totalCompra);

        PedidoDTO baixaEstoque = pedidoMapper.estoqueBaixaDto(produtos, new PedidoDTO(pedidoDTO));
        if (baixaEstoqueEfetuada(baixaEstoque)) {
            log.info("Salvando o pedido na base de dados.");
            PedidoDTO pedidoSalvo = salvarPedidoUseCase.execute(pedidoDTO);
            if (pedidoSalvo.getId() != null) {
                log.info("Efetuando pagamento.");

                pagamentoGatewayService.save(PagamentoDTO.builder()
                        .pedidoId(pedidoSalvo.getId())
                        .dtPagamento(LocalDateTime.now())
                        .dtAtualizacao(LocalDateTime.now())
                        .build());
            }
        }

    }

    private boolean baixaEstoqueEfetuada(PedidoDTO baixaEstoque) {
        if (validarEstoqueUseCase.execute(baixaEstoque)) {
            efetuarBaixaEstoqueUseCase.execute(baixaEstoque);
            return true;
        }
        return false;
    }

    private List<ProdutoDTO> buscarProdutos(PedidoDTO pedidoDTO) {
        return pedidoDTO.getItensPedidoList()
                .stream()
                .map(item -> produtoGatewayService.findBySku(item.getSkuProduto()))
                .toList();
    }

}

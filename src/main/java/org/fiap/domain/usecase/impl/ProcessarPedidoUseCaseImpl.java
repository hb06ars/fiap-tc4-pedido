package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.gateway.ClienteGatewayService;
import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.app.service.gateway.PagamentoGatewayService;
import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.app.service.postgres.ItensPedidoService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.CalcularTotalPedidoUseCase;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.EfetuarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.fiap.infra.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class ProcessarPedidoUseCaseImpl implements ProcessarPedidoUseCase {

    private final ClienteGatewayService clienteGatewayService;
    private final ProdutoGatewayService produtoGatewayService;
    private final PagamentoGatewayService pagamentoGatewayService;
    private final ValidarEstoqueUseCase validarEstoqueUseCase;
    private final EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase;
    private final CalcularTotalPedidoUseCase calcularTotalPedidoUseCase;
    private final CancelarBaixaEstoqueUseCase cancelarBaixaEstoqueUseCase;
    private final SalvarPedidoUseCase salvarPedidoUseCase;
    private final PedidoService pedidoService;
    private final ItensPedidoService itensPedidoService;
    private final PedidoMapper pedidoMapper;

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService, EstoqueGatewayService estoqueGatewayService, PagamentoGatewayService pagamentoGatewayService, ValidarEstoqueUseCase validarEstoqueUseCase, EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase, CalcularTotalPedidoUseCase calcularTotalPedidoUseCase, CancelarBaixaEstoqueUseCase cancelarBaixaEstoqueUseCase, SalvarPedidoUseCase salvarPedidoUseCase, PedidoService pedidoService, ItensPedidoService itensPedidoService, PedidoMapper pedidoMapper) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
        this.pagamentoGatewayService = pagamentoGatewayService;
        this.validarEstoqueUseCase = validarEstoqueUseCase;
        this.efetuarBaixaEstoqueUseCase = efetuarBaixaEstoqueUseCase;
        this.calcularTotalPedidoUseCase = calcularTotalPedidoUseCase;
        this.cancelarBaixaEstoqueUseCase = cancelarBaixaEstoqueUseCase;
        this.salvarPedidoUseCase = salvarPedidoUseCase;
        this.pedidoService = pedidoService;
        this.itensPedidoService = itensPedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        if (cliente != null) {
            List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
            BigDecimal totalCompra = calcularTotalPedidoUseCase.execute(pedidoDTO, produtos);

            PedidoDTO baixaEstoque = pedidoMapper.estoqueBaixaDto(produtos, new PedidoDTO(pedidoDTO));
            if (baixaEstoqueEfetuada(baixaEstoque)) {
                PagamentoDTO pagamento = PagamentoDTO.builder()
                        .valorTotal(totalCompra)
                        .dtPagamento(LocalDateTime.now())
                        .dtAtualizacao(LocalDateTime.now())
                        .build();
                try {
                    log.info("Salvando o pedido na base de dados.");
                    pedidoDTO.setValorTotal(totalCompra);
                    PedidoDTO pedidoSalvo = salvarPedidoUseCase.execute(pedidoDTO);

                    if (pedidoSalvo.getId() != null) {
                        log.info("Efetuando pagamento.");

                        pagamento.setPedidoId(pedidoSalvo.getId());

                        var resultadoPagamento = pagamentoGatewayService.save(pagamento);
                        if (resultadoPagamento != null)
                            pedidoDTO.setStatus(resultadoPagamento.getStatusPagamento());
                        else
                            pedidoDTO.setStatus(StatusPagamentoEnum.FECHADO_SEM_CREDITO);
                        pedidoService.save(new PedidoEntity(pedidoDTO));
                        log.info("Processamento do pedido finalizada.");
                    }
                } catch (Exception e) {
                    log.error("Erro ao efetuar processamento do pedido: {}", e.getMessage());
                    if (Objects.nonNull(pagamento) && pagamento.getPedidoId() != null) {
                        log.error("Efetuando rollback de estoque.");
                        cancelarBaixaEstoqueUseCase.execute(pagamento);
                        throw new ObjectNotFoundException("Erro ao efetuar o pagamento do pedido.");
                    }
                }
            } else {
                log.error("Não há estoque disponível para o pedido.");
                log.error("Cancelando o pedido.");
                pedidoDTO.setStatus(StatusPagamentoEnum.FECHADO_SEM_ESTOQUE);
                pedidoDTO.setValorTotal(totalCompra);
                var pedidoCancelado = pedidoService.save(new PedidoEntity(pedidoDTO));

                pedidoDTO.getItensPedidoList()
                        .forEach(item -> {
                            ItensPedidoEntity itensPedidoEntity = new ItensPedidoEntity(item);
                            itensPedidoEntity.setPedidoId(pedidoCancelado.getId());
                            itensPedidoEntity.setDtAtualizacao(LocalDateTime.now());
                            itensPedidoService.save(itensPedidoEntity);
                        });

                log.error("Pedido cancelado.");
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

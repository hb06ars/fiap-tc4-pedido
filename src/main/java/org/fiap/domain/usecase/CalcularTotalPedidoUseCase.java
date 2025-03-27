package org.fiap.domain.usecase;

import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CalcularTotalPedidoUseCase {
    BigDecimal execute(PedidoDTO pedidoDTO, List<ProdutoDTO> produtos);
}

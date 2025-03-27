package org.fiap.app.service.postgres;

import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.infra.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoDTO findById(Long id) {
        return new PedidoDTO(Objects.requireNonNull(pedidoRepository.findById(id).orElse(null)));
    }

    public PedidoDTO save(PedidoEntity pedido) {
        return new PedidoDTO(pedidoRepository.save(pedido));
    }
}

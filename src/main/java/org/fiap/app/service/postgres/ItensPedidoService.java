package org.fiap.app.service.postgres;

import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensPedidoService {

    private final ItensPedidoRepository itensPedidoRepository;

    public ItensPedidoService(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    public List<ItensPedidoDTO> findByPedidoId(Long pedidoId) {
        return itensPedidoRepository
                .findByPedidoId(pedidoId).stream().map(ItensPedidoDTO::new).toList();
    }

    public ItensPedidoDTO save(ItensPedidoEntity itensPedidoEntity) {
        return new ItensPedidoDTO(itensPedidoRepository.save(itensPedidoEntity));
    }
}

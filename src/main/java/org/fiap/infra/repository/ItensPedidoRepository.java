package org.fiap.infra.repository;

import org.fiap.domain.entity.ItensPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensPedidoRepository extends JpaRepository<ItensPedidoEntity, Long> {

    List<ItensPedidoEntity> findByPedidoId(Long pedidoId);
}
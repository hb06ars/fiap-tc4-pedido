package org.fiap.infra.repository;

import org.fiap.domain.entity.ItensPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepository extends JpaRepository<ItensPedidoEntity, Long> {
}
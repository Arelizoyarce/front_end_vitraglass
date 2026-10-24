package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstadoPedidoNombreEstado(String nombreEstado);

}
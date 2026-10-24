package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    List<Cotizacion> findAllByOrderByFechaDesc();

    List<Cotizacion> findByEstadoOrderByFechaDesc(String estado);

    List<Cotizacion> findByClienteContainingIgnoreCaseOrderByFechaDesc(String cliente);
}

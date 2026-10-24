package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {

    List<DetalleCotizacion> findByCotizacionIdCotizacion(Integer idCotizacion);
}

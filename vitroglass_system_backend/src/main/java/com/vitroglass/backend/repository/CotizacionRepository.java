package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    List<Cotizacion> findByEstado(String estado);

    List<Cotizacion> findAllByOrderByFechaCotizacionDesc();
    
    List<Cotizacion> findByUsuarioCorreoElectronicoOrderByFechaCotizacionDesc(String correo);

    @Query("""
        SELECT c FROM Cotizacion c
        JOIN FETCH c.cliente
        JOIN FETCH c.usuario
        WHERE c.idCotizacion = :id
    """)
    Optional<Cotizacion> findByIdCompleto(@Param("id") Integer id);
    
}
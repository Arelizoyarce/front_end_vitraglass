package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.TipoVidrio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoVidrioRepository extends JpaRepository<TipoVidrio, Integer> {

    List<TipoVidrio> findByEstado(String estado);

    List<TipoVidrio> findByEstadoIgnoreCase(String estado);

    boolean existsByNombreIgnoreCase(String nombre);
}

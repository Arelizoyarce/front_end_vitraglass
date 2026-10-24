package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.TipoVidrio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TipoVidrioRepository extends JpaRepository<TipoVidrio, Integer> {

    List<TipoVidrio> findByEstado(String estado);
}
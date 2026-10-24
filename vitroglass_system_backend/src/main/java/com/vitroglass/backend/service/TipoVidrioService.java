package com.vitroglass.backend.service;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.repository.TipoVidrioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoVidrioService {

    @Autowired
    private TipoVidrioRepository tipoVidrioRepository;

    public List<TipoVidrio> listarActivos() {
        return tipoVidrioRepository.findByEstado("ACTIVO");
    }

    public List<TipoVidrio> listarTodos() {
        return tipoVidrioRepository.findAll();
    }

    public TipoVidrio guardar(TipoVidrio tipoVidrio) {
        tipoVidrio.setEstado("ACTIVO");
        return tipoVidrioRepository.save(tipoVidrio);
    }

    public TipoVidrio actualizar(Integer id, TipoVidrio datos) {
        TipoVidrio existing = tipoVidrioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vidrio no encontrado"));
        existing.setNombre(datos.getNombre());
        existing.setDescripcion(datos.getDescripcion());
        existing.setGrosorMm(datos.getGrosorMm());
        existing.setPrecioMetroCuadrado(datos.getPrecioMetroCuadrado());
        existing.setEstado(datos.getEstado());
        return tipoVidrioRepository.save(existing);
    }

    public void desactivar(Integer id) {
        TipoVidrio existing = tipoVidrioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vidrio no encontrado"));
        existing.setEstado("INACTIVO");
        tipoVidrioRepository.save(existing);
    }
}
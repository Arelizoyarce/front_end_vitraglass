package com.vitroglass.backend.service;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.repository.TipoVidrioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoVidrioService {

    private final TipoVidrioRepository tipoVidrioRepository;

    @Autowired
    public TipoVidrioService(TipoVidrioRepository tipoVidrioRepository) {
        this.tipoVidrioRepository = tipoVidrioRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoVidrio> listarTodos() {
        return tipoVidrioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TipoVidrio> listarActivos() {
        return tipoVidrioRepository.findByEstadoIgnoreCase("ACTIVO");
    }

    @Transactional(readOnly = true)
    public TipoVidrio obtenerPorId(Integer id) {
        return tipoVidrioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de vidrio no encontrado con ID: " + id));
    }

    public TipoVidrio crear(TipoVidrio tipoVidrio) {
        validarTipoVidrio(tipoVidrio);
        if (tipoVidrio.getEstado() == null || tipoVidrio.getEstado().trim().isEmpty()) {
            tipoVidrio.setEstado("ACTIVO");
        }
        return tipoVidrioRepository.save(tipoVidrio);
    }

    public TipoVidrio actualizar(Integer id, TipoVidrio datosActualizados) {
        TipoVidrio existente = obtenerPorId(id);
        validarTipoVidrio(datosActualizados);

        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setGrosorMm(datosActualizados.getGrosorMm());
        existente.setPrecioMetroCuadrado(datosActualizados.getPrecioMetroCuadrado());

        if (datosActualizados.getEstado() != null && !datosActualizados.getEstado().trim().isEmpty()) {
            existente.setEstado(datosActualizados.getEstado());
        }

        return tipoVidrioRepository.save(existente);
    }

    public void desactivar(Integer id) {
        TipoVidrio existente = obtenerPorId(id);
        existente.setEstado("INACTIVO");
        tipoVidrioRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if (!tipoVidrioRepository.existsById(id)) {
            throw new RuntimeException("Tipo de vidrio no encontrado para eliminar con ID: " + id);
        }
        tipoVidrioRepository.deleteById(id);
    }

    private void validarTipoVidrio(TipoVidrio tipoVidrio) {
        if (tipoVidrio == null) {
            throw new IllegalArgumentException("Los datos del tipo de vidrio no pueden ser nulos.");
        }
        if (tipoVidrio.getNombre() == null || tipoVidrio.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de vidrio es obligatorio.");
        }
        if (tipoVidrio.getPrecioMetroCuadrado() == null || tipoVidrio.getPrecioMetroCuadrado() <= 0) {
            throw new IllegalArgumentException("El precio base por metro cuadrado debe ser un valor positivo.");
        }
        if (tipoVidrio.getGrosorMm() == null || tipoVidrio.getGrosorMm() <= 0) {
            throw new IllegalArgumentException("El grosor en milímetros debe ser un valor positivo.");
        }
    }
}

package com.vitroglass.backend.service;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.repository.TipoVidrioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de negocio para la gestión del catálogo de tipos de vidrio.
 *
 * <p>Ofrece las operaciones de consulta, creación, actualización,
 * desactivación lógica y eliminación física de {@link TipoVidrio}, aplicando las
 * validaciones necesarias antes de persistir los datos.
 *
 * <p>La clase completa se ejecuta dentro de una transacción
 * ({@link Transactional}); los métodos de solo lectura se marcan con
 * {@code readOnly = true} para optimizar el acceso a la base de datos.
 *
 * @see TipoVidrio
 * @see TipoVidrioRepository
 */
@Service
@Transactional
public class TipoVidrioService {

    /** Repositorio de acceso a datos del catálogo de tipos de vidrio. */
    private final TipoVidrioRepository tipoVidrioRepository;

    /**
     * Construye el servicio con su dependencia (inyección por constructor).
     *
     * @param tipoVidrioRepository repositorio del catálogo de tipos de vidrio
     */
    @Autowired
    public TipoVidrioService(TipoVidrioRepository tipoVidrioRepository) {
        this.tipoVidrioRepository = tipoVidrioRepository;
    }

    /**
     * Lista todos los tipos de vidrio registrados, sin importar su estado.
     *
     * @return lista de tipos de vidrio; vacía si no existe ninguno
     */
    @Transactional(readOnly = true)
    public List<TipoVidrio> listarTodos() {
        return tipoVidrioRepository.findAll();
    }

    /**
     * Lista únicamente los tipos de vidrio cuyo estado es {@code "ACTIVO"}
     * (la comparación no distingue mayúsculas de minúsculas).
     *
     * @return lista de tipos de vidrio activos; vacía si no existe ninguno
     */
    @Transactional(readOnly = true)
    public List<TipoVidrio> listarActivos() {
        return tipoVidrioRepository.findByEstadoIgnoreCase("ACTIVO");
    }

    /**
     * Obtiene un tipo de vidrio por su identificador.
     *
     * @param id identificador del tipo de vidrio
     * @return el tipo de vidrio encontrado
     * @throws RuntimeException si no existe un tipo de vidrio con el ID indicado
     */
    @Transactional(readOnly = true)
    public TipoVidrio obtenerPorId(Integer id) {
        return tipoVidrioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de vidrio no encontrado con ID: " + id));
    }

    /**
     * Crea y persiste un nuevo tipo de vidrio.
     *
     * <p>Valida los datos recibidos; si no se indica un estado, se asigna
     * {@code "ACTIVO"} por defecto.
     *
     * @param tipoVidrio datos del tipo de vidrio a crear
     * @return el tipo de vidrio guardado
     * @throws IllegalArgumentException si los datos son nulos, el nombre está
     *         vacío, o el precio por m² o el grosor no son positivos
     */
    public TipoVidrio crear(TipoVidrio tipoVidrio) {
        validarTipoVidrio(tipoVidrio);
        if (tipoVidrio.getEstado() == null || tipoVidrio.getEstado().trim().isEmpty()) {
            tipoVidrio.setEstado("ACTIVO");
        }
        return tipoVidrioRepository.save(tipoVidrio);
    }

    /**
     * Actualiza los datos de un tipo de vidrio existente.
     *
     * <p>Se reemplazan nombre, descripción, grosor y precio por m². El estado
     * solo se modifica si en los datos recibidos viene informado (no nulo ni
     * vacío).
     *
     * @param id                 identificador del tipo de vidrio a actualizar
     * @param datosActualizados  nuevos datos del tipo de vidrio
     * @return el tipo de vidrio actualizado
     * @throws RuntimeException si no existe un tipo de vidrio con el ID indicado
     * @throws IllegalArgumentException si los nuevos datos no superan la
     *         validación (nombre vacío, precio o grosor no positivos, o datos
     *         nulos)
     */
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

    /**
     * Desactiva lógicamente un tipo de vidrio, cambiando su estado a
     * {@code "INACTIVO"} sin eliminarlo de la base de datos.
     *
     * @param id identificador del tipo de vidrio a desactivar
     * @throws RuntimeException si no existe un tipo de vidrio con el ID indicado
     */
    public void desactivar(Integer id) {
        TipoVidrio existente = obtenerPorId(id);
        existente.setEstado("INACTIVO");
        tipoVidrioRepository.save(existente);
    }

    /**
     * Elimina físicamente un tipo de vidrio de la base de datos.
     *
     * <p>Para conservar el registro, preferir {@link #desactivar(Integer)}.
     *
     * @param id identificador del tipo de vidrio a eliminar
     * @throws RuntimeException si no existe un tipo de vidrio con el ID indicado
     */
    public void eliminar(Integer id) {
        if (!tipoVidrioRepository.existsById(id)) {
            throw new RuntimeException("Tipo de vidrio no encontrado para eliminar con ID: " + id);
        }
        tipoVidrioRepository.deleteById(id);
    }

    /**
     * Valida las reglas de negocio de un tipo de vidrio antes de guardarlo.
     *
     * <p>Reglas: el objeto no puede ser nulo, el nombre es obligatorio, y tanto
     * el precio base por metro cuadrado como el grosor en milímetros deben ser
     * valores positivos.
     *
     * @param tipoVidrio tipo de vidrio a validar
     * @throws IllegalArgumentException si alguna de las reglas no se cumple
     */
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
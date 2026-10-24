package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.dto.CotizacionResponseDTO;
import com.vitroglass.backend.dto.DetalleRequestDTO;
import com.vitroglass.backend.dto.DetalleResponseDTO;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.model.DetalleCotizacion;
import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.repository.CotizacionRepository;
import com.vitroglass.backend.repository.TipoVidrioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para la gestión de cotizaciones de vidrio.
 *
 * <p>Centraliza las reglas de negocio asociadas a las cotizaciones:
 * <ul>
 *   <li>Validación de la solicitud recibida desde la capa de presentación.</li>
 *   <li>Cálculo automático de área, subtotales y total de cada cotización.</li>
 *   <li>Consulta de cotizaciones existentes.</li>
 *   <li>Actualización del estado y de la fecha de entrega.</li>
 * </ul>
 *
 * <p>La clase completa se ejecuta dentro de una transacción
 * ({@link Transactional}); los métodos de solo lectura se marcan con
 * {@code readOnly = true} para optimizar el acceso a la base de datos.
 *
 * @see CotizacionRepository
 * @see TipoVidrioRepository
 * @see CotizacionRequestDTO
 * @see CotizacionResponseDTO
 */
@Service
@Transactional
public class CotizacionService {

    /** Repositorio de acceso a datos de las cotizaciones. */
    private final CotizacionRepository cotizacionRepository;

    /** Repositorio de acceso a datos del catálogo de tipos de vidrio. */
    private final TipoVidrioRepository tipoVidrioRepository;

    /**
     * Construye el servicio con sus dependencias (inyección por constructor).
     *
     * @param cotizacionRepository repositorio de cotizaciones
     * @param tipoVidrioRepository repositorio del catálogo de tipos de vidrio
     */
    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository, TipoVidrioRepository tipoVidrioRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.tipoVidrioRepository = tipoVidrioRepository;
    }

    /**
     * Crea y persiste una nueva cotización a partir de los datos recibidos.
     *
     * <p>Flujo del proceso:
     * <ol>
     *   <li>Valida la solicitud (cliente y al menos un detalle).</li>
     *   <li>Crea la cabecera con la fecha actual y el estado inicial
     *       {@code "COTIZADO"}.</li>
     *   <li>Por cada detalle valida los datos, busca el tipo de vidrio y calcula:
     *     <ul>
     *       <li><b>Área</b> = ancho × alto (redondeada a 4 decimales).</li>
     *       <li><b>Precio unitario</b> por m²: el enviado si es positivo; de lo
     *           contrario, el precio base del catálogo.</li>
     *       <li><b>Subtotal</b> = área × precio unitario × cantidad
     *           (redondeado a 2 decimales).</li>
     *     </ul>
     *   </li>
     *   <li>Acumula los subtotales en el total de la cotización (redondeado a
     *       2 decimales) y guarda el resultado.</li>
     * </ol>
     *
     * @param requestDTO datos de la cotización a crear (cliente, fecha de entrega
     *                   y lista de detalles)
     * @return la cotización guardada, representada como {@link CotizacionResponseDTO}
     * @throws IllegalArgumentException si la solicitud es nula, el cliente está
     *         vacío, no hay detalles, o algún detalle tiene id de tipo de vidrio
     *         nulo, cantidad no positiva, o ancho/alto no positivos
     * @throws RuntimeException si algún tipo de vidrio indicado no existe en el
     *         catálogo
     */
    public CotizacionResponseDTO crearCotizacion(CotizacionRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La solicitud de cotización no puede ser nula.");
        }
        if (requestDTO.getCliente() == null || requestDTO.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (requestDTO.getDetalles() == null || requestDTO.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La cotización debe incluir al menos un detalle de vidrio.");
        }

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(requestDTO.getCliente().trim());
        cotizacion.setFecha(LocalDateTime.now());
        cotizacion.setFechaEntrega(requestDTO.getFechaEntrega());
        cotizacion.setEstado("COTIZADO");

        double totalAcumulado = 0.0;

        for (DetalleRequestDTO detDTO : requestDTO.getDetalles()) {
            if (detDTO.getIdTipoVidrio() == null) {
                throw new IllegalArgumentException("El id del tipo de vidrio es obligatorio en cada detalle.");
            }
            if (detDTO.getCantidad() == null || detDTO.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
            }
            if (detDTO.getAncho() == null || detDTO.getAncho() <= 0) {
                throw new IllegalArgumentException("El ancho debe ser un valor positivo.");
            }
            if (detDTO.getAlto() == null || detDTO.getAlto() <= 0) {
                throw new IllegalArgumentException("El alto debe ser un valor positivo.");
            }

            TipoVidrio tipoVidrio = tipoVidrioRepository.findById(detDTO.getIdTipoVidrio())
                    .orElseThrow(() -> new RuntimeException("Tipo de vidrio no encontrado con ID: " + detDTO.getIdTipoVidrio()));

            // Cálculo automático de área (ancho * alto)
            double areaCalculada = Math.round((detDTO.getAncho() * detDTO.getAlto()) * 10000.0) / 10000.0;

            // Precio unitario por m2: si viene especificado y es positivo se respeta, sino se toma el precio base del catálogo
            double precioUnitario = (detDTO.getPrecioUnitario() != null && detDTO.getPrecioUnitario() > 0)
                    ? detDTO.getPrecioUnitario()
                    : tipoVidrio.getPrecioMetroCuadrado();

            // Subtotal = área * precioUnitario * cantidad
            double subtotalCalculado = Math.round((areaCalculada * precioUnitario * detDTO.getCantidad()) * 100.0) / 100.0;

            DetalleCotizacion detalle = new DetalleCotizacion();
            detalle.setTipoVidrio(tipoVidrio);
            detalle.setCantidad(detDTO.getCantidad());
            detalle.setAncho(detDTO.getAncho());
            detalle.setAlto(detDTO.getAlto());
            detalle.setArea(areaCalculada);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotalCalculado);

            cotizacion.addDetalle(detalle);
            totalAcumulado += subtotalCalculado;
        }

        cotizacion.setTotal(Math.round(totalAcumulado * 100.0) / 100.0);
        Cotizacion guardada = cotizacionRepository.save(cotizacion);

        return mapearADTO(guardada);
    }

    /**
     * Lista todas las cotizaciones registradas, ordenadas por fecha de creación
     * de la más reciente a la más antigua.
     *
     * @return lista de cotizaciones como {@link CotizacionResponseDTO}; vacía si
     *         no existe ninguna
     */
    @Transactional(readOnly = true)
    public List<CotizacionResponseDTO> listarTodas() {
        return cotizacionRepository.findAllByOrderByFechaDesc()
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una cotización por su identificador.
     *
     * @param id identificador de la cotización
     * @return la cotización encontrada como {@link CotizacionResponseDTO}
     * @throws RuntimeException si no existe una cotización con el ID indicado
     */
    @Transactional(readOnly = true)
    public CotizacionResponseDTO obtenerPorId(Integer id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con ID: " + id));
        return mapearADTO(cotizacion);
    }

    /**
     * Actualiza el estado y/o la fecha de entrega de una cotización existente.
     *
     * <p>Ambos parámetros son opcionales: solo se modifican los que no sean
     * {@code null} ni cadenas vacías. El nuevo estado se normaliza a mayúsculas
     * y se eliminan los espacios sobrantes en ambos valores.
     *
     * @param id           identificador de la cotización a actualizar
     * @param nuevoEstado  nuevo estado (por ejemplo, {@code "aprobado"}); si es
     *                     {@code null} o vacío, el estado no cambia
     * @param fechaEntrega nueva fecha de entrega; si es {@code null} o vacía, no
     *                     se modifica
     * @return la cotización actualizada como {@link CotizacionResponseDTO}
     * @throws RuntimeException si no existe una cotización con el ID indicado
     */
    public CotizacionResponseDTO actualizarEstado(Integer id, String nuevoEstado, String fechaEntrega) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con ID: " + id));

        if (nuevoEstado != null && !nuevoEstado.trim().isEmpty()) {
            cotizacion.setEstado(nuevoEstado.trim().toUpperCase());
        }
        if (fechaEntrega != null && !fechaEntrega.trim().isEmpty()) {
            cotizacion.setFechaEntrega(fechaEntrega.trim());
        }

        Cotizacion actualizada = cotizacionRepository.save(cotizacion);
        return mapearADTO(actualizada);
    }

    /**
     * Convierte una entidad {@link Cotizacion} en su DTO de respuesta, incluyendo
     * la lista de detalles (con los datos del tipo de vidrio de cada uno) y la
     * cantidad total de detalles.
     *
     * @param cotizacion entidad a convertir
     * @return DTO con la información lista para ser devuelta a la capa de
     *         presentación
     */
    private CotizacionResponseDTO mapearADTO(Cotizacion cotizacion) {
        List<DetalleResponseDTO> detallesDTO = new ArrayList<>();
        if (cotizacion.getDetalles() != null) {
            for (DetalleCotizacion d : cotizacion.getDetalles()) {
                DetalleResponseDTO det = new DetalleResponseDTO(
                        d.getIdDetalle(),
                        d.getTipoVidrio().getIdTipoVidrio(),
                        d.getTipoVidrio().getNombre(),
                        d.getTipoVidrio().getGrosorMm(),
                        d.getCantidad(),
                        d.getAncho(),
                        d.getAlto(),
                        d.getArea(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()
                );
                detallesDTO.add(det);
            }
        }

        return new CotizacionResponseDTO(
                cotizacion.getIdCotizacion(),
                cotizacion.getCliente(),
                cotizacion.getFecha(),
                cotizacion.getFechaEntrega(),
                detallesDTO.size(),
                cotizacion.getTotal(),
                cotizacion.getEstado(),
                detallesDTO
        );
    }
}
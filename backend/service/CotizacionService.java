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

@Service
@Transactional
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private final TipoVidrioRepository tipoVidrioRepository;

    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository, TipoVidrioRepository tipoVidrioRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.tipoVidrioRepository = tipoVidrioRepository;
    }

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

    @Transactional(readOnly = true)
    public List<CotizacionResponseDTO> listarTodas() {
        return cotizacionRepository.findAllByOrderByFechaDesc()
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CotizacionResponseDTO obtenerPorId(Integer id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con ID: " + id));
        return mapearADTO(cotizacion);
    }

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

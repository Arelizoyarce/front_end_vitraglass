package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.dto.CotizacionResponseDTO;
import com.vitroglass.backend.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "*")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    @Autowired
    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    /**
     * Registra una cotización con cálculo automático de áreas, subtotales y total general.
     */
    @PostMapping
    public ResponseEntity<CotizacionResponseDTO> crear(@RequestBody CotizacionRequestDTO requestDTO) {
        CotizacionResponseDTO creada = cotizacionService.crearCotizacion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * Lista todas las cotizaciones registradas para el panel comercial / dashboard.
     */
    @GetMapping
    public ResponseEntity<List<CotizacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(cotizacionService.listarTodas());
    }

    /**
     * Consulta el desglose completo de una cotización y sus piezas por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CotizacionResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(cotizacionService.obtenerPorId(id));
    }

    /**
     * Actualiza el estado comercial de la cotización y su fecha pactada de entrega.
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<CotizacionResponseDTO> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body) {

        String nuevoEstado = body.get("estado") != null ? body.get("estado").toString() : null;
        String fechaEntrega = body.get("fechaEntrega") != null ? body.get("fechaEntrega").toString() : null;

        CotizacionResponseDTO actualizada = cotizacionService.actualizarEstado(id, nuevoEstado, fechaEntrega);
        return ResponseEntity.ok(actualizada);
    }
}

package com.vitroglass.backend.controller;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.service.TipoVidrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-vidrio")
@CrossOrigin(origins = "*")
public class TipoVidrioController {

    private final TipoVidrioService tipoVidrioService;

    @Autowired
    public TipoVidrioController(TipoVidrioService tipoVidrioService) {
        this.tipoVidrioService = tipoVidrioService;
    }

    /**
     * Endpoint público para el cotizador: devuelve únicamente los vidrios en estado ACTIVO.
     */
    @GetMapping
    public ResponseEntity<List<TipoVidrio>> listarActivos() {
        return ResponseEntity.ok(tipoVidrioService.listarActivos());
    }

    /**
     * Endpoint administrativo: devuelve todo el catálogo (activos e inactivos).
     */
    @GetMapping("/admin")
    public ResponseEntity<List<TipoVidrio>> listarTodosAdmin() {
        return ResponseEntity.ok(tipoVidrioService.listarTodos());
    }

    /**
     * Consulta las especificaciones de un tipo de vidrio por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoVidrio> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoVidrioService.obtenerPorId(id));
    }

    /**
     * Registra un nuevo tipo de vidrio en el catálogo maestro.
     */
    @PostMapping("/admin")
    public ResponseEntity<TipoVidrio> crear(@RequestBody TipoVidrio tipoVidrio) {
        TipoVidrio creado = tipoVidrioService.crear(tipoVidrio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Actualiza las especificaciones técnicas o costos de un vidrio existente.
     */
    @PutMapping("/admin/{id}")
    public ResponseEntity<TipoVidrio> actualizar(@PathVariable Integer id, @RequestBody TipoVidrio datosActualizados) {
        TipoVidrio actualizado = tipoVidrioService.actualizar(id, datosActualizados);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Desactiva lógicamente un tipo de vidrio del catálogo.
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        tipoVidrioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.vitroglass.backend.controller;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.service.TipoVidrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-vidrio")
@CrossOrigin("*")
public class TipoVidrioController {

    @Autowired
    private TipoVidrioService tipoVidrioService;

    // Vendedor: solo activos (para usar en cotizaciones)
    @GetMapping
    public List<TipoVidrio> listar() {
        return tipoVidrioService.listarActivos();
    }

    // Admin: todos incluidos inactivos
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TipoVidrio> listarTodos() {
        return tipoVidrioService.listarTodos();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public TipoVidrio crear(@RequestBody TipoVidrio tipoVidrio) {
        return tipoVidrioService.guardar(tipoVidrio);
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TipoVidrio actualizar(@PathVariable Integer id,
                                  @RequestBody TipoVidrio tipoVidrio) {
        return tipoVidrioService.actualizar(id, tipoVidrio);
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivar(@PathVariable Integer id) {
        tipoVidrioService.desactivar(id);
    }
}
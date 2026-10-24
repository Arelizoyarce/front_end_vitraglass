package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.DashboardItemDTO;
import com.vitroglass.backend.model.Pedido;
import com.vitroglass.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Integer id) {
        return pedidoService.obtenerPorId(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Pedido> listarPorEstado(@PathVariable String estado) {
        return pedidoService.listarPorEstado(estado);
    }

    // Vendedor: solo ve sus cotizaciones
    @GetMapping("/dashboard")
    public List<DashboardItemDTO> dashboard(Authentication authentication) {
        String correo = authentication.getName();
        return pedidoService.listarDashboard(correo);
    }

    // Admin: ve todas las cotizaciones
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DashboardItemDTO> adminDashboard() {
        return pedidoService.listarDashboard();
    }
}
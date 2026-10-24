package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.DashboardItemDTO;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.model.Pedido;
import com.vitroglass.backend.repository.CotizacionRepository;
import com.vitroglass.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    public List<Pedido> listarPorEstado(String estado) {
        return pedidoRepository.findByEstadoPedidoNombreEstado(estado);
    }

    // Para vendedor: solo sus cotizaciones
    public List<DashboardItemDTO> listarDashboard(String correoUsuario) {
        List<Cotizacion> cotizaciones = cotizacionRepository
                .findByUsuarioCorreoElectronicoOrderByFechaCotizacionDesc(correoUsuario);
        return mapearDashboard(cotizaciones);
    }

    // Para admin: todas las cotizaciones
    public List<DashboardItemDTO> listarDashboard() {
        List<Cotizacion> cotizaciones = cotizacionRepository
                .findAllByOrderByFechaCotizacionDesc();
        return mapearDashboard(cotizaciones);
    }

    private List<DashboardItemDTO> mapearDashboard(List<Cotizacion> cotizaciones) {
        List<DashboardItemDTO> response = new ArrayList<>();
        for (Cotizacion c : cotizaciones) {
            DashboardItemDTO dto = new DashboardItemDTO();
            dto.setId(c.getIdCotizacion());
            dto.setTipo(
                c.getEstado().equalsIgnoreCase("EN PROCESO")
                    ? "PEDIDO"
                    : "COTIZACION"
            );
            dto.setCliente(
                c.getCliente().getNombres() + " " +
                c.getCliente().getApellidos()
            );
            dto.setFecha(
                c.getFechaCotizacion() != null
                    ? c.getFechaCotizacion().toString()
                    : "-"
            );
            dto.setItems(
                c.getDetalleCotizaciones() != null
                    ? c.getDetalleCotizaciones().size()
                    : 0
            );
            dto.setTotal(c.getTotal());
            dto.setEstado(c.getEstado());
            response.add(dto);
        }
        return response;
    }
}
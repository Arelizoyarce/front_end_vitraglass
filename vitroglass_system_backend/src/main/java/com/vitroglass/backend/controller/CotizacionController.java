package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.service.BoletaService;
import com.vitroglass.backend.service.CotizacionService;
import com.vitroglass.backend.service.EmailService;
import com.vitroglass.backend.service.PdfService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin("*")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;
    
    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BoletaService boletaService;

    @PostMapping
    public Cotizacion crear(@RequestBody CotizacionRequestDTO dto) {
        return cotizacionService.crear(dto);
    }

    @GetMapping
    public List<Cotizacion> listar() {
        return cotizacionService.listarCotizaciones();
    }

    @GetMapping("/{id}")
    public Cotizacion obtenerPorId(@PathVariable Integer id) {
        return cotizacionService.obtenerPorId(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Cotizacion> listarPorEstado(@PathVariable String estado) {
        return cotizacionService.listarPorEstado(estado);
    }
    
    @PutMapping("/{id}/estado")
public Cotizacion actualizarEstado(
        @PathVariable Integer id,
        @RequestBody Map<String, Object> body
) {
    String estado = (String) body.get("estado");

    LocalDateTime fechaEntrega = null;

    if (body.get("fechaEntrega") != null) {
        fechaEntrega = LocalDate.parse(
        body.get("fechaEntrega").toString()
).atStartOfDay();
    }

    return cotizacionService.actualizarEstado(id, estado, fechaEntrega);
}

@GetMapping("/{id}/pdf")
public ResponseEntity<byte[]> descargarPdf(@PathVariable Integer id) {
    Cotizacion cotizacion = cotizacionService.obtenerPorId(id);
    byte[] pdf = pdfService.generarCotizacionPdf(cotizacion);

    String filename = "Cotizacion_" + String.format("%06d", id) + ".pdf";

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}


@PostMapping("/{id}/enviar-correo")
public ResponseEntity<Map<String, String>> enviarPorCorreo(@PathVariable Integer id) {
    Cotizacion cotizacion = cotizacionService.obtenerPorId(id);

    String correoCliente = cotizacion.getCliente().getCorreoElectronico();
    if (correoCliente == null || correoCliente.isEmpty()) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "El cliente no tiene correo electrónico registrado"));
    }

    byte[] pdf = pdfService.generarCotizacionPdf(cotizacion);
    String nombreCliente = cotizacion.getCliente().getNombres() + " " +
            cotizacion.getCliente().getApellidos();

    emailService.enviarCotizacionPorCorreo(correoCliente, nombreCliente,
            cotizacion.getIdCotizacion(), pdf);

    return ResponseEntity.ok(Map.of("mensaje", "Cotización enviada al correo " + correoCliente));
}


@GetMapping("/{id}/boleta-pdf")
public ResponseEntity<byte[]> descargarBoleta(@PathVariable Integer id) {
    Cotizacion cotizacion = cotizacionService.obtenerPorId(id);

    if (!"COMPLETADO".equalsIgnoreCase(cotizacion.getEstado())) {
        return ResponseEntity.badRequest().build();
    }

    byte[] pdf = boletaService.generarBoletaPdf(cotizacion);
    String filename = "Boleta_B001-" + String.format("%06d", id) + ".pdf";

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}

@PostMapping("/{id}/enviar-boleta")
public ResponseEntity<Map<String, String>> enviarBoleta(@PathVariable Integer id) {
    Cotizacion cotizacion = cotizacionService.obtenerPorId(id);

    if (!"COMPLETADO".equalsIgnoreCase(cotizacion.getEstado())) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Solo se puede enviar boleta de ventas completadas"));
    }

    String correoCliente = cotizacion.getCliente().getCorreoElectronico();
    if (correoCliente == null || correoCliente.isEmpty()) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "El cliente no tiene correo electrónico registrado"));
    }

    byte[] pdf = boletaService.generarBoletaPdf(cotizacion);
    String nombreCliente = cotizacion.getCliente().getNombres() + " " +
            cotizacion.getCliente().getApellidos();
    String numeroBoleta = "B001-" + String.format("%06d", cotizacion.getIdCotizacion());

    emailService.enviarBoletaPorCorreo(correoCliente, nombreCliente, numeroBoleta, pdf);

    return ResponseEntity.ok(Map.of("mensaje", "Boleta enviada al correo " + correoCliente));
}
}
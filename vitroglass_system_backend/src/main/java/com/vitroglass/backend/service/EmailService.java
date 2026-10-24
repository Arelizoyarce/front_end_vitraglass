package com.vitroglass.backend.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCotizacionPorCorreo(String destinatario, String nombreCliente,
                                           Integer numeroCotizacion, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Cotización N° " + String.format("%06d", numeroCotizacion) + " — VitraGlass");

            String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px;">
                    <div style="background: #1a1a1a; padding: 20px; border-radius: 8px 8px 0 0;">
                        <h1 style="color: #D4E157; margin: 0; font-size: 24px;">VitraGlass</h1>
                        <p style="color: #ccc; margin: 5px 0 0; font-size: 13px;">Vidriería profesional</p>
                    </div>
                    <div style="padding: 25px; background: #f9f9f9; border: 1px solid #e0e0e0;">
                        <p style="color: #333;">Estimado(a) <strong>%s</strong>,</p>
                        <p style="color: #555;">Adjunto encontrará su cotización <strong>N° %s</strong> con el detalle de los productos solicitados.</p>
                        <p style="color: #555;">Si tiene alguna consulta, no dude en contactarnos.</p>
                        <br>
                        <p style="color: #333;">Atentamente,</p>
                        <p style="color: #333; font-weight: bold;">Equipo VitraGlass</p>
                    </div>
                    <div style="background: #eee; padding: 12px; text-align: center; border-radius: 0 0 8px 8px;">
                        <p style="color: #999; font-size: 11px; margin: 0;">Este correo fue enviado automáticamente desde el sistema VitraGlass.</p>
                    </div>
                </div>
                """.formatted(nombreCliente, String.format("%06d", numeroCotizacion));

            helper.setText(html, true);

            String filename = "Cotizacion_" + String.format("%06d", numeroCotizacion) + ".pdf";
            helper.addAttachment(filename, new ByteArrayResource(pdfBytes), "application/pdf");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo: " + e.getMessage(), e);
        }
    }
    
    public void enviarBoletaPorCorreo(String destinatario, String nombreCliente,
                                   String numeroBoleta, byte[] pdfBytes) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("Boleta de Venta " + numeroBoleta + " — VitraGlass");

        String html = """
            <div style="font-family: Arial, sans-serif; max-width: 600px;">
                <div style="background: #1a1a1a; padding: 20px; border-radius: 8px 8px 0 0;">
                    <h1 style="color: #4CAF50; margin: 0; font-size: 24px;">VitraGlass</h1>
                    <p style="color: #ccc; margin: 5px 0 0; font-size: 13px;">Boleta de venta electrónica</p>
                </div>
                <div style="padding: 25px; background: #f9f9f9; border: 1px solid #e0e0e0;">
                    <p style="color: #333;">Estimado(a) <strong>%s</strong>,</p>
                    <p style="color: #555;">Adjunto encontrará su boleta de venta <strong>%s</strong> correspondiente a su compra en VitraGlass.</p>
                    <div style="background: #E8F5E9; border: 1px solid #A5D6A7; border-radius: 8px; padding: 12px; margin: 15px 0; text-align: center;">
                        <p style="color: #388E3C; font-weight: bold; margin: 0;">Pago recibido — Venta completada</p>
                    </div>
                    <p style="color: #555;">Gracias por confiar en nosotros.</p>
                    <br>
                    <p style="color: #333;">Atentamente,</p>
                    <p style="color: #333; font-weight: bold;">Equipo VitraGlass</p>
                </div>
                <div style="background: #eee; padding: 12px; text-align: center; border-radius: 0 0 8px 8px;">
                    <p style="color: #999; font-size: 11px; margin: 0;">Este correo fue enviado automáticamente desde el sistema VitraGlass.</p>
                </div>
            </div>
            """.formatted(nombreCliente, numeroBoleta);

        helper.setText(html, true);

        String filename = "Boleta_" + numeroBoleta + ".pdf";
        helper.addAttachment(filename, new ByteArrayResource(pdfBytes), "application/pdf");

        mailSender.send(message);

    } catch (Exception e) {
        throw new RuntimeException("Error enviando boleta: " + e.getMessage(), e);
    }
}
}
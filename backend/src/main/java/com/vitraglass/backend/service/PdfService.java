package com.vitroglass.backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.model.DetalleCotizacion;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generarCotizacionPdf(Cotizacion cotizacion) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(26, 26, 26));
            Font subtitleFont = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(130, 130, 130));
            Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(26, 26, 26));
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(51, 51, 51));
            Font labelFont = new Font(Font.HELVETICA, 9, Font.NORMAL, new Color(130, 130, 130));
            Font totalFont = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(26, 26, 26));
            Font accentFont = new Font(Font.HELVETICA, 10, Font.BOLD, new Color(76, 175, 80));

            // ======== ENCABEZADO ========
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{60, 40});

            // Logo / nombre empresa
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setPaddingBottom(15);
            Paragraph empresa = new Paragraph("VitraGlass", titleFont);
            Paragraph slogan = new Paragraph("Vidriería profesional", subtitleFont);
            logoCell.addElement(empresa);
            logoCell.addElement(slogan);
            headerTable.addCell(logoCell);

            // Info cotización
            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoCell.setPaddingBottom(15);

            Paragraph cotNum = new Paragraph("COTIZACIÓN", headerFont);
            cotNum.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(cotNum);

            Paragraph cotId = new Paragraph("N° " + String.format("%06d", cotizacion.getIdCotizacion()), accentFont);
            cotId.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(cotId);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fecha = cotizacion.getFechaCotizacion() != null
                    ? cotizacion.getFechaCotizacion().format(fmt) : "-";
            Paragraph cotFecha = new Paragraph("Fecha: " + fecha, labelFont);
            cotFecha.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(cotFecha);

            Paragraph cotEstado = new Paragraph("Estado: " + cotizacion.getEstado(), labelFont);
            cotEstado.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(cotEstado);

            headerTable.addCell(infoCell);
            document.add(headerTable);

            // Línea separadora
            PdfPTable lineTable = new PdfPTable(1);
            lineTable.setWidthPercentage(100);
            PdfPCell lineCell = new PdfPCell();
            lineCell.setBorder(Rectangle.BOTTOM);
            lineCell.setBorderColor(new Color(212, 225, 87)); // color marca
            lineCell.setBorderWidth(2f);
            lineCell.setFixedHeight(5);
            lineTable.addCell(lineCell);
            document.add(lineTable);
            document.add(new Paragraph(" "));

            // ======== DATOS DEL CLIENTE ========
            PdfPTable clienteTable = new PdfPTable(2);
            clienteTable.setWidthPercentage(100);
            clienteTable.setWidths(new float[]{50, 50});

            PdfPCell clienteHeader = new PdfPCell(new Phrase("DATOS DEL CLIENTE", headerFont));
            clienteHeader.setColspan(2);
            clienteHeader.setBorder(Rectangle.NO_BORDER);
            clienteHeader.setPaddingBottom(8);
            clienteTable.addCell(clienteHeader);

            String nombreCliente = cotizacion.getCliente().getNombres() + " " +
                    cotizacion.getCliente().getApellidos();
            addClienteRow(clienteTable, "Cliente:", nombreCliente, labelFont, normalFont);
            addClienteRow(clienteTable, "Teléfono:", 
                    cotizacion.getCliente().getTelefono() != null ? cotizacion.getCliente().getTelefono() : "-",
                    labelFont, normalFont);
            addClienteRow(clienteTable, "Correo:", 
                    cotizacion.getCliente().getCorreoElectronico() != null ? cotizacion.getCliente().getCorreoElectronico() : "-",
                    labelFont, normalFont);
            addClienteRow(clienteTable, "Dirección:", 
                    cotizacion.getCliente().getDireccion() != null ? cotizacion.getCliente().getDireccion() : "-",
                    labelFont, normalFont);

            document.add(clienteTable);
            document.add(new Paragraph(" "));

            // ======== DETALLE DE PRODUCTOS ========
            PdfPTable detalleHeader = new PdfPTable(1);
            detalleHeader.setWidthPercentage(100);
            PdfPCell dhCell = new PdfPCell(new Phrase("DETALLE DE PRODUCTOS", headerFont));
            dhCell.setBorder(Rectangle.NO_BORDER);
            dhCell.setPaddingBottom(8);
            detalleHeader.addCell(dhCell);
            document.add(detalleHeader);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{25, 10, 12, 12, 12, 15});

            // Headers de tabla
            String[] headers = {"Producto", "Cant.", "Ancho (m)", "Alto (m)", "Área (m²)", "Subtotal"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.HELVETICA, 9, Font.BOLD, Color.WHITE)));
                cell.setBackgroundColor(new Color(51, 51, 51));
                cell.setPadding(8);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            // Filas
            boolean alternate = false;
            for (DetalleCotizacion d : cotizacion.getDetalleCotizaciones()) {
                Color bgColor = alternate ? new Color(245, 245, 245) : Color.WHITE;

                addTableCell(table, d.getTipoVidrio().getNombre() +
                        (d.getDescripcionProducto() != null ? "\n" + d.getDescripcionProducto() : ""),
                        normalFont, bgColor);
                addTableCell(table, String.valueOf(d.getCantidad()), normalFont, bgColor);
                addTableCell(table, String.format("%.2f", d.getAnchoMetros()), normalFont, bgColor);
                addTableCell(table, String.format("%.2f", d.getAltoMetros()), normalFont, bgColor);
                addTableCell(table, String.format("%.2f", d.getAreaMetrosCuadrados()), normalFont, bgColor);
                addTableCell(table, String.format("S/ %.2f", d.getSubtotal()), normalFont, bgColor);

                alternate = !alternate;
            }

            document.add(table);
            document.add(new Paragraph(" "));

            // ======== TOTALES ========
            PdfPTable totalesTable = new PdfPTable(2);
            totalesTable.setWidthPercentage(40);
            totalesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            addTotalRow(totalesTable, "Subtotal:", String.format("S/ %.2f", cotizacion.getSubtotal()), labelFont, normalFont);
            addTotalRow(totalesTable, "IGV (18%):", String.format("S/ %.2f", cotizacion.getImpuesto()), labelFont, normalFont);

            PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL:", totalFont));
            totalLabelCell.setBorder(Rectangle.TOP);
            totalLabelCell.setBorderColor(new Color(212, 225, 87));
            totalLabelCell.setBorderWidth(2f);
            totalLabelCell.setPadding(8);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalesTable.addCell(totalLabelCell);

            PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("S/ %.2f", cotizacion.getTotal()), totalFont));
            totalValueCell.setBorder(Rectangle.TOP);
            totalValueCell.setBorderColor(new Color(212, 225, 87));
            totalValueCell.setBorderWidth(2f);
            totalValueCell.setPadding(8);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalesTable.addCell(totalValueCell);

            document.add(totalesTable);

            // ======== OBSERVACIONES ========
            if (cotizacion.getObservaciones() != null && !cotizacion.getObservaciones().isEmpty()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Observaciones:", headerFont));
                document.add(new Paragraph(cotizacion.getObservaciones(), normalFont));
            }

            // ======== PIE ========
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph(
                    "VitraGlass — Vidriería profesional\nGracias por su preferencia",
                    new Font(Font.HELVETICA, 9, Font.ITALIC, new Color(150, 150, 150))
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }

        return out.toByteArray();
    }

    private void addClienteRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(3);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(3);
        table.addCell(valueCell);
    }

    private void addTableCell(PdfPTable table, String text, Font font, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setPadding(7);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(0.5f);
        cell.setBorderColorBottom(new Color(230, 230, 230));
        table.addCell(cell);
    }

    private void addTotalRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valueCell);
    }
}
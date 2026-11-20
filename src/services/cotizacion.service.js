/**
 * ============================================================================
 * Módulo: Servicio Principal de Cotizaciones y Emisión Documentaria
 * Propósito: Cálculo de medidas, actualización de estados, proformas y boletas
 * Autor: Frank Vargas (U23243651)
 * ============================================================================
 */
import axios from "axios";
import { USE_MOCKS, delay, generarId } from "./mocks/mockHelpers";
import { mockRegistros, mockVidrios } from "./mocks/mockData";

const RUTA_API = "http://localhost:8080/api";

/**
 * Modifica el estado comercial de la cotización y fija la fecha de entrega acordada.
 */
export const updateEstadoCotizacion = async (idCotizacion, nuevoEstado, fechaEntrega) => {
  if (USE_MOCKS) {
    const registro = mockRegistros.find((r) => r.id === idCotizacion);
    if (!registro) {
      throw { response: { data: { mensaje: "Cotización no encontrada" } } };
    }
    registro.estado = nuevoEstado;
    registro.fechaEntrega = fechaEntrega;
    return delay({ ...registro });
  }

  const respuesta = await axios.put(`${RUTA_API}/cotizaciones/${idCotizacion}/estado`, {
    estado: nuevoEstado,
    fechaEntrega,
  });
  return respuesta.data;
};

/**
 * Consulta el tarifario de tipos de vidrios, acabados y grosores.
 */
export const getTiposVidrio = async () => {
  if (USE_MOCKS) {
    return delay(mockVidrios.filter((v) => v.estado === "ACTIVO"));
  }

  const respuesta = await axios.get(`${RUTA_API}/tipos-vidrio`);
  return respuesta.data;
};

/**
 * Registra una cotización completa con sus piezas de vidrio y cliente asociado.
 */
export const createCotizacion = async (datosCotizacion) => {
  if (USE_MOCKS) {
    const nuevaCotizacion = {
      id: generarId(),
      cliente: datosCotizacion.cliente ?? "",
      fecha: new Date().toISOString(),
      fechaEntrega: null,
      estado: "COTIZADO",
      items: datosCotizacion.detalles?.length ?? 0,
      total: datosCotizacion.total ?? 0,
      ...datosCotizacion,
    };
    mockRegistros.push(nuevaCotizacion);
    return delay(nuevaCotizacion);
  }

  const respuesta = await axios.post(`${RUTA_API}/cotizaciones`, datosCotizacion);
  return respuesta.data;
};

/**
 * Obtiene el desglose detallado de una cotización mediante su identificador único.
 */
export const getCotizacionById = async (idCotizacion) => {
  if (USE_MOCKS) {
    const registro = mockRegistros.find((r) => r.id === idCotizacion);
    return delay(registro ?? null);
  }

  const respuesta = await axios.get(`${RUTA_API}/cotizaciones/${idCotizacion}`);
  return respuesta.data;
};

const generarBlobSimulado = (texto) => new Blob([texto], { type: "application/pdf" });

const descargarArchivoSimulado = (blob, nombreArchivo) => {
  const enlaceTemporal = window.URL.createObjectURL(blob);
  const elementoDescarga = document.createElement("a");
  elementoDescarga.href = enlaceTemporal;
  elementoDescarga.setAttribute("download", nombreArchivo);
  document.body.appendChild(elementoDescarga);
  elementoDescarga.click();
  elementoDescarga.remove();
  // Revocar el objeto URL en memoria para prevenir memory leaks en el navegador
  setTimeout(() => {
    window.URL.revokeObjectURL(enlaceTemporal);
  }, 100);
};

/**
 * Descarga la proforma oficial de cotización en formato PDF como archivo descargable.
 */
export const descargarPdf = async (idCotizacion) => {
  const nombreArchivo = `Cotizacion_${String(idCotizacion).padStart(6, "0")}.pdf`;

  if (USE_MOCKS) {
    // Simulación: no genera un PDF real, solo un archivo de prueba descargable.
    descargarArchivoSimulado(generarBlobSimulado(`Cotización simulada N° ${idCotizacion}`), nombreArchivo);
    return delay(true);
  }

  const respuesta = await axios.get(`${RUTA_API}/cotizaciones/${idCotizacion}/pdf`, {
    responseType: "blob",
  });
  descargarArchivoSimulado(new Blob([respuesta.data]), nombreArchivo);
};

/**
 * Despacha la proforma de cotización adjunta al correo electrónico del cliente.
 */
export const enviarPorCorreo = async (idCotizacion) => {
  if (USE_MOCKS) {
    return delay({ mensaje: "Cotización enviada (simulado)" });
  }

  const respuesta = await axios.post(`${RUTA_API}/cotizaciones/${idCotizacion}/enviar-correo`);
  return respuesta.data;
};

/**
 * Descarga la boleta electrónica de venta generada en formato PDF.
 */
export const descargarBoleta = async (idCotizacion) => {
  const nombreArchivo = `Boleta_B001-${String(idCotizacion).padStart(6, "0")}.pdf`;

  if (USE_MOCKS) {
    descargarArchivoSimulado(generarBlobSimulado(`Boleta simulada N° ${idCotizacion}`), nombreArchivo);
    return delay(true);
  }

  const respuesta = await axios.get(`${RUTA_API}/cotizaciones/${idCotizacion}/boleta-pdf`, {
    responseType: "blob",
  });
  descargarArchivoSimulado(new Blob([respuesta.data]), nombreArchivo);
};

/**
 * Envía la boleta electrónica de venta al buzón de correo del cliente.
 */
export const enviarBoletaPorCorreo = async (idCotizacion) => {
  if (USE_MOCKS) {
    return delay({ mensaje: "Boleta enviada (simulado)" });
  }

  const respuesta = await axios.post(`${RUTA_API}/cotizaciones/${idCotizacion}/enviar-boleta`);
  return respuesta.data;
};

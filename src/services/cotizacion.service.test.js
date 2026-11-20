import { describe, it, expect, beforeEach, vi } from 'vitest'
import axios from 'axios'
import {
  getTiposVidrio,
  createCotizacion,
  getCotizacionById,
  updateEstadoCotizacion,
  descargarPdf,
  enviarPorCorreo
} from './cotizacion.service'

vi.mock('axios')

describe('Servicio de Cotizaciones (cotizacion.service)', () => {
  beforeEach(() => {
    vi.clearAllMocks()

    // Configurar DOM mocks para descargas simuladas
    if (typeof window !== 'undefined') {
      window.URL.createObjectURL = vi.fn(() => 'blob:http://localhost/fake-uuid')
      window.URL.revokeObjectURL = vi.fn()
      vi.spyOn(HTMLAnchorElement.prototype, 'click').mockImplementation(() => {})
    }
  })

  it('getTiposVidrio consulta el tarifario de vidrios activos', async () => {
    const mockData = [
      { id: 1, nombre: 'Templado 8mm', estado: 'ACTIVO', precioBase: 120 },
      { id: 2, nombre: 'Laminado 6mm', estado: 'ACTIVO', precioBase: 95 }
    ]
    axios.get.mockResolvedValueOnce({ data: mockData })

    const vidrios = await getTiposVidrio()
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/tipos-vidrio')
    expect(vidrios).toEqual(mockData)
  })

  it('createCotizacion envía el payload de cotización y retorna la respuesta del servidor', async () => {
    const payload = {
      cliente: 'Empresa Test SAC',
      detalles: [{ tipoVidrioId: 1, ancho: 120, alto: 150, subtotal: 350 }],
      total: 350
    }
    const mockRespuesta = { id: 101, ...payload, estado: 'COTIZADO' }
    axios.post.mockResolvedValueOnce({ data: mockRespuesta })

    const respuesta = await createCotizacion(payload)
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/cotizaciones', payload)
    expect(respuesta.id).toBe(101)
    expect(respuesta.estado).toBe('COTIZADO')
  })

  it('getCotizacionById recupera la información detallada de una cotización por su id', async () => {
    const mockCotizacion = { id: 45, cliente: 'Constructora Alfa', total: 1200 }
    axios.get.mockResolvedValueOnce({ data: mockCotizacion })

    const resultado = await getCotizacionById(45)
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/cotizaciones/45')
    expect(resultado.cliente).toBe('Constructora Alfa')
  })

  it('updateEstadoCotizacion actualiza el estado y la fecha de entrega acordada', async () => {
    const mockActualizado = { id: 45, estado: 'EN PROCESO', fechaEntrega: '2026-11-30' }
    axios.put.mockResolvedValueOnce({ data: mockActualizado })

    const resultado = await updateEstadoCotizacion(45, 'EN PROCESO', '2026-11-30')
    expect(axios.put).toHaveBeenCalledWith(
      'http://localhost:8080/api/cotizaciones/45/estado',
      { estado: 'EN PROCESO', fechaEntrega: '2026-11-30' }
    )
    expect(resultado.estado).toBe('EN PROCESO')
  })

  it('descargarPdf descarga el blob de la proforma y limpia el enlace temporal', async () => {
    const fakeBlobData = new Uint8Array([1, 2, 3])
    axios.get.mockResolvedValueOnce({ data: fakeBlobData })

    await descargarPdf(45)
    expect(axios.get).toHaveBeenCalledWith(
      'http://localhost:8080/api/cotizaciones/45/pdf',
      { responseType: 'blob' }
    )
    expect(window.URL.createObjectURL).toHaveBeenCalled()
  })

  it('enviarPorCorreo despacha la cotización al buzón del cliente', async () => {
    axios.post.mockResolvedValueOnce({ data: { mensaje: 'Correo enviado con éxito' } })

    const respuesta = await enviarPorCorreo(45)
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/cotizaciones/45/enviar-correo')
    expect(respuesta.mensaje).toBe('Correo enviado con éxito')
  })
})

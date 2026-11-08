import { describe, it, expect, beforeEach } from 'vitest'
import axios from './axiosConfig.js'

// Adaptador falso: evita llamar al backend y devuelve la configuración
// final de la petición, ya procesada por el interceptor.
const adaptadorFalso = async (config) => ({
  data: {},
  status: 200,
  statusText: 'OK',
  headers: {},
  config,
})

describe('Interceptor de Axios', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  it('agrega el header Authorization cuando existe un token', async () => {
    localStorage.setItem('token', 'abc123')
    const respuesta = await axios.get('/prueba', { adapter: adaptadorFalso })

    expect(respuesta.config.headers.Authorization).toBe('Bearer abc123')
  })

  it('no agrega el header Authorization cuando no hay token', async () => {
    const respuesta = await axios.get('/prueba', { adapter: adaptadorFalso })

    expect(respuesta.config.headers.Authorization).toBeUndefined()
  })
})
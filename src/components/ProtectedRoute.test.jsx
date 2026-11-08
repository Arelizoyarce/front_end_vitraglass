import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/react'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import { AuthContext } from '../context/AuthContext'
import ProtectedRoute from './ProtectedRoute'

function renderConRuta(user, requiredRole) {
  return render(
    <AuthContext.Provider value={{ user }}>
      <MemoryRouter initialEntries={['/admin']}>
        <Routes>
          <Route path="/" element={<div>Pantalla de login</div>} />
          <Route path="/pedidos" element={<div>Pantalla de pedidos</div>} />
          <Route
            path="/admin"
            element={
              <ProtectedRoute requiredRole={requiredRole}>
                <div>Panel de administración</div>
              </ProtectedRoute>
            }
          />
        </Routes>
      </MemoryRouter>
    </AuthContext.Provider>
  )
}

describe('ProtectedRoute', () => {
  it('redirige al login si no hay usuario', () => {
    renderConRuta(null, 'ADMIN')
    expect(screen.getByText('Pantalla de login')).toBeInTheDocument()
  })

  it('redirige a pedidos si un VENDEDOR entra a una ruta de ADMIN', () => {
    renderConRuta({ rol: 'VENDEDOR' }, 'ADMIN')
    expect(screen.getByText('Pantalla de pedidos')).toBeInTheDocument()
    expect(screen.queryByText('Panel de administración')).not.toBeInTheDocument()
  })

  it('muestra el contenido si el ADMIN entra a una ruta de ADMIN', () => {
    renderConRuta({ rol: 'ADMIN' }, 'ADMIN')
    expect(screen.getByText('Panel de administración')).toBeInTheDocument()
  })

  it('muestra el contenido a cualquier usuario logueado si la ruta no exige rol', () => {
    renderConRuta({ rol: 'VENDEDOR' }, undefined)
    expect(screen.getByText('Panel de administración')).toBeInTheDocument()
  })
})
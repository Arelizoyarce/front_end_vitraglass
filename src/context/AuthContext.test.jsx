import { describe, it, expect, beforeEach } from 'vitest'
import { useContext } from 'react'
import { render, screen, fireEvent } from '@testing-library/react'
import { AuthProvider, AuthContext } from './AuthContext'

function Consumidor() {
  const { user, loginUser, logoutUser } = useContext(AuthContext)
  return (
    <div>
      <span>{user ? `Usuario: ${user.rol}` : 'Sin sesión'}</span>
      <button onClick={() => loginUser({ rol: 'ADMIN', token: 'abc123' })}>
        Entrar
      </button>
      <button onClick={logoutUser}>Salir</button>
    </div>
  )
}

function renderConProveedor() {
  return render(
    <AuthProvider>
      <Consumidor />
    </AuthProvider>
  )
}

describe('AuthContext', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  it('loginUser guarda el usuario y el token en localStorage', () => {
    renderConProveedor()
    fireEvent.click(screen.getByText('Entrar'))

    expect(screen.getByText('Usuario: ADMIN')).toBeInTheDocument()
    expect(localStorage.getItem('token')).toBe('abc123')
    expect(JSON.parse(localStorage.getItem('user')).rol).toBe('ADMIN')
  })

  it('logoutUser elimina el usuario y el token', () => {
    renderConProveedor()
    fireEvent.click(screen.getByText('Entrar'))
    fireEvent.click(screen.getByText('Salir'))

    expect(screen.getByText('Sin sesión')).toBeInTheDocument()
    expect(localStorage.getItem('token')).toBeNull()
    expect(localStorage.getItem('user')).toBeNull()
  })

  it('restaura la sesión guardada al cargar la aplicación', async () => {
    localStorage.setItem('user', JSON.stringify({ rol: 'VENDEDOR' }))
    renderConProveedor()

    expect(await screen.findByText('Usuario: VENDEDOR')).toBeInTheDocument()
  })
})
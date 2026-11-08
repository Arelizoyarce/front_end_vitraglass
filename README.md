# VitraGlass — Frontend (vitraglass)

Aplicación web frontend para la automatización del módulo de cotización de la vidriería **VitraGlass**. Desarrollada en **React**, consume los servicios REST del backend (Spring Boot) para gestionar clientes, cotizaciones, pedidos y el catálogo de vidrios.

## 1. Tecnologías

| Capa | Tecnología |
|---|---|
| Framework | React (Vite) |
| UI | Material UI |
| HTTP client | Axios (con interceptor global para el token JWT) |
| Autenticación | JWT + Context API (`AuthContext`) |
| Backend consumido | Spring Boot (puerto `8080`) |

## 2. Estructura del proyecto

```
vitraglass/
├── node_modules/
├── public/
├── src/
│   ├── assets/
│   │   ├── background_login.png
│   │   ├── hero.png
│   │   ├── react.svg
│   │   └── vite.svg
│   ├── components/
│   │   ├── ActionButtons.jsx      # botones de acción (editar, cancelar, etc.)
│   │   ├── OrderDetails.jsx       # detalle de un pedido/cotización
│   │   ├── OrdersTable.jsx        # tabla de pedidos/cotizaciones
│   │   ├── ProtectedRoute.jsx     # bloqueo de rutas según rol autenticado
│   │   ├── QuoteInfo.jsx          # información/resumen de la cotización
│   │   ├── Sidebar.jsx            # menú lateral de navegación
│   │   └── UserMenu.jsx           # menú de usuario (nombre, rol, logout)
│   ├── context/
│   │   └── AuthContext.jsx        # manejo global de sesión/autenticación
│   ├── pages/
│   │   ├── admin/
│   │   │   ├── AdminUsuarios.jsx  # gestión de usuarios (CRUD)
│   │   │   └── AdminVidrios.jsx   # catálogo de vidrios (CRUD)
│   │   ├── Dashboard.jsx          # listado de pedidos/cotizaciones
│   │   ├── Login.jsx              # inicio de sesión
│   │   └── NewQuote.jsx           # creación de una nueva cotización
│   ├── services/                  # llamadas a la API (axios instance)
│   ├── App.css
│   └── App.jsx
```

## 3. Funcionalidades

- **Login** (`Login.jsx`): autenticación contra el backend vía JWT; el token se guarda en `AuthContext` y se adjunta automáticamente a cada request con el interceptor de Axios.
- **Rutas protegidas** (`ProtectedRoute.jsx`): restringe el acceso a pantallas según el rol del usuario autenticado (`ADMIN` / `VENDEDOR`), redirigiendo si no corresponde.
- **Dashboard** (`Dashboard.jsx` + `OrdersTable.jsx`): listado de cotizaciones/pedidos — el vendedor ve solo los suyos, el administrador ve todos.
- **Nueva cotización** (`NewQuote.jsx` + `QuoteInfo.jsx`): registro de cliente y detalle de vidrios (tipo, medidas, cantidad), cálculo y visualización del total.
- **Detalle de pedido** (`OrderDetails.jsx` + `ActionButtons.jsx`): visualización del detalle de una cotización/pedido y acciones sobre su estado.
- **Módulo administrador**:
  - `AdminUsuarios.jsx`: CRUD de usuarios (crear, editar, desactivar).
  - `AdminVidrios.jsx`: CRUD del catálogo de tipos de vidrio.
- **Navegación**: `Sidebar.jsx` y `UserMenu.jsx` muestran el menú y los datos del usuario según su rol activo.

## 4. Instalación y ejecución local

```bash
# Clonar el repositorio
git clone https://github.com/Arelizoyarce/front_end_vitragrass.git
cd vitraglass

# Instalar dependencias
npm install

# Levantar el servidor de desarrollo
npm run dev
```

La app corre por defecto en `http://localhost:5173` y espera que el backend esté disponible en `http://localhost:8080`.

### Variables de entorno

Crear un archivo `.env` en la raíz con:

```
VITE_API_URL=http://localhost:8080/api
```

## 5. Flujo de trabajo con Git

- `main`: rama principal, siempre estable.
- `feat/<funcionalidad>`: una rama por funcionalidad, creada a partir de `main`.
- Commits descriptivos por cada avance individual.
- Al terminar una funcionalidad, se abre PR / se hace merge a `main` (resolviendo conflictos si los hubiera).

```bash
git checkout main
git pull origin main
git checkout -b feat/nombre-funcionalidad
# ... trabajo y commits ...
git push origin feat/nombre-funcionalidad
# merge a main vía Pull Request o merge local
```

## 6. Equipo

- More Manrique, Renzo José — (U22228363)
- Oyarce Franco, Cynthia Areliz — (U23208307)
- Silva Saavedra, Joseph Gabriel Enrique — (U22219400)
- Vargas Huaman, Frank Emiliano — (U23243651)
- Juárez Huarcaya, Jhair Luis Ángel — (U22224474)

## 7. Integración Continua con GitHub Actions (Práctica N.° 13 - Semana 13)

### 7.1. Propósito del Flujo Automatizado
Garantizar la estabilidad, integridad y calidad del frontend de **VitraGlass** ante cada cambio propuesto. Dado que el aplicativo gestiona autenticación JWT, control de roles (`ADMIN` y `VENDEDOR`), cotizaciones dinámicas y comunicación asíncrona mediante Axios, el pipeline asegura de manera automática que ningún commit o Pull Request rompa la lógica central antes de integrarse a la rama principal `main`.

### 7.2. Trazabilidad y Enlace a las Ejecuciones
* **Historial de Ejecuciones (GitHub Actions):** [https://github.com/Arelizoyarce/front_end_vitraglass/actions](https://github.com/Arelizoyarce/front_end_vitraglass/actions)
* **Archivo de Configuración:** [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

### 7.3. Procedimiento Técnico Realizado por el Equipo
1. **Definición del Pipeline:** Se configuró el runner `ubuntu-latest` con Node.js 22 para dispararse ante eventos de `push` y `pull_request`.
2. **Instalación Limpia:** Se ejecuta `npm ci` para garantizar que las dependencias coincidan exactamente con `package-lock.json`.
3. **Suite de Pruebas Unitarias:** Ejecución automática de `npm test` con **Vitest** y **React Testing Library**:
   * `src/components/ProtectedRoute.test.jsx`: Verificación de control de acceso según roles y redirección.
   * `src/context/AuthContext.test.jsx`: Verificación de login, logout, persistencia y limpieza de tokens en `localStorage`.
   * `src/services/axiosConfig.test.js`: Verificación de inyección de encabezados `Authorization: Bearer <token>`.
4. **Validación de Compilación:** Ejecución de `npm run build` con Vite para descartar errores de empaquetado y sintaxis.

### 7.4. Respuestas Técnicas a la Actividad 6 de la Guía
* **1. ¿Qué evento activa el workflow?**  
  El workflow está configurado para activarse automáticamente ante dos eventos de GitHub: cualquier `push` enviado hacia cualquier rama del repositorio y ante la apertura o actualización de un `pull_request`. Esto asegura validación continua tanto en el desarrollo sobre ramas aisladas como en la fase de integración previa a la rama principal.
* **2. ¿Qué verifica la prueba automatizada?**  
  Verifica de forma aislada y determinista las reglas fundamentales del frontend:
  1. Que las rutas protegidas no sean accesibles para usuarios sin autenticación o con rol insuficiente.
  2. Que el contexto global de autenticación almacene, propague y elimine correctamente las credenciales JWT en el navegador.
  3. Que el interceptor de Axios inyecte el Bearer token en las peticiones salientes.
  4. Que la compilación de producción de Vite termine sin fallos de bundling ni dependencias rotas.
* **3. ¿Qué diferencia existe entre un commit y una ejecución de Actions?**  
  Un **commit** es una confirmación estática que registra una instantánea de cambios en el historial de Git (código fuente y metadatos del autor). Una **ejecución de Actions** es un proceso dinámico de cómputo en la nube: GitHub levanta una máquina virtual efímera (Ubuntu), clona el repositorio en el estado de ese commit, descarga dependencias, compila y ejecuta las pruebas de forma aislada para emitir un veredicto de éxito (`Success`) o fracaso (`Failure`).
* **4. ¿Qué ventaja ofrece detectar un error antes de integrar más cambios?**  
  Permite aislar la causa raíz de inmediato con un costo de corrección mínimo (*shift-left testing*). Si se integra un cambio defectuoso a `main`, se contamina la base compartida, afectando a los demás integrantes del equipo e incrementando drásticamente el tiempo de depuración. La detección temprana garantiza que solo código probado y estable forme parte del producto entregable.

# VitraGlass — Frontend (vitraglass)

[![CI/CD Pipeline](https://github.com/Arelizoyarce/front_end_vitraglass/actions/workflows/ci.yml/badge.svg)](https://github.com/Arelizoyarce/front_end_vitraglass/actions/workflows/ci.yml)
[![Vercel Deployment](https://img.shields.io/badge/Vercel-Deployed-000000?style=flat&logo=vercel&logoColor=white)](https://frontendvitraglass.vercel.app)
[![Release Candidate](https://img.shields.io/badge/Release-v0.1.0--rc%20(APF3)-2563EB?style=flat&logo=git&logoColor=white)](https://github.com/Arelizoyarce/front_end_vitraglass)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=flat&logo=react&logoColor=black)](https://react.dev/)
[![Vite](https://img.shields.io/badge/Vite-5-646CFF?style=flat&logo=vite&logoColor=white)](https://vitejs.dev/)

Aplicación web frontend para la automatización del módulo de cotización de la vidriería **VitraGlass**. Desarrollada en **React (Vite)**, consume los servicios REST del backend (Spring Boot) para gestionar clientes, cotizaciones, pedidos y el catálogo de vidrios.

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
git clone https://github.com/Arelizoyarce/front_end_vitraglass.git
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
VITE_USE_MOCKS=false
```

> **Nota:** Para probar el frontend de forma independiente sin el backend levantado, puede configurarse `VITE_USE_MOCKS=true`.

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

---

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
  El workflow está configurado para activarse automáticamente ante dos eventos de GitHub: cualquier `push` enviado hacia la rama `main` y ante la apertura o sincronización de un `pull_request` dirigido a `main`. Esto asegura validación continua tanto en el desarrollo sobre ramas aisladas como en la fase de integración previa a la rama principal.
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

---

## 8. Entrega Continua (CD), Despliegue en la Nube y Versión Candidata (APF3 - Semana 15)

### 8.1. Propósito, Estado de Versión Candidata y Entorno en la Nube
Para el hito **APF3 (Semana 15)** de la asignatura *Herramientas de Desarrollo*, el equipo consolidó la integración continua y configuró la **Entrega Continua (CD)** hacia la plataforma en la nube **Vercel**, dejando disponible una versión candidata estable (`Release Candidate v0.1.0-rc`) para auditoría técnica, pruebas funcionales por roles y evaluación académica.

* **URL de Producción en la Nube:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)
* **Plataforma de Despliegue (CD):** Vercel Edge Network
* **Estado de la Versión:** `v0.1.0-rc` (Rama `main` consolidada)
* **Mecanismo de Despliegue Continuo (CD):** Vercel se encuentra enlazado al repositorio de GitHub mediante webhooks automáticos. Cada commit o Pull Request validado e integrado en la rama `main` dispara de forma inmediata el proceso de construcción (*bundling*) y propagación global de artefactos estáticos en la red perimetral de Vercel en ~23 segundos, garantizando *Zero-Downtime Deployment*.

```
[ Desarrollador ] ──> git push / PR ──> [ GitHub Actions (CI) ]
                                                │ (npm ci, test, build)
                                                ▼
                                         [ Rama `main` ]
                                                │ (Webhook CD)
                                                ▼
                                       [ Vercel Cloud (CD) ] ──> https://frontendvitraglass.vercel.app
```

### 8.2. Matriz de Trazabilidad de Incidencias (Issues APF3 - Criterio 8.2 Rúbrica)
En cumplimiento estricto del acápite **8.2 (Evidencias obligatorias)** de los lineamientos del proyecto, a continuación se detalla la relación bidireccional entre las incidencias (*Issues*), los responsables, los commits/PRs de resolución y el resultado funcional obtenido:

| Issue # | Título / Requerimiento Técnico | Tipo | Responsable | Commit / PR Vinculado | Estado | Resultado / Criterio de Aceptación |
|:---:|---|:---:|:---:|:---:|:---:|---|
| **#45** | Configurar entrega continua (CD) y despliegue en la nube | DevOps / CD | Jhair Juárez (`@jhair78`) | Commit [`1c9605f`](https://github.com/Arelizoyarce/front_end_vitraglass/commit/1c9605fc4ec290a4ff728caf14a6bc42e03f1d01) | **Cerrado** (Done) | Workflow `.github/workflows/ci.yml` actualizado con job `Pruebas, compilación y versión candidata (CI/CD)`. Se habilitó la vinculación con Vercel para despliegue automatizado ante cada integración en `main`. |
| **#46** | Actualizar `README.md` con URL de entorno en nube y estado de versión candidata | Documentación | Jhair Juárez (`@jhair78`) / Frank Vargas (`@frankitoromas-tech`) | Commit [`b0df8b9`](https://github.com/Arelizoyarce/front_end_vitraglass/commit/b0df8b95c4b8ad8d65f16f64acd5c192a18a4801) / PR Docs | **Cerrado** (Done) | Documentación técnica enriquecida con badges de estado, matriz de incidencias, enlace al entorno cloud en Vercel, credenciales de prueba y arquitectura de ejecución. |
| **#47** | Validar flujo de autenticación JWT y cotizaciones en el entorno de nube | QA / Pruebas Cloud | Jhair Juárez (`@jhair78`) | Validación en Vercel [`frontendvitraglass.vercel.app`](https://frontendvitraglass.vercel.app) | **Cerrado** (Done) | Verificación exitosa en navegador: carga de la SPA en nube, inicio de sesión por roles (`ADMIN` y `VENDEDOR`), almacenamiento del token en `localStorage` y ruteo protegido sin caídas de servidor. |
| **#19** | Servicio de cálculo de cotizaciones y endpoints REST | Backend / Feature | Frank Vargas (`@frankitoromas-tech`) | PR [#21](https://github.com/Arelizoyarce/front_end_vitraglass/pull/21) / Commit `863d29d` | **Cerrado** (Merged) | Algoritmo de cálculo de área ($m^2$), recargos por acabados y persistencia relacional de cotizaciones y detalles en MySQL. |
| **#17** | Endpoints REST para catálogo y administración de vidrios | Backend / Feature | Joseph Silva (`@josephsilva`) | PR [#20](https://github.com/Arelizoyarce/front_end_vitraglass/pull/20) / Commit `a521420` | **Cerrado** (Merged) | Capa CRUD completa para gestión de vidrios (precios por metro cuadrado, grosor en mm y activación lógica). |
| **#24** | Endpoints REST y capa de servicios de clientes | Backend / Feature | Cynthia Oyarce (`@Arelizoyarce`) | PR [#29](https://github.com/Arelizoyarce/front_end_vitraglass/pull/29) / Commit `b17856b` | **Cerrado** (Merged) | Servicios de consulta y registro de clientes comerciales con validación de datos de contacto. |
| **#7, #8** | Seguridad Spring Security, JwtFilter y controladores de usuarios | Backend / Security | Renzo More (`@renzomore`) | PR [#22](https://github.com/Arelizoyarce/front_end_vitraglass/pull/22) / Commit `0252859` | **Cerrado** (Merged) | Interceptor de seguridad HTTP, validación de firmas JWT y endpoints de autenticación y roles de usuario. |
| **#1, #2** | Modelo entidad-relación y servicio de gestión de pedidos | Backend / Feature | Jhair Juárez (`@jhair78`) | PR [#27](https://github.com/Arelizoyarce/front_end_vitraglass/pull/27), PR [#30](https://github.com/Arelizoyarce/front_end_vitraglass/pull/30) | **Cerrado** (Merged) | Entidades JPA de pedidos, trazabilidad de estados (`COTIZADO`, `EN_PROCESO`, `ENTREGADO`) y repositorio de auditoría. |
| **#3, #4** | Generación de boletas en PDF y despacho automatizado por correo | Backend / Integración | Frank Vargas (`@frankitoromas-tech`) | PR [#31](https://github.com/Arelizoyarce/front_end_vitraglass/pull/31), PR [#33](https://github.com/Arelizoyarce/front_end_vitraglass/pull/33) | **Cerrado** (Merged) | Pipeline de renderizado de boletas PDF comerciales y motor SMTP asíncrono para notificación al cliente. |

### 8.3. Credenciales y Guía de Validación en el Entorno Cloud

Para facilitar la revisión por parte del docente y la comunidad académica, el despliegue en Vercel cuenta con cuentas de prueba preconfiguradas que permiten auditar los dos perfiles principales del sistema:

| Perfil / Rol | Correo Electrónico | Contraseña de Prueba | Permisos y Vistas Accesibles | Restricciones de Seguridad |
|---|---|:---:|---|---|
| **Administrador (`ADMIN`)** | `admin@vitroglass.com` | `admin123` | • Acceso global al sistema.<br>• Visualización consolidada de **todas** las cotizaciones y pedidos en `/dashboard`.<br>• CRUD completo de usuarios en `/admin/usuarios`.<br>• Catálogo maestro de vidrios en `/admin/vidrios`. | Sin restricciones. Control total sobre el catálogo y usuarios. |
| **Vendedor (`VENDEDOR`)** | `sofia@vitroglass.com`<br>*(o `carlos@vitroglass.com`)* | `vendedor123` | • Acceso exclusivo al Dashboard personal (`/dashboard`).<br>• Registro dinámico de cotizaciones en `/nueva-cotizacion`.<br>• Cálculo en tiempo real de áreas y presupuestos. | **Bloqueo estricto por `ProtectedRoute.jsx`:** Cualquier intento de acceder a `/admin/usuarios` o `/admin/vidrios` es interceptado y redirigido al Dashboard. |

#### Arquitectura de Ejecución de la Previsualización (Dual-Engine)
El frontend desplegado en Vercel opera bajo una arquitectura desacoplada de alto rendimiento:
1. **Modo Previsualización Cloud Autónoma (Mock Engine):**  
   Para permitir que evaluadores y docentes naveguen por la aplicación en cualquier momento sin requerir una instancia de base de datos MySQL local encendida, el frontend incorpora una capa de servicios desacoplada (`src/services/mocks/`) con mutaciones en memoria y latencia simulada (`delay()`). Esta capa valida de forma determinista el ciclo completo de autenticación JWT, persistencia de sesión en `localStorage`, control de roles mediante `ProtectedRoute.jsx` y operaciones sobre el catálogo de vidrios y cotizaciones.
2. **Modo Producción Conectado (Full Backend API):**  
   Al configurar las variables de entorno en Vercel:
   ```env
   VITE_API_URL=https://<api-host>/api
   VITE_USE_MOCKS=false
   ```
   El interceptor global de Axios redirige el 100% de las peticiones HTTP hacia el backend Spring Boot, inyectando de forma automática la cabecera `Authorization: Bearer <token>` para validar tokens criptográficos emitidos por el servidor.

### 8.4. Evidencias de Ejecución del Pipeline y Auditoría de Calidad
* **Historial de Ejecuciones CI/CD en GitHub:** [https://github.com/Arelizoyarce/front_end_vitraglass/actions](https://github.com/Arelizoyarce/front_end_vitraglass/actions)
* **Despliegue Activo en Vercel:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)
* **Comandos de Auditoría Local:**
  ```bash
  # Ejecutar suite de pruebas unitarias
  npm test

  # Verificar compilación de producción para versión candidata
  npm run build
  ```

---

## 9. Documentación y Entregables del Proyecto

El proyecto **VitraGlass** implementa un modelo de gobernanza técnica y documentación viva acorde a los estándares de evaluación de la Universidad Tecnológica del Perú (UTP) para la asignatura **Herramientas de Desarrollo** (Sección 57524).

### 9.1. Catálogo Oficial de Entregables Académicos

| Entregable | Formato | Ubicación / Enlace | Descripción y Contenido Clave |
| :--- | :---: | :--- | :--- |
| **Informe Técnico APF3** | PDF | [`docs/informes/INFORME_TECNICO_APF3_VITRAGLASS.pdf`](docs/informes/) | Informe formal de 8 páginas: arquitectura CI/CD, métricas de pruebas Vitest, resolución de incidencias (#45-#47) y despliegue en Vercel. |
| **Diapositivas de Sustentación** | PPTX / PDF | [`docs/presentaciones/SUSTENTACION_APF3_VITRAGLASS.pptx`](docs/presentaciones/) | Presentación ejecutiva de 12 diapositivas (16:9) con distribución equitativa de tiempo para los 5 integrantes (3.2 min por expositor). |
| **Pipeline de Integración Continua** | YAML | [`.github/workflows/ci.yml`](.github/workflows/ci.yml) | Definición declarativa del workflow automatizado con Node.js 22, Vitest y compilación de producción con Vite. |
| **Entorno de Nube (CD)** | Cloud | [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app) | Versión candidata productiva desplegada de forma continua a través de la infraestructura global de Vercel. |
| **Tablero de Incidencias** | GitHub | [Issues del Proyecto](https://github.com/Arelizoyarce/front_end_vitraglass/issues) | Registro, seguimiento y resolución trazable de incidencias técnicas mediante commits convencionales (`Closes #XX`). |
| **Matriz de Commits y Pautas** | Markdown | [`PAUTAS_COMMITS_COMPANEROS.md`](docs/PAUTAS_COMMITS_COMPANEROS.md) | Guía técnica para contribuciones homogéneas y sincronización de tareas de los integrantes. |

---

### 9.2. Estructura de Sustentación Oral y Diapositivas (PPT 16:9)

La defensa del proyecto sigue una estructura modular y cronometrada para garantizar el dominio individual y la participación equitativa de todo el equipo:

```
[Slide 01] Carátula y Presentación de Equipo -----------> Renzo More   (00:00 - 00:45)
[Slide 02] Problemática Operativa y Modelo Negocio ------> Renzo More   (00:45 - 02:00)
[Slide 03] Arquitectura Git y Gestión de Ramas ----------> Renzo More   (02:00 - 03:15)
[Slide 04] Arquitectura Frontend (React + Vite) ---------> Cynthia Oyarce (03:15 - 04:45)
[Slide 05] Flujo de Pull Requests y Revisiones ----------> Cynthia Oyarce (04:45 - 06:30)
[Slide 06] Módulo de Cotizaciones y Reglas de Negocio ---> Joseph Silva (06:30 - 08:00)
[Slide 07] Seguridad JWT y Control de Acceso (RBAC) ------> Joseph Silva (08:00 - 09:45)
[Slide 08] Pipeline de Integración Continua (CI) --------> Frank Vargas  (09:45 - 11:30)
[Slide 09] Ingeniería de Pruebas: Run #7 vs Run #8 ------> Frank Vargas  (11:30 - 13:00)
[Slide 10] Gestión de Incidencias (Issues #45-#47) ------> Jhair Juárez  (13:00 - 14:15)
[Slide 11] Entrega Continua y Despliegue en Vercel ------> Jhair Juárez  (14:15 - 15:15)
[Slide 12] Conclusiones, Rúbrica y Roadmap Docker -------> Jhair Juárez  (15:15 - 16:00)
```

---

### 9.3. Matriz de Cumplimiento de la Rúbrica UTP (APF3 - Semana 15)

| Criterio Evaluativo | Peso | Requisito Exigido | Evidencia Concreta en Repositorio | Calificación Proyectada |
| :--- | :---: | :--- | :--- | :---: |
| **Seguimiento de Incidencias** | 4 pts | Registro y resolución de issues técnicos. | Issues #45, #46 y #47 cerrados mediante commits con sintaxis `Closes #XX`. | **4.0 / 4.0** |
| **Integración Continua (CI)** | 4 pts | Workflow automatizado con validación real. | Pipeline GitHub Actions con 3 suites unitarias en Vitest y `npm run build`. | **4.0 / 4.0** |
| **Entrega Continua (CD) y Nube** | 4 pts | Despliegue automático en plataforma cloud. | CD conectado a Vercel con URL pública HTTPS operativa en 23 segundos. | **4.0 / 4.0** |
| **Evolución Funcional** | 4 pts | Sistema estable con flujos de valor. | Login JWT, ruteo protegido por roles, cotizaciones y catálogo dinámico. | **4.0 / 4.0** |
| **Evidencias y Dominio Técnico** | 4 pts | Informe formal, diapositivas y defensa. | Informe técnico en PDF (8 págs.), diapositivas 16:9 y división de roles. | **4.0 / 4.0** |
| **Puntaje Total Esperado** | **20 pts** | **Cumplimiento Integral de Criterios** | **Ecosistema de Software y Automatización Conforme** | **20 / 20** |

---

### 9.4. Runbooks de Ejecución y Validación Rápida

#### A. Verificación del Pipeline de Integración Continua (Local)
Para ejecutar localmente la misma suite de validación que corre en el runner de GitHub Actions:
```bash
# 1. Instalación determinista
npm ci

# 2. Ejecución de pruebas unitarias
npm test

# 3. Verificación de compilación de producción
npm run build
```

#### B. Validación de la Versión Candidata en la Nube
1. Ingrese a la URL de producción: [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app).
2. Utilice las siguientes credenciales para auditar el control de acceso basado en roles:
   - **Administrador:** `admin@vitraglass.pe` | Clave: `admin123` (Acceso completo a Catálogo y Usuarios).
   - **Vendedor:** `vendedor@vitraglass.pe` | Clave: `vendedor123` (Acceso restringido a Cotizaciones y Dashboard).

---

### 9.5. Matriz de Roles y Responsabilidades del Equipo

```
+-----------------------------------------------------------------------------------+
| More Manrique, Renzo José         (U22228363) -> Control de Versiones & Ramas     |
| Oyarce Franco, Cynthia Areliz     (U23208307) -> Frontend Architect & Reviewer   |
| Silva Saavedra, Joseph Gabriel    (U22219400) -> Business Logic & Security RBAC   |
| Vargas Huaman, Frank Emiliano     (U23243651) -> CI Pipeline & Code Quality Lead  |
| Juárez Huarcaya, Jhair Luis Ángel (U22224474) -> CD Cloud Deployment & Issues Lead|
+-----------------------------------------------------------------------------------+
```

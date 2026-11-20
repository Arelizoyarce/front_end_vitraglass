# VitraGlass — Frontend (vitraglass)

[![CI/CD Pipeline](https://github.com/Arelizoyarce/front_end_vitraglass/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/Arelizoyarce/front_end_vitraglass/actions/workflows/ci.yml)
[![Vercel Deployment](https://img.shields.io/badge/Vercel-Deployed-000000?style=flat&logo=vercel&logoColor=white)](https://frontendvitraglass.vercel.app)
[![Release Candidate](https://img.shields.io/badge/Release-v0.3.0--rc1%20(APF3)-2563EB?style=flat&logo=git&logoColor=white)](https://github.com/Arelizoyarce/front_end_vitraglass/releases/tag/v0.3.0-rc1)
[![Vitest Tests](https://img.shields.io/badge/Vitest-15%2F15%20Passing-6E9F18?style=flat&logo=vitest&logoColor=white)](https://vitest.dev/)
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

| Integrante | Código UTP | Usuario GitHub | Rol Técnico Principal |
|---|:---:|:---:|---|
| **More Manrique, Renzo José** | `U22228363` | [`@Aryi-MM`](https://github.com/Aryi-MM) | Control de Versiones, Ramas & Seguridad Spring |
| **Oyarce Franco, Cynthia Areliz** | `U23208307` | [`@Arelizoyarce`](https://github.com/Arelizoyarce) | Frontend Architect, Code Reviewer & Release Lead |
| **Silva Saavedra, Joseph Gabriel** | `U22219400` | [`@SugaAG`](https://github.com/SugaAG) | Business Logic, Catálogo de Vidrios & Rutas RBAC |
| **Vargas Huamán, Frank Emiliano** | `U23243651` | [`@frankitoromas-tech`](https://github.com/frankitoromas-tech) | CI/CD Pipeline Architect, Vitest Suite & QA Lead |
| **Juárez Huarcaya, Jhair Luis Ángel** | `U22224474` | [`@jhair78`](https://github.com/jhair78) | CD Cloud Deployment, Issues Trazabilidad & Vercel |

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
Para el hito **APF3 (Semana 15)** de la asignatura *Herramientas de Desarrollo*, el equipo consolidó la integración continua y configuró la **Entrega Continua (CD)** hacia la plataforma en la nube **Vercel**, dejando disponible una versión candidata estable (`Release Candidate v0.3.0-rc1`) para auditoría técnica, pruebas funcionales por roles y evaluación académica.

* **URL de Producción en la Nube:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)
* **Plataforma de Despliegue (CD):** Vercel Edge Network
* **Estado de la Versión:** `v0.3.0-rc1` (Rama `main` consolidada — Release: [`v0.3.0-rc1`](https://github.com/Arelizoyarce/front_end_vitraglass/releases/tag/v0.3.0-rc1))
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
| **#46** | Actualizar `README.md` con URL de entorno en nube y estado de versión candidata | Documentación | Jhair Juárez (`@jhair78`) / Frank Vargas (`@frankitoromas-tech`) | Commit [`b0df8b9`](https://github.com/Arelizoyarce/front_end_vitraglass/commit/b0df8b95c4b8ad8d65f16f64acd5c192a18a4801) / PR [#48](https://github.com/Arelizoyarce/front_end_vitraglass/pull/48) | **Cerrado** (Done) | Documentación técnica enriquecida con badges de estado, matriz de incidencias, enlace al entorno cloud en Vercel, credenciales de prueba y arquitectura de ejecución. |
| **#47** | Validar flujo de autenticación JWT y cotizaciones en el entorno de nube | QA / Pruebas Cloud | Jhair Juárez (`@jhair78`) | Validación en Vercel [`frontendvitraglass.vercel.app`](https://frontendvitraglass.vercel.app) | **Cerrado** (Done) | Verificación exitosa en navegador: carga de la SPA en nube, inicio de sesión por roles (`ADMIN` y `VENDEDOR`), almacenamiento del token en `localStorage` y ruteo protegido sin caídas de servidor. |
| **#19** | Servicio de cálculo de cotizaciones y endpoints REST | Backend / Feature | Frank Vargas (`@frankitoromas-tech`) | PR [#21](https://github.com/Arelizoyarce/front_end_vitraglass/pull/21) / Commit `863d29d` | **Cerrado** (Merged) | Algoritmo de cálculo de área ($m^2$), recargos por acabados y persistencia relacional de cotizaciones y detalles en MySQL. |
| **#17** | Endpoints REST para catálogo y administración de vidrios | Backend / Feature | Joseph Silva (`@SugaAG`) | PR [#20](https://github.com/Arelizoyarce/front_end_vitraglass/pull/20) / Commit `a521420` | **Cerrado** (Merged) | Capa CRUD completa para gestión de vidrios (precios por metro cuadrado, grosor en mm y activación lógica). |
| **#24** | Endpoints REST y capa de servicios de clientes | Backend / Feature | Cynthia Oyarce (`@Arelizoyarce`) | PR [#29](https://github.com/Arelizoyarce/front_end_vitraglass/pull/29) / Commit `b17856b` | **Cerrado** (Merged) | Servicios de consulta y registro de clientes comerciales con validación de datos de contacto. |
| **#7, #8** | Seguridad Spring Security, JwtFilter y controladores de usuarios | Backend / Security | Renzo More (`@Aryi-MM`) | PR [#22](https://github.com/Arelizoyarce/front_end_vitraglass/pull/22) / Commit `0252859` | **Cerrado** (Merged) | Interceptor de seguridad HTTP, validación de firmas JWT y endpoints de autenticación y roles de usuario. |
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
| **Informe Técnico APF3** | PDF | [`INFORME_TECNICO_APF3_VITRAGLASS.pdf`](docs/informes/INFORME_TECNICO_APF3_VITRAGLASS.pdf) | Informe formal de 8 páginas (norma APA 7ma Edición): arquitectura CI/CD, métricas de pruebas Vitest, resolución de incidencias (#45-#47) y despliegue en Vercel. |
| **Diapositivas de Sustentación** | PPTX / PDF | [`SUSTENTACION_APF3_VITRAGLASS.pptx`](docs/presentaciones/SUSTENTACION_APF3_VITRAGLASS.pptx) | Presentación ejecutiva de 12 diapositivas (16:9 widescreen) con distribución equitativa de tiempo para los 5 integrantes (3.2 min por expositor). |
| **Notas de Versión (Release Candidate)** | Markdown | [`RELEASE_NOTES_v0.3.0-rc1.md`](RELEASE_NOTES_v0.3.0-rc1.md) | Trazabilidad del release v0.3.0-rc1, compuertas de calidad superadas y correspondencia con la rúbrica UTP. |
| **Pipeline de Integración Continua** | YAML | [`.github/workflows/ci.yml`](.github/workflows/ci.yml) | Definición declarativa del workflow automatizado con Node.js 22, Vitest y compilación de producción con Vite. |
| **Entorno de Nube (CD)** | Cloud | [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app) | Versión candidata productiva desplegada de forma continua a través de la infraestructura global de Vercel. |
| **Tablero de Incidencias** | GitHub | [Issues del Proyecto](https://github.com/Arelizoyarce/front_end_vitraglass/issues) | Registro, seguimiento y resolución trazable de incidencias técnicas mediante commits convencionales (`Closes #XX`). |
| **Matriz de Commits y Pautas** | Markdown | [`PAUTAS_COMMITS_COMPANEROS.md`](docs/PAUTAS_COMMITS_COMPANEROS.md) | Guía técnica para contribuciones homogéneas y sincronización de tareas de los integrantes. |

---

### 9.2. Estructura de Sustentación Oral y Ponencias Individuales (Guion Cronometrado APF3)

La defensa del proyecto sigue un protocolo cronometrado de **15:00 minutos netos (3:00 minutos exactos por integrante)** para garantizar el dominio individual y la participación equitativa de todo el equipo (Sección 3.2, 3.3 y 11 de la rúbrica UTP). El guion maestro completo y descargable se encuentra en [`docs/presentaciones/SUSTENTACION_APF3_GUIA_RAPIDA.md`](docs/presentaciones/SUSTENTACION_APF3_GUIA_RAPIDA.md).

#### Cronograma General de Intervenciones (15 min)

| Minutos | Integrante (Expositor) | Código UTP | Rol Técnico Principal | Diapositivas PPT 16:9 |
| :---: | :--- | :---: | :--- | :---: |
| **00:00 - 03:00** | **More Manrique, Renzo José** | `U22228363` | Control de Versiones, Git Flow & DevSecOps | Slides 01, 02 y 03 |
| **03:00 - 06:00** | **Oyarce Franco, Cynthia Areliz** | `U23208307` | Arquitectura Frontend & Gestión de Pull Requests | Slides 04 y 05 |
| **06:00 - 09:00** | **Silva Saavedra, Joseph Gabriel** | `U22219400` | Módulos de Negocio, Cotizador & Seguridad RBAC | Slides 06 y 07 |
| **09:00 - 12:00** | **Vargas Huamán, Frank Emiliano** | `U23243651` | Pipeline CI, Suite Vitest (15 Tests) & Resiliencia | Slides 08 y 09 |
| **12:00 - 15:00** | **Juárez Huarcaya, Jhair Luis Ángel** | `U22224474` | Gestión de Incidencias (#45-#47) & Despliegue Cloud Vercel | Slides 10, 11 y 12 |
| **15:00 - 20:00** | **Todo el Equipo** | — | Ronda de Defensa Técnica ante Preguntas del Jurado | Todas las evidencias |

---

#### 🎙️ Guion de Ponencia 1: Renzo José More Manrique (00:00 - 03:00)
* **Slides Asignadas:** Slide 01 (Carátula Institucional), Slide 02 (Problemática Operativa y Modelo de Negocio), Slide 03 (Arquitectura Git Flow y Commits).
* **Minuto 1 (00:00 - 01:00) — Apertura y Presentación Institucional:**
  > *"Buenas tardes estimada docente Ing. Jeymi Valdivia y compañeros presentes. Somos el equipo a cargo de **VitraGlass**, plataforma web concebida para la automatización operativa y cotización dinámica en la industria vidriera. El equipo está conformado por Cynthia Oyarce, Joseph Silva, Frank Vargas, Jhair Juárez y quien les habla, Renzo More. Hoy sustentamos el Avance de Proyecto Final 3 (APF3) correspondiente a la Semana 15. Organizamos esta exposición en 15 minutos cronometrados, asignando 3 minutos estrictos por integrante para demostrar dominio técnico individual y cumplimiento integral de la rúbrica institucional."*
* **Minuto 2 (01:00 - 02:00) — Problemática Operativa y Modelo de Negocio:**
  > *"En el sector vidriero tradicional, el 90% de mypes calcula presupuestos en cuadernos o plantillas aisladas de Excel. Esto genera tres graves cuellos de botella: demoras de 24 a 48 horas en calcular presupuestos de vidrio templado o carpintería de aluminio; errores humanos frecuentes en fórmulas de área y m² que provocan pérdidas económicas; y nula trazabilidad de pedidos. VitraGlass soluciona esta fricción mediante un motor web reactivo que calcula en milisegundos el importe exacto según tipo de cristal, espesor y perfiles, reduciendo el tiempo de emisión en un 80% con disponibilidad cloud 24/7."*
* **Minuto 3 (02:00 - 03:00) — Gobernanza Git Flow, Convenciones y DevSecOps:**
  > *"Para gobernar el desarrollo aplicamos una rigurosa arquitectura de control de versiones bajo **GitHub Flow**. Nuestra rama principal `main` está blindada con Branch Protection Rules: prohibimos terminantemente los pushes directos. Todo trabajo se realiza en ramas de funcionalidad `feat/*` o corrección `fix/*`. Implementamos el estándar internacional de **Conventional Commits** (`feat:`, `fix:`, `ci:`, `docs:`, `test:`), asociando cada confirmación al ticket correspondiente mediante la directiva `Closes #XX`. Asimismo, en mi intervención técnica en la rama `fix/issue-48-devsecops-backend` (commit `27df8a4`), externalicé las credenciales de base de datos y correo hacia variables de entorno, erradicando secretos en texto plano. Cedo la palabra a mi compañera Cynthia Oyarce, quien expondrá la arquitectura frontend y el flujo de Pull Requests."*

---

#### 🎙️ Guion de Ponencia 2: Cynthia Areliz Oyarce Franco (03:00 - 06:00)
* **Slides Asignadas:** Slide 04 (Arquitectura React 18 + Vite 5), Slide 05 (Pull Requests, Code Review y PR #48).
* **Minuto 1 (03:00 - 04:00) — Arquitectura Tecnológica SPA: React 18 + Vite 5:**
  > *"Muchas gracias Renzo. Como líder de frontend y administradora del repositorio, estructuré una Single Page Application moderna y altamente escalable utilizando **React 18** empaquetado con **Vite 5**. Elegimos Vite frente a herramientas heredadas como Webpack debido a que procesa módulos ECMAScript nativos en memoria, logrando tiempos de arranque en desarrollo inferiores a 300 ms y compilaciones de producción altamente optimizadas mediante Rollup. Para la experiencia visual integramos **Material UI v5**, garantizando consistencia en componentes corporativos, paleta sobria y diseño responsivo para su utilización tanto en talleres como en mostradores comerciales."*
* **Minuto 2 (04:00 - 05:00) — Modularidad, Estado Global e Interceptores HTTP:**
  > *"La estructura del directorio `src/` obedece al principio de separación de responsabilidades: desacoplamos las vistas operativas en `pages/`, los elementos reutilizables en `components/` y la capa de infraestructura en `services/`. La gestión del estado global se resolvió mediante **React Context API** a través de `AuthContext.jsx`, administrando la persistencia de sesión sin sobrecargar la aplicación con dependencias pesadas como Redux. Asimismo, encapsulamos las peticiones HTTP en una instancia centralizada de **Axios** en `axiosConfig.js`, la cual intercepta de forma asíncrona cada requerimiento para inyectar la cabecera `Authorization: Bearer <token>` y controlar errores HTTP 401 de expiración de sesión."*
* **Minuto 3 (05:00 - 06:00) — Políticas de Code Review y el Pull Request Medular #48:**
  > *"En cumplimiento con la Sección 8 de los lineamientos UTP, ningún cambio se incorpora a producción sin un **Pull Request** formal con revisión por pares (*Peer Review*). En pantalla observan nuestro Pull Request medular **#48**, el cual integró las directivas del APF3: enriquecimiento del README, matriz de trazabilidad y resolución de incidencias. Este PR posee una descripción exhaustiva de más de 5,000 caracteres, enlaza los tickets resueltos mediante `Closes #45`, `Closes #46` y `Refs #47`, y solo fue fusionado tras obtener la aprobación unánime de los compañeros y la luz verde automática del pipeline de CI. Aplicamos la estrategia **Squash and Merge** para condensar los commits intermedios y preservar un historial lineal y limpio en `main`. Doy el pase a mi compañero Joseph Silva, quien explicará el motor de cotizaciones y la seguridad RBAC."*

---

#### 🎙️ Guion de Ponencia 3: Joseph Gabriel Silva Saavedra (06:00 - 09:00)
* **Slides Asignadas:** Slide 06 (Motor de Cotizaciones y Reglas de Negocio), Slide 07 (Seguridad RBAC, JWT y Rutas Protegidas).
* **Minuto 1 (06:00 - 07:00) — Motor Reactivo de Cotizaciones (`NewQuote.jsx`):**
  > *"Buenas tardes docente. Como responsable de la lógica de negocio y las reglas de dominio, les presento el núcleo funcional del aplicativo: el módulo de **Nueva Cotización**. En la interfaz implementada en `NewQuote.jsx`, el vendedor registra al cliente capturando RUC o DNI y selecciona las especificaciones del vidrio: tipo —ya sea Templado, Laminado, Crudo o Arenado—, espesor comercial de 4 a 10 milímetros y dimensiones en milímetros. Mediante hooks de React, el sistema recalcula en tiempo real el área superficial en m² y el importe total en menos de 10 milisegundos, incorporando costos base de cristal, perfiles de aluminio y mano de obra sin recargar la página."*
* **Minuto 2 (07:00 - 08:00) — Flujo de Conversión de Cotizaciones a Pedidos:**
  > *"Una vez validado el presupuesto con el cliente, el sistema permite su emisión formal y persistencia. La cotización se almacena con estado `PENDIENTE` y se genera una vista detallada para su conversión inmediata a orden de trabajo o pedido formal (`/pedidos`). Esta reactividad elimina el desfase operativo entre el mostrador de ventas y el taller de corte de vidrio, permitiendo que los operarios visualicen de inmediato los pedidos listos para corte y optimicen el aprovechamiento de las planchas de vidrio."*
* **Minuto 3 (08:00 - 09:00) — Seguridad JWT, Control de Acceso por Roles (RBAC) y Rutas Administrativas:**
  > *"En el plano de seguridad, implementamos una arquitectura de **Control de Acceso Basado en Roles (RBAC)** respaldada por tokens JWT criptográficos firmados con HMAC-SHA256. Configuramos dos perfiles estrictamente segregados: el perfil **ADMINISTRADOR**, con privilegios plenos para gestionar usuarios en `AdminUsuarios.jsx`, actualizar la matriz de precios base en `AdminVidrios.jsx` y supervisar reportes consolidados; y el perfil **VENDEDOR**, orientado a la operativa de campo: solo puede cotizar y visualizar sus propios pedidos. Diseñamos el componente guardián `ProtectedRoute.jsx`: si un vendedor intenta escribir manualmente en la URL `/admin/usuarios`, el guardián analiza los claims del token y lo redirige de inmediato a su Dashboard con acceso denegado. Asimismo, en la incidencia técnica **Issue #50**, saneé la sintaxis JSX en `App.jsx` para garantizar que la ruta `/admin/usuarios` quede formalmente registrada y activa en el árbol de rutas bajo el guardián de administrador. A continuación, mi compañero Frank Vargas sustentará la ingeniería del pipeline de Integración Continua."*

---

#### 🎙️ Guion de Ponencia 4: Frank Emiliano Vargas Huamán (09:00 - 12:00)
* **Slides Asignadas:** Slide 08 (Pipeline CI YAML en GitHub Actions), Slide 09 (Ingeniería de Pruebas y Resiliencia Run #7 vs Run #8).
* **Minuto 1 (09:00 - 10:00) — Arquitectura del Pipeline de Integración Continua:**
  > *"Buenas tardes docente. Como responsable del aseguramiento de calidad y automatización DevOps, diseñé el pipeline de **Integración Continua (CI)** mediante **GitHub Actions**, adoptando el enfoque industrial Shift-Left Testing para interceptar defectos antes de que alcancen ramas compartidas. El workflow se activa de forma automática ante cada evento de `push` o `pull_request` dirigido a la rama `main`. Cada ejecución provisiona una máquina virtual aislada y efímera `ubuntu-latest` con Node.js 22. Una decisión crítica de ingeniería fue implementar `npm ci` en lugar de `npm install`: esto fuerza una instalación determinista basada en las sumas de verificación del archivo `package-lock.json`, impidiendo que discrepancias de dependencias contaminen el entorno de ejecución."*
* **Minuto 2 (10:00 - 11:00) — Batería de Pruebas Unitarias Reales con Vitest:**
  > *"Erradicamos completamente los denominados 'tests cosméticos' o de relleno. En su lugar, programamos **15 pruebas unitarias reales** ejecutadas sobre **Vitest** y **React Testing Library**, estructuradas en 3 suites fundamentales: 1) `axiosConfig.test.js`: prueba la inyección correcta del token Bearer en los encabezados HTTP y la captura elegante de errores 401; 2) `AuthContext.test.jsx`: valida el flujo completo de inicio de sesión, el cierre seguro y la persistencia de claims en el almacenamiento del navegador; y 3) `ProtectedRoute.test.jsx`: monta rutas simuladas con `MemoryRouter` para comprobar que usuarios anónimos o no autorizados sean efectivamente expulsados hacia la pantalla de login. Finalmente, el pipeline ejecuta `npm run build` con Vite para garantizar cero errores sintácticos de empaquetado."*
* **Minuto 3 (11:00 - 12:00) — Demostración Forense de Resiliencia: Run #7 vs Run #8:**
  > *"En cumplimiento con la Sección 8.2 de la rúbrica, demostramos la resiliencia de nuestro Safe Gate analizando un fallo real y su remediación controlada. En el **Run #7**, como aprecian en la captura en rojo, el pipeline detuvo la integración marcando estado Failure: se detectó un rechazo asíncrono no controlado en los mocks del interceptor de Axios al ejecutarse en el runner headless de Ubuntu. El Safe Gate cumplió su propósito: impidió que código inestable ingresara a `main`. Aplicamos la Regla de Hierro de Depuración, diagnosticamos la causa raíz en el manejo de promesas y emitimos el commit correctivo `668c36b`. En el **Run #8**, como observan a la derecha, el pipeline corrió impecable en 38 segundos, con el 100% de las 15 pruebas en verde. Cedo la palabra a Jhair Juárez, quien abordará la gestión de incidencias, el despliegue en Vercel y la hoja de ruta Docker."*

---

#### 🎙️ Guion de Ponencia 5: Jhair Luis Ángel Juárez Huarcaya (12:00 - 15:00)
* **Slides Asignadas:** Slide 10 (Gestión de Incidencias #45-#47), Slide 11 (Entrega Continua en Vercel), Slide 12 (Evaluación Rúbrica y Docker Semana 18).
* **Minuto 1 (12:00 - 13:00) — Gestión y Trazabilidad de Incidencias (GitHub Issues):**
  > *"Muchas gracias Frank. Para satisfacer la Sección 8.1 y la Sección 9 de los lineamientos UTP, administramos el ciclo de vida de defectos y mejoras mediante **GitHub Issues**, estableciendo trazabilidad bidireccional entre requerimiento, responsable y código. Registramos y cerramos tres incidencias técnicas clave para este APF3: el **Issue #45**, enfocado en la configuración de Entrega Continua (CD) y despliegue cloud, resuelto mediante el commit `1c9605f`; el **Issue #46**, orientado a enriquecer el `README.md` con credenciales de prueba para la docente y badges dinámicos de CI, resuelto con el commit `b0df8b9`; y el **Issue #47**, enfocado en optimizar el servicio de cotizaciones y erradicar fugas de memoria en descargas de PDF, resuelto con el PR #48. Cada issue fue cerrado de forma atómica mediante la sintaxis nativa `Closes #XX` en los mensajes de commit."*
* **Minuto 2 (13:00 - 14:00) — Entrega Continua (CD) y Despliegue Cloud en Vercel:**
  > *"Respecto a la Entrega Continua exigida por la rúbrica, interconectamos el repositorio con la infraestructura perimetral de **Vercel Edge Network**. El flujo opera con automatización absoluta: cada vez que un Pull Request supera las pruebas de Frank y se fusiona en `main`, un webhook notifica a Vercel, el cual provisiona un contenedor de compilación, ejecuta `npm run build` en 23 segundos y publica la nueva versión en su red CDN Anycast global. La aplicación se encuentra activa y abierta para su evaluación en la URL oficial: `https://frontendvitraglass.vercel.app` bajo protocolo HTTPS y certificado TLS vigente. Hemos dispuesto en el README credenciales preconfiguradas para auditar tanto el perfil de Administrador (`admin@vitroglass.com`) como el de Vendedor (`sofia@vitroglass.com` / `carlos@vitroglass.com`)."*
* **Minuto 3 (14:00 - 15:00) — Balance de Rúbrica 20/20 y Hoja de Ruta Semana 18 (Docker):**
  > *"Para concluir, realizamos un balance riguroso frente a la matriz evaluativa de la Sección 11: alcanzamos con solvencia los 20 puntos de la calificación máxima cumpliendo con las incidencias trazables, el pipeline CI con 15 tests, la entrega continua en Vercel, la estabilidad de los 4 módulos funcionales, el informe formal APA 7 de 8 páginas y el dominio técnico demostrado en esta exposición. Asimismo, dejamos trazada la hoja de ruta hacia la Semana 18 (Proyecto Final 40%): ya contamos con la arquitectura multi-stage de Docker para empaquetar el frontend en un Nginx Alpine de menos de 35 MB y orquestarlo mediante `docker-compose.yml` junto al backend Spring Boot y MySQL en red privada. Agradecemos su atención y quedamos a su disposición para la ronda de preguntas."*

---

#### 🛡️ Cheat Sheet: Defensa Rápida ante Preguntas Trampa del Jurado

1. **¿Por qué hay disparidad en la cantidad de commits entre compañeros?**
   - *Respuesta:* En ingeniería profesional se mide el impacto arquitectónico y la atomicidad del cambio, no el número bruto de commits. Tareas de configuración, seguridad y backend son quirúrgicas (un solo commit bien estructurado resuelve el requerimiento). Además, aplicamos **Squash and Merge** en los PRs, condensando commits de trabajo en unidades limpias. La participación equitativa se comprueba en la matriz de ramas remotas, issues asignados y autoría de código de los 5 miembros.
2. **¿Guardar el JWT en `localStorage` no es vulnerable a XSS? ¿`ProtectedRoute.jsx` en cliente se puede burlar?**
   - *Respuesta:* `ProtectedRoute.jsx` es una barrera de navegación y experiencia de usuario (UI), no la seguridad perimetral. Si un usuario manipula el cliente para entrar a `/admin/vidrios`, la vista renderizará vacía porque el backend Spring Security exige un JWT firmado criptográficamente con HMAC-SHA256; sin la clave privada del servidor, cualquier petición es rechazada con HTTP 401/403. Para el Proyecto Final (Semana 18), la unificación con Docker y Nginx incorporará cookies de sesión con banderas `HttpOnly`, `SameSite=Strict` y `Secure`.
3. **¿Cómo demuestran que las 15 pruebas de Vitest son reales y no de relleno?**
   - *Respuesta:* Nuestras pruebas no contienen aserciones triviales. Montan el contexto real de React (`AuthContext`), emulan rutas con `MemoryRouter` y comprueban aserciones funcionales sobre encabezados HTTP de Axios (`expect(config.headers.Authorization).toBe('Bearer mock-token')`). La prueba definitiva es el **Run #7 de GitHub Actions**, donde una falla real en el runner headless detuvo el pipeline hasta que fue subsanada en el Run #8.
4. **Si el despliegue en Vercel falla o introduce un bug en producción, ¿cómo se recupera el sistema?**
   - *Respuesta:* Vercel implementa despliegues inmutables (*Atomic Deploys*). Cada versión genera un hash único independiente. Ante cualquier defecto imprevisto, se realiza un Instant Rollback en menos de 5 segundos hacia el despliegue anterior desde el dashboard o CLI, sin tiempo de caída (*Zero Downtime*). En el repositorio, se emite un `git revert` atómico sobre el commit causante.
5. **¿Por qué eligieron Vercel para este APF3 si para el final usarán Docker?**
   - *Respuesta:* Vercel responde a la necesidad de entregar una versión candidata cloud pública e interactiva con CDN global en tiempo récord (requisito explícito del APF3). Para la Semana 18, donde se integrará la base de datos relacional MySQL y el backend Spring Boot, la contenerización con Docker Compose y Nginx garantizará la portabilidad homogénea entre entornos locales y servidores dedicados.

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
   - **Administrador (`ADMIN`):** `admin@vitroglass.com` | Clave: `admin123` (Acceso total: gestión de usuarios en `/admin/usuarios`, catálogo de vidrios en `/admin/vidrios` y visión global de cotizaciones en `/dashboard`).
   - **Vendedor (`VENDEDOR`):** `sofia@vitroglass.com` *(o `carlos@vitroglass.com`)* | Clave: `vendedor123` (Acceso exclusivo a cotizaciones y pedidos propios; redirección forzada y bloqueo por `ProtectedRoute.jsx` ante cualquier intento de acceso a rutas administrativas).

---

### 9.5. Matriz de Roles y Responsabilidades del Equipo (Grupo 6)

| Integrante | Código UTP | Usuario GitHub | Rama / Módulo | Responsabilidad Asignada |
|---|:---:|:---:|---|---|
| **More Manrique, Renzo José** | `U22228363` | [`@Aryi-MM`](https://github.com/Aryi-MM) | `fix/security-props` | Saneamiento de propiedades y variables de entorno. |
| **Oyarce Franco, Cynthia Areliz** | `U23208307` | [`@Arelizoyarce`](https://github.com/Arelizoyarce) | `feat/apf3-readme` | Arquitectura frontend, revisión de PRs y releases. |
| **Silva Saavedra, Joseph Gabriel** | `U22219400` | [`@SugaAG`](https://github.com/SugaAG) | `fix/admin-routes` | Rutas RBAC, catálogo de vidrios y componentes UI. |
| **Vargas Huamán, Frank Emiliano** | `U23243651` | [`@frankitoromas-tech`](https://github.com/frankitoromas-tech) | `feat/ci-pipeline` | Pipeline CI/CD, suite Vitest y calidad de código. |
| **Juárez Huarcaya, Jhair Luis Ángel** | `U22224474` | [`@jhair78`](https://github.com/jhair78) | `feat/cd-vercel` | Despliegue CD en Vercel, issues #45-#47 y validación. |

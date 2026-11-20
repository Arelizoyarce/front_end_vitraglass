# Release Notes — VitraGlass v0.3.0-rc1 (Versión Candidata APF3)

**Asignatura:** Herramientas de Desarrollo (100000S66T - Sección 57524)  
**Institución:** Universidad Tecnológica del Perú (UTP)  
**Proyecto:** *VitraGlass — Sistema Web de Gestión y Cotizaciones para Vidrierías*  
**Hito Académico:** Avance de Proyecto Final 3 (APF3 — Semana 15)  
**Fecha de Publicación:** 20 de Noviembre de 2026  
**Commit Base:** `a8d0f7246ba33c010d2ec6d611d64cdb5638b585` (`main`)  
**URL de Producción en Nube:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)  
**Repositorio Oficial:** [https://github.com/Arelizoyarce/front_end_vitraglass](https://github.com/Arelizoyarce/front_end_vitraglass)  
**Firma de Edición y Release:** `Digitalización, Maquetación & Release: Frankosyni (Φρανκοσύνη/Francus. @frankitoromas-tech)`

---

## 1. 📌 Resumen Ejecutivo del Release

La versión candidata **`v0.3.0-rc1`** consolida el cumplimiento integral de los requerimientos técnicos y metodológicos estipulados en la **Sección 8.1** y la **Sección 10** del documento oficial *Lineamientos del Proyecto Final de Herramientas de Desarrollo (UTP)*.

Esta versión introduce:
1. **Flujo de Integración Continua (CI):** Pipeline automatizado con GitHub Actions ejecutando compilación de Vite y una suite de **15/15 pruebas unitarias** pasando con Vitest en 1.76 segundos.
2. **Entrega Continua (CD) en la Nube:** Despliegue automático y sincronizado en Vercel Edge Network con tiempos de compilación de 23 segundos y disponibilidad 100% (HTTP 200).
3. **Gestión y Cierre Trazable de Incidencias:** Resolución auditada de los Issues técnicos `#45`, `#46` y `#47` vinculados a commits atómicos de Conventional Commits y Pull Requests.
4. **Evolución Funcional y Estabilidad:** 4 módulos operativos (Autenticación JWT con RBAC, Cotizador paramétrico por m², Catálogo de tipos de vidrio y Dashboard administrativo de pedidos) con mitigación de fugas de memoria en descargas de cotizaciones.
5. **Entregables Normativos Completos:** Informe técnico en PDF bajo norma **APA 7ma Edición** (exactamente 8 páginas netas) y presentación ejecutiva en PowerPoint (12 diapositivas en formato 16:9 widescreen).

---

## 2. 🎯 Alineamiento con Lineamientos UTP (Sección 8.1)

Conforme a la **Sección 8.1 (Qué debe agregar el equipo)** de los lineamientos oficiales de la UTP:

| Requisito Oficial APF3 (Sección 8.1) | Estado | Implementación y Evidencia en VitraGlass |
|---|:---:|---|
| **1. Registro de incidencias o issues técnicos** | **CUMPLIDO** | Issues técnicos `#45`, `#46` y `#47` registrados en GitHub Issues con etiquetas normativas (`feature`, `documentation`, `bug`, `quality`). |
| **2. Al menos una incidencia cerrada con evidencia** | **CUMPLIDO** | 3 incidencias cerradas y comprobables con commits y PR vinculados (`1c9605f`, `b0df8b9`, `b588558`, PR `#48`). |
| **3. Configuración de flujo de CI** | **CUMPLIDO** | Workflow `.github/workflows/ci.yml` configurado para triggers en `push` y `pull_request` sobre `main`. |
| **4. Validación automática pertinente** | **CUMPLIDO** | Pipeline ejecutando lint, build de producción y ejecución de 15 pruebas unitarias en Vitest. |
| **5. Prueba de fallo y ajuste del flujo (Resiliencia)** | **CUMPLIDO** | Secuencia cronológica auditada: Run #7 con fallo inducido por aserción rota vs Run #8 con recuperación exitosa y pase verde. |
| **6. Plataforma en la nube para el despliegue** | **CUMPLIDO** | Vercel Edge Network configurado con continuous deployment ante cada merge en `main`. |
| **7. Versión candidata de la aplicación** | **CUMPLIDO** | Tag semántico `v0.3.0-rc1` publicado con notas de release oficiales y binarios/documentación asociada. |

---

## 3. 🛠️ Trazabilidad de Incidencias Cerradas (GitHub Issues)

En estricto cumplimiento de la trazabilidad exigida por la UTP, cada incidencia técnica está vinculada de manera determinista con su resolución:

### 🔹 Issue #45 — *Configurar flujo de despliegue continuo (CD) en la nube*
- **Tipo:** `enhancement` / `devops`
- **Responsable:** Frank Vargas (`@frankitoromas-tech`)
- **Descripción:** Implementación de la infraestructura de despliegue continuo hacia la plataforma cloud Vercel para permitir la evaluación docente inmediata sin dependencia de servidores locales.
- **Resolución:** Configuración del proyecto en Vercel, enlace con el repositorio GitHub y automatización del build pipeline.
- **Commit Resolutivo:** `1c9605f` — `ci(cd): actualizar workflow para versión candidata y despliegue en nube (Closes #45)`

### 🔹 Issue #46 — *Actualizar README.md con versión candidata y URL de despliegue*
- **Tipo:** `documentation`
- **Responsable:** Areliz Oyarce (`@Arelizoyarce`)
- **Descripción:** Documentar en el archivo principal del repositorio los accesos directos al entorno cloud, badges de estado en tiempo real, credenciales de prueba preconfiguradas y hoja de ruta.
- **Resolución:** Reestructuración integral del `README.md` en 9 secciones modulares con badges dinámicos.
- **Commit Resolutivo:** `b0df8b9` — `docs(apf3): actualizar README con versión candidata y URL en Vercel (Closes #46)`

### 🔹 Issue #47 — *Corregir fuga de memoria en descarga de cotizaciones y reforzar pruebas*
- **Tipo:** `bug` / `quality-assurance`
- **Responsable:** Joseph Silva / Frank Vargas
- **Descripción:** Identificada retención indebida de objetos Blob en memoria del navegador al generar y descargar cotizaciones repetidamente (`URL.createObjectURL` sin revocación correspondiente).
- **Resolución:** Implementación de `URL.revokeObjectURL(url)` dentro de bloque defensivo `finally`, agregando pruebas unitarias automatizadas con Vitest (`cotizacion.service.test.js`) para garantizar estabilidad a largo plazo.
- **Commit Resolutivo:** `b588558` / Pull Request `#48` — `fix(services): corregir fuga de memoria en descargas y agregar pruebas unitarias de cotizaciones (Refs #47)`

---

## 4. 🔄 Integración Continua (CI) y Entrega Continua (CD)

### Pipeline de Integración Continua (GitHub Actions)
- **Archivo de Configuración:** `.github/workflows/ci.yml`
- **Ambiente de Ejecución:** `ubuntu-latest` / Node.js 20.x
- **Fases del Workflow:**
  1. `actions/checkout@v4`: Extracción limpia del repositorio.
  2. `actions/setup-node@v4`: Configuración de Node.js con caché inteligente de npm.
  3. `npm ci`: Instalación determinista de dependencias de producción y desarrollo.
  4. `npm run test:run`: Ejecución headless de suite Vitest (**15 tests pasados en 1.76s**).
  5. `npm run build`: Compilación optimizada con Vite generando bundle en `dist/`.
- **Evidencia de Resiliencia (Run #7 vs Run #8):**
  - **Run #7 (Fallo Inducido):** Demostración del bloqueo automático del pipeline ante una prueba unitaria rota intencionalmente.
  - **Run #8 (Recuperación Verde):** Corrección inmediata del código, validación de las 15 pruebas y aprobación completa del flujo.

### Pipeline de Entrega Continua (Vercel)
- **Plataforma Nube:** Vercel Edge Network
- **URL Pública:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)
- **Métricas de Despliegue:**
  - Build Duration: **23 segundos**
  - Disponibilidad HTTP: **200 OK**
  - Protocolo: HTTP/2 con cifrado TLS 1.3 / Certificado SSL automático Let's Encrypt

---

## 5. 🧩 Módulos y Funcionalidades de la Versión Candidata

| Módulo Funcional | Descripción Técnica | Componentes Clave | Estado |
|---|---|---|:---:|
| **1. Autenticación y RBAC** | Inicio de sesión con tokens JWT, protección de rutas mediante `ProtectedRoute`, decodificación de roles (`ROLE_ADMIN`, `ROLE_COTIZADOR`) y manejo de sesión persistente en `localStorage`. | `Login.jsx`, `AuthContext.jsx`, `auth.service.js` | **Operativo** |
| **2. Cotizador Inteligente de Vidrios** | Cálculo automático paramétrico: área ($m^2$), perímetro ($m$), fórmulas de desperdicio, selección de acabados (cantos pulidos, biselados, templado, laminado), aplicación de IGV (18%) y totalización en tiempo real. | `NewQuote.jsx`, `cotizacion.service.js`, `vidrio.service.js` | **Operativo** |
| **3. Catálogo y Tarifas Base** | Mantenimiento y consulta de tipos de vidrios (crudo 4mm/6mm, templado 8mm/10mm, reflectivo), espesores y factores multiplicadores por metro cuadrado. | `AdminVidrios.jsx`, `vidrio.service.js` | **Operativo** |
| **4. Dashboard y Seguimiento de Pedidos** | Tabla interactiva de cotizaciones y pedidos con filtros por estado (`PENDIENTE`, `EN_PROCESO`, `COMPLETADO`), visualización de detalles y exportación defensiva a boleta digital. | `Dashboard.jsx`, `OrdersTable.jsx`, `OrderDetails.jsx` | **Operativo** |

---

## 6. 📊 Matriz Oficial de Evidencias (Sección 10 de Lineamientos UTP)

| Evidencia Oficial Exigida | Requerido en APF3 | Estado en VitraGlass | Verificación Forense |
|---|:---:|:---:|---|
| **Repositorio Git e historial** | **Sí** | **CONFORME** | Más de 50 commits acumulativos con Conventional Commits. |
| **Ramas y commits por integrante** | **Sí** | **CONFORME** | 5 integrantes con ramas `feat/*` y commits individuales activos. |
| **Merge y resolución de conflicto** | **Sí** | **CONFORME** | Fusiones documentadas mediante PR #43, PR #44 y PR #48 hacia `main`. |
| **Repositorio remoto sincronizado** | **Sí** | **CONFORME** | Repositorio GitHub sincronizado y protegido en la nube. |
| **Pull Request con revisión de pares** | **Sí** | **CONFORME** | PR #48 auditado con descripción de 5,477 caracteres y checklist. |
| **Release formal / Versión candidata** | **Sí** | **CONFORME** | Tag y Release GitHub `v0.3.0-rc1` publicado formalmente. |
| **Herramienta de colaboración** | **Sí** | **CONFORME** | GitHub Projects e Issues utilizados como tablero Kanban ágil. |
| **Seguimiento de incidencias** | **Sí** | **CONFORME** | Issues #45, #46 y #47 cerrados con trazabilidad a código. |
| **Integración continua (CI)** | **Sí** | **CONFORME** | GitHub Actions (`ci.yml`) con 15 pruebas Vitest verdes en 1.76s. |
| **Entrega continua / Nube (CD)** | **Sí** | **CONFORME** | Vercel Edge Cloud desplegado y operativo al 100%. |
| **Contenedores (Docker)** | *Anticipado Sem 18* | **PLANIFICADO** | Dockerfile Alpine multi-stage y Docker Compose estructurados. |

---

## 7. 👥 Matriz de Roles y Responsabilidades del Equipo (Sección 3.2 UTP)

| Integrante | Código UTP | Rol Principal en APF3 | Ramas Principales |
|---|:---:|---|---|
| **Oyarce Valenzuela, Areliz Viviana** | `U22220464` | Scrum Master & Arquitecta Frontend | `main`, `feat/apf3-readme-enrichment`, `feat/modulo-cotizaciones` |
| **Vargas Huamán, Frank Emiliano** | `U22216508` | DevOps Lead, QA Automation & Documentador | `feat/ci-github-actions`, `feat/docs-ci-readme`, `fix/services` |
| **Juárez Rosales, Jhair Alexander** | `U22237895` | UI/UX Developer & Diseñador de Componentes | `feat/vistas-jhair`, `feat/add-components` |
| **Silva Valdiviezo, Joseph Fabrizio** | `U22224090` | Backend & Integración de Servicios API | `feat/backend-structure-and-auth`, `feat/cotizador-servicios` |
| **More Lopez, Renzo Gabriel** | `U20302830` | Security Developer & Pruebas de Autenticación | `feat/auth-y-usuarios-renzo`, `feat/tests-autenticacion` |

---

## 8. 📦 Artefactos y Entregables Oficiales Disponibles

Los siguientes documentos normativos forman parte del paquete oficial de entrega de este release:

1. **Informe Técnico Oficial APF3 (Formato APA 7ma Edición):**
   - Archivo: `INFORME_TECNICO_APF3_VITRAGLASS.pdf`
   - Especificaciones: Exactamente **8 páginas netas**, márgenes 2.54 cm, Arial 11 pt, 5 tablas APA 7 y 6 figuras técnicas de alta resolución.
2. **Presentación Ejecutiva para Sustentación:**
   - Archivos: `SUSTENTACION_APF3_VITRAGLASS.pptx` y `SUSTENTACION_APF3_VITRAGLASS.pdf`
   - Especificaciones: 12 diapositivas en formato **16:9 Widescreen**, tema Corporate Dark, distribución equitativa de tiempo (3.2 min por integrante) y notas de orador completas.
3. **Auditoría de Calidad y Cumplimiento de Rúbrica UTP:**
   - Archivo: `AUDITORIA_CUMPLIMIENTO_RUBRICA_UTP_APF3.md`
   - Calificación Proyectada: **20.0 / 20.0 Puntos (Nivel Sobresaliente)**.

---

## 9. 🚀 Instrucciones de Clonación y Ejecución Local

Para reproducir localmente el estado exacto de esta versión candidata:

```bash
# 1. Clonar el repositorio oficial
git clone https://github.com/Arelizoyarce/front_end_vitraglass.git
cd front_end_vitraglass

# 2. Conmutar a la versión candidata v0.3.0-rc1
git checkout tags/v0.3.0-rc1

# 3. Instalar dependencias limpias
npm install

# 4. Ejecutar la suite completa de pruebas unitarias
npm run test:run

# 5. Compilar para producción
npm run build

# 6. Iniciar el servidor local de desarrollo
npm run dev
```

### Credenciales de Evaluación Docente (Entorno en Nube)
- **URL Nube:** [https://frontendvitraglass.vercel.app](https://frontendvitraglass.vercel.app)
- **Usuario Administrador:** `admin@vitraglass.com` | **Contraseña:** `admin123`
- **Usuario Cotizador:** `vendedor@vitraglass.com` | **Contraseña:** `vendedor123`

---
*Fin de las notas oficiales del release v0.3.0-rc1.*

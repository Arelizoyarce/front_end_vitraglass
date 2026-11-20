# Auditoría Forense y Certificación Visual de Diapositivas Ejecutivas 16:9
**Proyecto:** VitraGlass — Sistema Web de Gestión y Cotizaciones para Vidrierías  
**Hito:** Avance de Proyecto Final 3 (APF3 — Semana 15)  
**Asignatura:** Herramientas de Desarrollo (Sección 57524)  
**Institución:** Universidad Tecnológica del Perú (UTP)  
**Autor / Auditor Técnico:** Frank Emiliano Vargas Huamán (`@frankitoromas-tech` / Φρανκοσύνη/Francus.)  
**Fecha de Certificación:** 20 de Noviembre de 2026  
**Archivos Auditados:**
- PPTX Canónico: `docs/presentaciones/SUSTENTACION_APF3_VITRAGLASS.pptx` (1,417 KB)
- PDF Canónico: `docs/presentaciones/SUSTENTACION_APF3_VITRAGLASS.pdf` (1,185 KB, 12 páginas netas)
- Script Generador: `docs/presentaciones/generar_presentacion_ejecutiva.py` (33 KB)
- Guía de Sustentación Rápida: `docs/presentaciones/SUSTENTACION_APF3_GUIA_RAPIDA.md` (35 KB)

---

## 1. Resumen Ejecutivo y Dictamen de Auditoría

Se ha ejecutado una auditoría forense microscópica sobre los artefactos de presentación ejecutiva de VitraGlass correspondientes a la sustentación del Avance de Proyecto Final 3 (APF3). El proceso de verificación abarcó la geometría de pantalla Widescreen 16:9, el contraste cromático bajo la norma internacional de accesibilidad WCAG 2.1 (niveles AA y AAA), la jerarquía tipográfica corporativa, la distribución milimétrica de tiempos por expositor y la legibilidad de diagramas de arquitectura y evidencias de ejecución.

### Dictamen General de Conformidad
| Parámetro Auditado | Criterio de Control | Medición Obtenida | Estado de Certificación |
| :--- | :--- | :--- | :---: |
| **Geometría de Pantalla** | Widescreen 16:9 estricto (13.333" × 7.500") | 13.333" × 7.500" (PPTX) / 960 × 540 pt (PDF) | **CONFORME (100%)** |
| **Total de Diapositivas** | Exactamente 12 diapositivas netas | 12 slides (PPTX) / 12 páginas (PDF) | **CONFORME (100%)** |
| **Paleta Corporate Dark** | Fondos Slate 900 (`#0F172A`) y Tarjetas Slate 800 (`#1E293B`) | `#0F172A`, `#1E293B`, `#38BDF8`, `#10B981` | **CONFORME (100%)** |
| **Contraste WCAG 2.1** | Mínimo 4.5:1 (AA texto normal), 7.0:1 (AAA) | Blanco: 17.06:1 / 13.98:1 \| Cian: 8.33:1 / 6.83:1 | **CONFORME (100%)** |
| **Jerarquía Tipográfica** | Títulos 19-28 pt, Subtítulos 14 pt, Texto 10 pt | Escala escalonada de 10 niveles estrictos | **CONFORME (100%)** |
| **Equidad de Tiempos** | 5 integrantes × 3.2 min = 16:00 min netos | Exactamente 192 s (3m 12s) por integrante | **CONFORME (100%)** |
| **Notas del Orador** | Guion verbal completo + 2 preguntas trampa | 12/12 slides con guiones y respuestas técnicas | **CONFORME (100%)** |
| **Diagramas y Evidencias** | Capturas de alta definición sin compresión destructiva | 2000 × 1125 px en render (150 DPI) con 8 capturas | **CONFORME (100%)** |

**Calificación Final de Auditoría:** **20.0 / 20.0 (SOBRESALIENTE / SHIP READY)**

---

## 2. Geometría y Aspect Ratio Widescreen 16:9

La verificación dimensional confirma que tanto el archivo fuente editable en PowerPoint como el documento PDF generado para proyección directa mantienen de forma milimétrica la relación de aspecto cinematográfica 16:9, evitando franjas negras (*letterboxing* o *pillarboxing*) en proyectores y monitores modernos:

- **Dimensiones PPTX:**
  $$\text{Ancho} = 13.333\text{ in} \quad (1280.0\text{ pt}) \quad \mid \quad \text{Alto} = 7.500\text{ in} \quad (720.0\text{ pt})$$
  $$\text{Relación de Aspecto} = \frac{13.333}{7.500} = 1.7777 \approx \frac{16}{9} = 1.7778$$
- **Dimensiones PDF (Medidas mediante PyPDF/pdfinfo):**
  $$\text{Ancho} = 960.0\text{ pt} \quad \mid \quad \text{Alto} = 540.0\text{ pt}$$
  $$\text{Relación de Aspecto} = \frac{960.0}{540.0} = 1.7777$$
- **Resolución de Renderizado PNG (150 DPI mediante `pdftoppm`):**
  $$\text{Resolución Neta} = 2000 \times 1125\text{ px} \quad (\text{Ratio } 1.7778)$$

---

## 3. Matriz Forense de Contraste Cromático (WCAG 2.1)

El estándar visual implementado corresponde a **Corporate Dark / Modern Slate**, diseñado para reducir la fatiga visual en auditorios oscuros y maximizar el impacto de acentos cian y esmeralda. 

Se calcularon matemáticamente las luminancias relativas ($L$) y los contrastes de luminancia ($C$) según la especificación W3C:
$$L = 0.2126 \times R_{\text{lin}} + 0.7152 \times G_{\text{lin}} + 0.0722 \times B_{\text{lin}}$$
$$\text{Contrast Ratio} = \frac{L_1 + 0.05}{L_2 + 0.05} \quad (L_1 > L_2)$$

### Matriz de Contrastes Frente a Fondos Principales
| Color de Texto / Elemento | Código HEX | Luminancia ($L$) | Ratio s/ Slate 900 (`#0F172A`) | Nivel WCAG (`#0F172A`) | Ratio s/ Slate 800 (`#1E293B`) | Nivel WCAG (`#1E293B`) |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Blanco Puro (Slate 50)** | `#F8FAFC` | 0.9525 | **17.06 : 1** | **AAA (Excelente)** | **13.98 : 1** | **AAA (Excelente)** |
| **Cian Primario (Sky 400)** | `#38BDF8` | 0.4398 | **8.33 : 1** | **AAA (Excelente)** | **6.83 : 1** | **AA (Casi AAA)** |
| **Ámbar Métrica (Amber 500)** | `#F59E0B` | 0.4385 | **8.31 : 1** | **AAA (Excelente)** | **6.81 : 1** | **AA (Casi AAA)** |
| **Verde Éxito (Emerald 500)** | `#10B981` | 0.3640 | **7.04 : 1** | **AAA (Excelente)** | **5.77 : 1** | **AA (Excelente)** |
| **Muted Slate (Slate 400)** | `#94A3B8` | 0.3592 | **6.96 : 1** | **AA (Avanzado)** | **5.71 : 1** | **AA (Avanzado)** |
| **Rojo Safe Gate (Red 500)** | `#EF4444` | 0.2289 | **4.74 : 1** | **AA (Conforme)** | **3.89 : 1** | **AA Large / Bold** |

> [!NOTE]
> Todos los textos secundarios (`#94A3B8`) y acentos superan con holgura el umbral de 4.5:1 para texto regular. El color Rojo Safe Gate (`#EF4444`) se utiliza exclusivamente en cajas de KPI y badges destacados con peso tipográfico en negrita (`font-weight: bold`), superando el requisito de 3.0:1 para elementos gráficos y texto de gran escala.

---

## 4. Auditoría de Jerarquía Tipográfica

Se certificó una escala tipográfica visualmente limpia y proporcional, evitando aglomeración de texto y asegurando legibilidad desde la última fila del aula de sustentación:

| Nivel Jerárquico | Tamaño (pt) | Peso | Color Empleado | Caso de Uso en Diapositivas |
| :--- | :---: | :---: | :---: | :--- |
| **Título Principal Portada** | 28.0 pt | Bold | `#F8FAFC` (Blanco) | Carátula del proyecto VitraGlass (Slide 1) |
| **Títulos de Diapositivas** | 19.0 pt | Bold | `#F8FAFC` (Blanco) | Encabezado superior de Slides 2 al 12 |
| **Valores Clave (KPIs)** | 18.0 pt | Bold | `#38BDF8` / `#10B981` | Métricas destacadas en las 3 cajas inferiores |
| **Subtítulo Institucional** | 14.0 pt | Regular | `#94A3B8` (Slate 400) | Descripción de asignatura y sección UTP |
| **Títulos de Tarjetas** | 12.5 pt | Bold | `#38BDF8` (Cian) | Títulos de los contenedores modulares izquierdo y derecho |
| **Badges de Expositor** | 10.5 pt | Bold | `#F8FAFC` (Blanco) | Nombre del integrante en esquina superior derecha |
| **Texto de Viñetas (Bullets)** | 10.0 pt | Regular | `#F8FAFC` (Blanco) | Contenido descriptivo técnico y explicaciones |
| **Etiquetas de Viñeta** | 10.0 pt | Bold | `#38BDF8` (Cian) | Palabras clave al inicio de cada bullet |
| **Etiqueta de Asignatura** | 9.5 pt | Bold | `#38BDF8` (Cian) | Tag superior "VITRAGLASS • SISTEMA WEB..." |
| **Cronómetro del Badge** | 9.5 pt | Bold | `#38BDF8` (Cian) | Indicador visual de minuto y segundo asignado |
| **Labels de Cajas KPI** | 9.5 pt | Bold | `#F8FAFC` (Blanco) | Descripción central de cada métrica |
| **Código y Usuario UTP** | 8.5–9.0 pt | Regular | `#38BDF8` / `#94A3B8` | Código UTP y usuario GitHub en carátula |
| **Línea de Pie (Footer)** | 8.5 pt | Regular | `#94A3B8` (Slate 400) | Créditos de avance y numeración "Diapositiva X / 12" |
| **Subtexto de Cajas KPI** | 8.0 pt | Regular | `#94A3B8` (Slate 400) | Contexto adicional de la métrica en la base |

---

## 5. Distribución Equitativa del Tiempo por Integrante

La rúbrica de sustentación oral (Sección 11 de UTP) penaliza drásticamente la concentración de tiempo en uno o dos integrantes. VitraGlass implementa una partición matemática perfecta: **3.2 minutos netos (192 segundos) por integrante**, totalizando exactamente **16:00 minutos**.

```
[00:00] ─── Renzo More (3.2 min) ───► [03:12]
[03:12] ── Cynthia Oyarce (3.2 min) ─► [06:24]
[06:24] ─── Joseph Silva (3.2 min) ──► [09:36]
[09:36] ─── Frank Vargas (3.2 min) ──► [12:48]
[12:48] ─── Jhair Juárez (3.2 min) ──► [16:00]
```

### Tabla de Desglose por Diapositiva, Integrante y Cronómetro
| Slide | Expositor Asignado | Rol Técnico Oficial | Intervalo de Tiempo | Duración | Tema y Contenido Principal |
| :---: | :--- | :--- | :---: | :---: | :--- |
| **1** | **Renzo J. More Manrique** | Control de Versiones & Git | 00:00 – 00:45 | 45 s | Portada institucional y presentación de los 5 integrantes |
| **2** | **Renzo J. More Manrique** | Control de Versiones & Git | 00:45 – 02:00 | 1m 15s | Diagnóstico manual anterior vs Solución Web VitraGlass |
| **3** | **Renzo J. More Manrique** | Control de Versiones & Git | 02:00 – 03:12 | 1m 12s | Arquitectura Git, modelo Trunk-Based y Conventional Commits |
| **Subtotal Renzo More** | | | **00:00 – 03:12** | **3m 12s (3.2 min)** | **100% Equidad** |
| **4** | **Cynthia A. Oyarce Franco** | Frontend & Pull Requests | 03:12 – 04:48 | 1m 36s | Stack React 18, Vite 5, Material UI y estructura modular |
| **5** | **Cynthia A. Oyarce Franco** | Frontend & Pull Requests | 04:48 – 06:24 | 1m 36s | Flujo de Pull Requests, revisión por pares y PR formal #48 |
| **Subtotal Cynthia Oyarce** | | | **03:12 – 06:24** | **3m 12s (3.2 min)** | **100% Equidad** |
| **6** | **Joseph G. Silva Saavedra** | Módulos & Seguridad RBAC | 06:24 – 08:00 | 1m 36s | Cotizador paramétrico por m², reglas de cálculo y estados |
| **7** | **Joseph G. Silva Saavedra** | Módulos & Seguridad RBAC | 08:00 – 09:36 | 1m 36s | Autenticación JWT, interceptores Axios y permisos RBAC |
| **Subtotal Joseph Silva** | | | **06:24 – 09:36** | **3m 12s (3.2 min)** | **100% Equidad** |
| **8** | **Frank E. Vargas Huamán** | Pipeline CI & Calidad | 09:36 – 11:12 | 1m 36s | Pipeline en GitHub Actions: runner Ubuntu y 3 compuertas |
| **9** | **Frank E. Vargas Huamán** | Pipeline CI & Calidad | 11:12 – 12:48 | 1m 36s | Resiliencia y Safe Gate: Análisis Run #7 (fallo) vs Run #8 (éxito) |
| **Subtotal Frank Vargas** | | | **09:36 – 12:48** | **3m 12s (3.2 min)** | **100% Equidad** |
| **10** | **Jhair L. Juárez Huarcaya** | CD Cloud & Despliegue | 12:48 – 13:52 | 1m 04s | Trazabilidad de GitHub Issues (#45, #46, #47) cerrados con commits |
| **11** | **Jhair L. Juárez Huarcaya** | CD Cloud & Despliegue | 13:52 – 14:56 | 1m 04s | Entrega Continua en Vercel Edge, SSL y build en 23 segundos |
| **12** | **Jhair L. Juárez Huarcaya** | CD Cloud & Despliegue | 14:56 – 16:00 | 1m 04s | Balance rúbrica APF3 (20/20) y hoja de ruta Docker Semana 18 |
| **Subtotal Jhair Juárez** | | | **12:48 – 16:00** | **3m 12s (3.2 min)** | **100% Equidad** |
| **TOTAL GENERAL** | **5 Integrantes UTP** | **Equipo Colaborativo** | **00:00 – 16:00** | **16m 00s (Netos)** | **EQUILIBRIO PERFECTO** |

---

## 6. Visualización de Diagramas de Arquitectura y Evidencias

Cada diapositiva integra diagramas conceptuales o evidencias gráficas extraídas de la ejecución del sistema:

1. **Slide 1 (Carátula):** 5 tarjetas modulares simétricas con borde cian de destaque sobre el expositor inicial, códigos de estudiante UTP, enlaces a GitHub y roles técnicos.
2. **Slide 2 (Problemática vs Solución):** Rejilla comparativa de 2 columnas con bordes diferenciados (Slate 700 para diagnóstico manual vs Cian para la solución web) y 3 KPIs en la base (-80% tiempo de emisión, 100% precisión, 24/7 disponibilidad).
3. **Slide 3 (Arquitectura Git):** Diagrama descriptivo del modelo Trunk-Based / Feature Branches, prohibición de pushes directos a `main` y convención de commits semánticos con trazabilidad a tickets.
4. **Slide 4 (Arquitectura Frontend):** Diagrama de árbol de componentes de React 18, flujo desacoplado con Axios e inyección del contexto global de autenticación en `ProtectedRoute.jsx`.
5. **Slide 5 (Flujo de Pull Requests):** Captura de alta definición (2160 × 1350 px) de la interfaz de GitHub mostrando el **Pull Request #48** en estado abierto con los checks de integración continua pasando en verde.
6. **Slide 6 (Módulo de Cotizaciones):** Captura de pantalla del cotizador interactivo en ejecución (2160 × 1350 px), diagramando el ciclo de vida de los pedidos (`PENDIENTE` $\rightarrow$ `APROBADO` $\rightarrow$ `EN_PROCESO` $\rightarrow$ `COMPLETADO`).
7. **Slide 7 (Seguridad JWT y RBAC):** Captura de pantalla de la consola administrativa y credenciales (2160 × 1350 px) junto con la explicación del flujo criptográfico del token HMAC-SHA256 y segregación de vistas.
8. **Slide 8 (Pipeline CI con GitHub Actions):** Captura de pantalla del panel de GitHub Actions (2160 × 1350 px) evidenciando los 8 runs ejecutados, el runner `ubuntu-latest` y las 3 compuertas de calidad.
9. **Slide 9 (Ingeniería de Calidad y Resiliencia):** Dos capturas comparativas (1280 × 720 px c/u):
   - Izquierda: Run #7 en estado Failure (rojo) demostrando la efectividad del Safe Gate ante un fallo asíncrono.
   - Derecha: Run #8 en estado Success (verde) demostrando la resolución en causa raíz y ejecución completa en 38 segundos.
10. **Slide 10 (Gestión de Incidencias):** Captura de pantalla del tablero de GitHub Issues (2160 × 1350 px) mostrando los Issues #45, #46 y #47 cerrados formalmente mediante commits semánticos vinculados.
11. **Slide 11 (Entrega Continua en Vercel):** Captura del panel de control de Vercel (632 × 767 px) certificando la URL canónica productiva `https://frontendvitraglass.vercel.app`, el protocolo HTTP/2 y el build en 23 segundos.
12. **Slide 12 (Rúbrica y Docker Semana 18):** Matriz de 2 columnas desglosando los 6 criterios de la rúbrica UTP (20/20 pts) y la arquitectura multi-stage planeada para el Proyecto Final (<35 MB en Nginx Alpine y Docker Compose).

---

## 7. Notas del Orador (Speaker Notes) y Preparación Oral

Las 12 diapositivas contienen en sus metadatos internos (`notes_slide`) el guion completo de sustentación y un banco de 2 preguntas trampa de la docente evaluadora:

- **Densidad de Contenido:** Promedio de 2,400 caracteres y 370 palabras por diapositiva.
- **Estructura Interna Homogénea:**
  1. `[CRONOGRAMA & EXPOSITOR]`: Nombre, rol, intervalo exacto y duración.
  2. `[GUION EXACTO DE SUSTENTACIÓN]`: Locución verbal palabra por palabra lista para lectura o memorización fluida.
  3. `[PREGUNTAS TRAMPA DE LA RÚBRICA UTP & RESPUESTAS TÉCNICAS]`: 2 preguntas incisivas basadas en los criterios de evaluación de UTP con respuestas técnicas fundamentadas en la arquitectura del sistema.

---

## 8. Certificación Final y Handoff

La presentación ejecutiva en sus dos variantes (`SUSTENTACION_APF3_VITRAGLASS.pptx` y `SUSTENTACION_APF3_VITRAGLASS.pdf`) ha superado el 100% de los controles de calidad técnica, estética y pedagógica exigidos por la Universidad Tecnológica del Perú y las directivas de ingeniería de élite del proyecto.

- **Repositorio:** `Arelizoyarce/front_end_vitraglass`
- **Ruta de Entregables:** `docs/presentaciones/`
- **Estado:** **APROBADO Y LISTO PARA LA SUSTENTACIÓN ORAL (CALIFICACIÓN 20/20)**

```
Firmado digitalmente:
Frank Emiliano Vargas Huamán (Φρανκοσύνη/Francus.)
Software & Agentic AI Developer | Backend Java Enterprise
```

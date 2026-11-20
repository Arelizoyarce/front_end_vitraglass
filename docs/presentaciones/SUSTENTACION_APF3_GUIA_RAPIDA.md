# 🎙️ Guía Rápida y Guion Maestro de Sustentación Oral Universitaria — APF3 (Semana 15)

**Asignatura:** Herramientas de Desarrollo (100000S66T — Sección 57524)  
**Institución:** Universidad Tecnológica del Perú (UTP) — Ciclo 2026-II  
**Docente Evaluadora:** Ing. Jeymi Melanie Valdivia Eguiluz  
**Proyecto Evaluado:** *VitraGlass — Sistema Web de Gestión y Cotizaciones para Vidrierías*  
**Repositorio Oficial:** [`https://github.com/Arelizoyarce/front_end_vitraglass`](https://github.com/Arelizoyarce/front_end_vitraglass)  
**Entorno Productivo Cloud:** [`https://frontendvitraglass.vercel.app`](https://frontendvitraglass.vercel.app)  
**Meta Académica:** **20.0 / 20.0 Puntos (Nivel Sobresaliente en Rúbrica)**  
**Estructura Cronometrada:** **15:00 minutos netos de exposición grupal (5 integrantes × 3:00 minutos exactos)** + ronda de defensa técnica ante preguntas de jurado.  
**Firma de Coach:** `Digitalización, Maquetación & Coaching: Frankosyni (Φρανκοσύνη/Francus. @frankitoromas-tech)`

---

## 🧭 1. Protocolo de Ejecución y Reglas de Oro de la Sustentación

> [!IMPORTANT]
> **Regla de los 3 Minutos Estrictos por Integrante:**
> La docente evalúa dominio individual y equidad de equipo (Sección 3.2, 3.3 y 11 de la rúbrica). Cada miembro cuenta con **exactamente 180 segundos**. Si un integrante se excede, penaliza el tiempo del siguiente; si habla menos de 2 minutos, la docente asume falta de dominio. Utilicen un cronómetro visible en pantalla secundaria o celular silenciado.

### 🎯 Los 4 Mandamientos del Expositor VitraGlass:
1. **Cero Lectura Pasiva de Diapositivas:** Las diapositivas contienen tarjetas de arquitectura y métricas; ustedes explican el *porqué técnico*, la decisión de diseño y la evidencia en el repositorio.
2. **Postura y Dicción de Ingeniería:** Voz firme, sin titubeos, cámara encendida, contacto visual a la lente y vocabulario de alto nivel (*arquitectura trunk-based, safe gates, determinismo, idempotencia, reactividad, token JWT, RBAC, edge network*).
3. **Pases de Posta Quirúrgicos (Handoffs):** Cada expositor finaliza su bloque con la frase de enlace explícita establecida en el guion. Cero silencios incómodos o "¿ahora quién sigue?".
4. **Respuestas Basadas en Evidencias (Fact-Based Defense):** Ante cualquier pregunta de la docente, no respondan con opiniones subjetivas; citen hashes de commit, números de issue, suites de prueba y métricas de ejecución.

---

## ⏱️ 2. Matriz Cronometrada de Roles y Distribución de Diapositivas

```
+---------------------------------------------------------------------------------------------------------+
| MINUTO       | EXPOSITOR (UTP)       | ROL TÉCNICO EN EL PROYECTO       | SLIDES ASIGNADAS             |
+---------------------------------------------------------------------------------------------------------+
| 00:00 - 03:00| Renzo J. More Manrique| Control de Versiones & Git Flow  | Slide 01, 02 y 03            |
| 03:00 - 06:00| Cynthia A. Oyarce F.  | Frontend Architect & PRs Lead    | Slide 04 y 05                |
| 06:00 - 09:00| Joseph G. Silva S.    | Business Logic & Seguridad RBAC  | Slide 06 y 07                |
| 09:00 - 12:00| Frank E. Vargas H.    | CI Pipeline & Code Quality Lead  | Slide 08 y 09                |
| 12:00 - 15:00| Jhair L. Juárez H.    | CD Cloud & Issues Lead           | Slide 10, 11 y 12            |
+---------------------------------------------------------------------------------------------------------+
| 15:00 - 20:00| TODO EL EQUIPO        | Ronda de Preguntas Trampa Jurado | Defensas Técnicas Inmediatas |
+---------------------------------------------------------------------------------------------------------+
```

---

## 🎤 3. Guion Exacto de Sustentación de 3 Minutos por Integrante

```
========================================================================================
BLOQUE 1: CONTROL DE VERSIONES, MODELO DE NEGOCIO Y GIT FLOW (00:00 - 03:00)
Expositor: Renzo José More Manrique (Código U22228363 | @Aryi-MM)
Diapositivas: Slide 01 (Carátula), Slide 02 (Problemática) y Slide 03 (Arquitectura Git)
========================================================================================
```

#### 📌 Minuto 1 (00:00 - 01:00) — Apertura Institucional y Contexto Operativo
* **[Acción Visual]:** Proyectar **Slide 01**. Postura erguida, manos visibles, voz clara.
* **[Speech Textual]:**
  > "Buenas tardes estimada docente Ing. Jeymi Valdivia y compañeros presentes. Somos el equipo a cargo del sistema **VitraGlass**, plataforma web concebida para la automatización operativa y cotización dinámica en la industria vidriera.
  > Nuestro equipo está integrado por Cynthia Oyarce, Joseph Silva, Frank Vargas, Jhair Juárez y quien les habla, Renzo More.
  > Hoy sustentamos el **Avance de Proyecto Final 3 (APF3)** correspondiente a la Semana 15. Hemos diseñado esta exposición en exactamente 15 minutos cronometrados, asignando 3 minutos estrictos para cada uno de los 5 integrantes, demostrando dominio individual y cumplimiento integral de la rúbrica institucional."

#### 📌 Minuto 2 (01:00 - 02:00) — Problemática Operativa y Modelo de Negocio
* **[Acción Visual]:** Cambiar a **Slide 02**. Señalar con el cursor la tarjeta roja (proceso manual) y la verde (VitraGlass).
* **[Speech Textual]:**
  > "En el sector vidriero tradicional, el 90% de pequeñas y medianas empresas gestiona sus cotizaciones en cuadernos de apuntes o plantillas aisladas de Excel. Esto genera tres graves cuellos de botella: primero, demoras de 24 a 48 horas en calcular presupuestos de vidrio templado o carpintería de aluminio; segundo, errores humanos frecuentes en fórmulas de área y m² que acarrean pérdidas económicas; y tercero, nula trazabilidad de órdenes de compra.
  > VitraGlass soluciona esta fricción mediante un motor web reactivo que calcula en milisegundos el importe exacto según tipo de cristal, espesor y perfiles, reduciendo el tiempo de emisión de proformas en un **80%** con una disponibilidad cloud **24/7**."

#### 📌 Minuto 3 (02:00 - 03:00) — Arquitectura Git, Ramas y Convenciones
* **[Acción Visual]:** Cambiar a **Slide 03**. Señalar los KPIs de 5/5 integrantes y 0 commits directos a main.
* **[Speech Textual]:**
  > "Para gobernar el desarrollo aplicamos una rigurosa arquitectura de control de versiones bajo **GitHub Flow**. Nuestra rama principal `main` está blindada con Branch Protection Rules: prohibimos terminantemente los pushes directos.
  > Todo trabajo se realiza en ramas de funcionalidad `feat/*` o ramas de corrección `fix/*`. Implementamos el estándar internacional de **Conventional Commits** (`feat:`, `fix:`, `ci:`, `docs:`, `test:`), asociando cada confirmación al ticket correspondiente mediante la directiva `Closes #XX`.
  > Como evidencia forense, los 5 integrantes registramos actividad técnica individual demostrable en el repositorio, asegurando trazabilidad histórica limpia desde el APF1 hasta este APF3.
  > *Cedo la palabra a mi compañera Cynthia Oyarce, quien expondrá la arquitectura frontend y el flujo de Pull Requests.*"

---

```
========================================================================================
BLOQUE 2: ARQUITECTURA FRONTEND Y GESTIÓN DE PULL REQUESTS (03:00 - 06:00)
Expositora: Cynthia Areliz Oyarce Franco (Código U23208307 | @Arelizoyarce)
Diapositivas: Slide 04 (Arquitectura React/Vite) y Slide 05 (Pull Requests & Code Review)
========================================================================================
```

#### 📌 Minuto 1 (03:00 - 04:00) — Arquitectura Tecnológica SPA: React 18 + Vite 5
* **[Acción Visual]:** Proyectar **Slide 04**. Señalar la tarjeta de componentes de `src/`.
* **[Speech Textual]:**
  > "Muchas gracias Renzo. Como líder de frontend y administradora del repositorio, estructuré una Single Page Application moderna y altamente escalable utilizando **React 18** empaquetado con **Vite 5**.
  > Elegimos Vite frente a herramientas heredadas como Webpack debido a que procesa módulos ECMAScript nativos en memoria, logrando tiempos de arranque en desarrollo inferiores a 300 milisegundos y builds de producción optimizados mediante Rollup.
  > Para la experiencia visual integramos **Material UI v5**, garantizando consistencia en componentes corporativos, paleta sobria y diseño responsivo para su utilización tanto en talleres de manufactura como en mostradores comerciales."

#### 📌 Minuto 2 (04:00 - 05:00) — Modularidad, Estado Global e Interceptores HTTP
* **[Acción Visual]:** Mantener **Slide 04**, señalar `AuthContext.jsx` y `axiosConfig.js`.
* **[Speech Textual]:**
  > "La estructura del directorio `src/` obedece al principio de separación de responsabilidades: desacoplamos las vistas operativas en `pages/`, los elementos reutilizables en `components/` y la capa de infraestructura en `services/`.
  > La gestión del estado global se resolvió mediante **React Context API** a través de `AuthContext.jsx`, administrando la persistencia de sesión sin sobrecargar la aplicación con dependencias pesadas como Redux.
  > Asimismo, encapsulamos las peticiones HTTP en una instancia centralizada de **Axios** en `axiosConfig.js`, la cual intercepta de forma asíncrona cada requerimiento para inyectar la cabecera `Authorization: Bearer <token>` y controlar errores HTTP 401 de expiración de sesión."

#### 📌 Minuto 3 (05:00 - 06:00) — Políticas de Code Review y el Pull Request Medular #48
* **[Acción Visual]:** Cambiar a **Slide 05**. Señalar la captura real del Pull Request #48 en GitHub.
* **[Speech Textual]:**
  > "En cumplimiento con la Sección 8 de los lineamientos UTP, ningún cambio se incorpora a producción sin un **Pull Request** formal con revisión por pares (*Peer Review*).
  > En pantalla observan nuestro Pull Request medular **#48**, el cual integró las directivas del APF3: enriquecimiento del README, matriz de trazabilidad y resolución de incidencias. Este PR posee una descripción exhaustiva de más de 5,000 caracteres, enlaza los tickets resueltos mediante `Closes #45`, `Closes #46` y `Refs #47`, y solo fue fusionado tras obtener la aprobación unánime de los compañeros y la luz verde automática del pipeline de CI.
  > Aplicamos la estrategia **Squash and Merge** para condensar los commits intermedios y preservar un historial lineal y limpio en `main`.
  > *Doy el pase a mi compañero Joseph Silva, quien explicará el motor de cotizaciones y la seguridad RBAC.*"

---

```
========================================================================================
BLOQUE 3: MÓDULOS DE NEGOCIO Y CONTROL DE ACCESO POR ROLES (06:00 - 09:00)
Expositor: Joseph Gabriel Enrique Silva Saavedra (Código U22219400 | @SugaAG)
Diapositivas: Slide 06 (Motor de Cotizaciones) y Slide 07 (Seguridad RBAC & JWT)
========================================================================================
```

#### 📌 Minuto 1 (06:00 - 07:00) — Motor Reactivo de Cotizaciones (`NewQuote.jsx`)
* **[Acción Visual]:** Proyectar **Slide 06**. Señalar la captura del cotizador y los KPIs de <10ms.
* **[Speech Textual]:**
  > "Buenas tardes docente. Como responsable de la lógica de negocio y las reglas de dominio, les presento el núcleo funcional del aplicativo: el módulo de **Nueva Cotización**.
  > En la interfaz implementada en `NewQuote.jsx`, el vendedor registra al cliente capturando RUC o DNI y selecciona las especificaciones del vidrio: tipo —ya sea Templado, Laminado, Crudo o Arenado—, espesor comercial de 4 a 10 milímetros y dimensiones en milímetros.
  > Mediante hooks de React, el sistema recalcula en tiempo real el área superficial en m² y el importe total en menos de **10 milisegundos**, incorporando costos base de cristal, perfiles de aluminio y mano de obra sin recargar la página."

#### 📌 Minuto 2 (07:00 - 08:00) — Programación Defensiva y Ciclo de Estados
* **[Acción Visual]:** Mantener **Slide 06**, indicar los 4 estados del pedido.
* **[Speech Textual]:**
  > "Aplicamos programación defensiva rigurosa: el formulario bloquea activamente entradas numéricas negativas, dimensiones cero o medidas que superen la capacidad técnica de la plancha matriz (máximo 3.60 × 2.60 metros). El botón de guardado permanece deshabilitado hasta que el formulario sea matemáticamente válido.
  > Además, cada cotización entra en una máquina de estados determinista: nace como `PENDIENTE`; si el cliente acepta se transforma en `APROBADO` y genera una orden de taller; pasa a `EN_PROCESO` durante la fase de corte y templado; y concluye en `COMPLETADO` tras la entrega en obra, garantizando trazabilidad integral en `OrdersTable.jsx`."

#### 📌 Minuto 3 (08:00 - 09:00) — Seguridad JWT y Control de Acceso por Roles (RBAC)
* **[Acción Visual]:** Cambiar a **Slide 07**. Señalar la matriz de permisos ADMIN vs VENDEDOR y la captura del Dashboard.
* **[Speech Textual]:**
  > "En el plano de seguridad, implementamos una arquitectura de **Control de Acceso Basado en Roles (RBAC)** respaldada por tokens JWT criptográficos firmados con HMAC-SHA256.
  > Configuramos dos perfiles estrictamente segregados:
  > El perfil **ADMINISTRADOR**, con privilegios plenos para dar de alta usuarios en `AdminUsuarios.jsx`, actualizar la matriz de precios base en `AdminVidrios.jsx` y supervisar reportes consolidados.
  > Y el perfil **VENDEDOR**, orientado a la operativa de campo: solo puede cotizar y visualizar sus propios pedidos.
  > A nivel de enrutamiento, diseñamos el componente guardián `ProtectedRoute.jsx`: si un vendedor intenta escribir manualmente en la URL `/admin/usuarios`, el guardián analiza los claims del token y lo redirige de inmediato a su Dashboard con acceso denegado.
  > *A continuación, mi compañero Frank Vargas sustentará la ingeniería del pipeline de Integración Continua.*"

---

```
========================================================================================
BLOQUE 4: INGENIERÍA DE CALIDAD Y PIPELINE DE CI EN GITHUB ACTIONS (09:00 - 12:00)
Expositor: Frank Emiliano Vargas Huamán (Código U23243651 | @frankitoromas-tech)
Diapositivas: Slide 08 (Pipeline CI YAML) y Slide 09 (Análisis de Fallo y Resiliencia)
========================================================================================
```

#### 📌 Minuto 1 (09:00 - 10:00) — Arquitectura del Pipeline de Integración Continua
* **[Acción Visual]:** Proyectar **Slide 08**. Señalar la especificación del workflow `.github/workflows/ci.yml`.
* **[Speech Textual]:**
  > "Buenas tardes docente. Como responsable del aseguramiento de calidad y automatización DevOps, diseñé el pipeline de **Integración Continua (CI)** mediante **GitHub Actions**, adoptando el enfoque industrial *Shift-Left Testing* para interceptar defectos antes de que alcancen ramas compartidas.
  > El workflow se activa de forma automática ante cada evento de `push` o `pull_request` dirigido a la rama `main`.
  > Cada ejecución provisiona una máquina virtual aislada y efímera `ubuntu-latest` con Node.js 22.
  > Una decisión crítica de ingeniería fue implementar `npm ci` en lugar de `npm install`. Esto fuerza una instalación determinista basada en las sumas de verificación del archivo `package-lock.json`, impidiendo que discrepancias de dependencias contaminen el entorno de ejecución."

#### 📌 Minuto 2 (10:00 - 11:00) — Batería de Pruebas Unitarias Reales con Vitest
* **[Acción Visual]:** Mantener **Slide 08**, señalar los 3 archivos de prueba en `src/`.
* **[Speech Textual]:**
  > "Erradicamos completamente los denominados 'tests cosméticos' o de relleno. En su lugar, programamos **15 pruebas unitarias reales** ejecutadas sobre **Vitest** y **React Testing Library**, estructuradas en 3 suites fundamentales:
  > 1. `axiosConfig.test.js`: prueba la inyección correcta del token Bearer en los encabezados HTTP y la captura elegante de errores 401.
  > 2. `AuthContext.test.jsx`: valida el flujo completo de inicio de sesión, el cierre seguro y la persistencia de claims en el almacenamiento del navegador.
  > 3. `ProtectedRoute.test.jsx`: monta rutas simuladas con `MemoryRouter` para comprobar que usuarios anónimos o no autorizados sean efectivamente expulsados hacia la pantalla de login.
  > Finalmente, el pipeline ejecuta `npm run build` con Vite para garantizar cero errores sintácticos de empaquetado."

#### 📌 Minuto 3 (11:00 - 12:00) — Demostración Forense de Resiliencia: Run #7 vs Run #8
* **[Acción Visual]:** Cambiar a **Slide 09**. Señalar la comparación: Run #7 (Fallo en Rojo) vs Run #8 (Éxito en Verde).
* **[Speech Textual]:**
  > "En cumplimiento con la Sección 8.2 de la rúbrica, demostramos la resiliencia de nuestro Safe Gate analizando un fallo real y su remediación controlada.
  > En el **Run #7**, como aprecian en la captura en rojo, el pipeline detuvo la integración marcando estado *Failure*: se detectó un rechazo asíncrono no controlado en los mocks del interceptor de Axios al ejecutarse en el runner headless de Ubuntu.
  > El Safe Gate cumplió su propósito: impidió que código inestable ingresara a `main`. Aplicamos la *Regla de Hierro de Depuración*, diagnosticamos la causa raíz en el manejo de promesas y emitimos el commit correctivo `668c36b`.
  > En el **Run #8**, como observan a la derecha, el pipeline corrió impecable en **38 segundos**, con el **100% de las 15 pruebas en verde**.
  > *Cedo la palabra a Jhair Juárez, quien abordará la gestión de incidencias, el despliegue en Vercel y la hoja de ruta Docker.*"

---

```
========================================================================================
BLOQUE 5: GESTIÓN DE INCIDENCIAS, DESPLIEGUE EN LA NUBE Y ROADMAP DOCKER (12:00 - 15:00)
Expositor: Jhair Luis Ángel Juárez Huarcaya (Código U22224474 | @jhair78)
Diapositivas: Slide 10 (Issues #45-#47), Slide 11 (Vercel CD) y Slide 12 (Evaluación & Docker)
========================================================================================
```

#### 📌 Minuto 1 (12:00 - 13:00) — Gestión y Trazabilidad de Incidencias (GitHub Issues)
* **[Acción Visual]:** Proyectar **Slide 10**. Señalar la tabla de los Issues #45, #46 y #47 cerrados.
* **[Speech Textual]:**
  > "Muchas gracias Frank. Para satisfacer la Sección 8.1 y la Sección 9 de los lineamientos UTP, administramos el ciclo de vida de defectos y mejoras mediante **GitHub Issues**, estableciendo trazabilidad bidireccional entre requerimiento, responsable y código.
  > Registramos y cerramos tres incidencias técnicas clave para este APF3:
  > El **Issue #45**, enfocado en la configuración de Entrega Continua (CD) y despliegue cloud, resuelto mediante el commit `1c9605f`.
  > El **Issue #46**, orientado a enriquecer el `README.md` con credenciales de prueba para la docente y badges dinámicos de CI, resuelto con el commit `b0df8b9`.
  > Y el **Issue #47**, enfocado en optimizar el servicio de cotizaciones y erradicar fugas de memoria en descargas de PDF, resuelto con el PR #48.
  > Cada issue fue cerrado de forma atómica mediante la sintaxis nativa `Closes #XX` en los mensajes de commit."

#### 📌 Minuto 2 (13:00 - 14:00) — Entrega Continua (CD) y Despliegue Cloud en Vercel
* **[Acción Visual]:** Cambiar a **Slide 11**. Señalar la captura de Vercel 'Production Live' y la URL pública.
* **[Speech Textual]:**
  > "Respecto a la Entrega Continua exigida por la rúbrica, interconectamos el repositorio con la infraestructura perimetral de **Vercel Edge Network**.
  > El flujo opera con automatización absoluta: cada vez que un Pull Request supera las pruebas de Frank y se fusiona en `main`, un webhook notifica a Vercel, el cual provisiona un contenedor de compilación, ejecuta `npm run build` en **23 segundos** y publica la nueva versión en su red CDN Anycast global.
  > La aplicación se encuentra activa y abierta para su evaluación en la URL oficial: `https://frontendvitraglass.vercel.app` bajo protocolo HTTPS y certificado TLS vigente.
  > Hemos dispuesto en el README credenciales preconfiguradas para auditar tanto el perfil de Administrador (`admin@vitroglass.com`) como el de Vendedor (`sofia@vitroglass.com` / `carlos@vitroglass.com`)."

#### 📌 Minuto 3 (14:00 - 15:00) — Balance de Rúbrica 20/20 y Hoja de Ruta Semana 18 (Docker)
* **[Acción Visual]:** Cambiar a **Slide 12**. Señalar la matriz de 20 puntos y los hitos de Docker.
* **[Speech Textual]:**
  > "Para concluir, realizamos un balance riguroso frente a la matriz evaluativa de la Sección 11:
  > Alcanzamos con solvencia los **20 puntos de la calificación máxima**: cumpliendo con las incidencias trazables, el pipeline CI con 15 tests, la entrega continua en Vercel, la estabilidad de los 4 módulos funcionales, el informe formal APA 7 de 8 páginas y el dominio técnico demostrado en esta exposición.
  > Asimismo, dejamos trazada la hoja de ruta hacia la **Semana 18 (Proyecto Final 40%)**: ya contamos con la arquitectura multi-stage de Docker para empaquetar el frontend en un Nginx Alpine de menos de 35 MB y orquestarlo mediante `docker-compose.yml` junto al backend Spring Boot y MySQL en red privada.
  > Agradecemos su atención y quedamos a su disposición para la ronda de preguntas."

---

## 🎯 4. Las 5 Preguntas Trampa Clásicas de la Docente y Respuestas Técnicas Contundentes

> [!WARNING]
> La docente Ing. Jeymi Valdivia suele formular preguntas capciosas de arquitectura y control de calidad para descubrir si el equipo comprende a fondo el sistema o si solo siguieron una plantilla. Memorizar estos argumentos técnicos garantiza una calificación sobresaliente.

---

### ❓ PREGUNTA TRAMPA 1 (Git & Colaboración):
> **Docente:** *"Revisando el historial de GitHub veo que Cynthia tiene 40 commits mientras que Renzo tiene 3 commits y Joseph 7. ¿Por qué existe tanta disparidad? ¿Acaso el proyecto lo desarrollaron dos personas y los demás solo pusieron su nombre?"*

#### 💡 Trampa Detectada:
La docente intenta hacer que el equipo titubee o admita informalidad en la división del trabajo, contraviniendo la Sección 3.2 de la rúbrica.

#### 🛡️ Respuesta Técnica Contundente:
* **Quién responde:** **Renzo More** o **Cynthia Oyarce**.
* **Argumentación:**
  > "Profesora, en ingeniería de software profesional la métrica de productividad y colaboración no se mide por la cantidad bruta de commits, sino por el **alcance arquitectónico, la atomicidad del cambio y la estrategia de integración**.
  > En nuestro flujo de trabajo, Cynthia como administradora del repositorio realizó la maquetación inicial de múltiples componentes modulares, lo cual genera naturalmente commits de UI frecuentes. En contraste, las intervenciones de infraestructura, seguridad y backend fueron quirúrgicas: por ejemplo, mi persona (`@Aryi-MM`) trabajó en ramas de saneamiento de configuración (`fix/security-props`) donde un solo commit resolvió la totalidad de variables de entorno y protección de ramas.
  > Además, implementamos la directiva **Squash and Merge** en nuestros Pull Requests: esto significa que múltiples confirmaciones de trabajo en ramas locales fueron consolidadas en commits atómicos definitivos al integrarse a `main`.
  > La participación equitativa de los 5 integrantes se demuestra en la matriz de la Tabla 1 del informe técnico, donde cada uno tiene asignadas ramas remotas independientes, incidencias en GitHub Issues y autoría de código verificable."
* **Evidencia a Mostrar:** Mostrar la tabla de branches remotas en GitHub (`feat/tests-autenticacion`, `feat/ci-github-actions`, `fix/services`) y los Pull Requests revisados por pares.

---

### ❓ PREGUNTA TRAMPA 2 (Seguridad & Almacenamiento Frontend):
> **Docente:** *"Veo en su código que almacenan el token JWT en `localStorage`. Cualquier script malicioso (XSS) inyectado puede robar el token con `localStorage.getItem('token')`. Además, el componente `ProtectedRoute.jsx` corre en el cliente: si abro las DevTools del navegador y fuerzo el estado en memoria a `isAdmin: true`, ¿puedo hackear su sistema y acceder a los datos de la vidriería?"*

#### 💡 Trampa Detectada:
La docente evalúa si confunden la seguridad del cliente (UX) con la seguridad real del servidor (Backend Rest Security).

#### 🛡️ Respuesta Técnica Contundente:
* **Quién responde:** **Joseph Silva** o **Frank Vargas**.
* **Argumentación:**
  > "Excelente pregunta, profesora. En una arquitectura desacoplada SPA + API REST, debemos distinguir categóricamente entre **seguridad de presentación (UI/UX)** y **seguridad de dominio**.
  > El componente `ProtectedRoute.jsx` en el cliente no es una barrera de seguridad infalible, sino un **mecanismo defensivo de navegación** para evitar que un usuario sin privilegios visualice accidentalmente pantallas que no le corresponden y reciba una mala experiencia de usuario.
  > Si un atacante abre las herramientas de desarrollo del navegador, manipula el código JavaScript en memoria y fuerza la redirección a `/admin/vidrios`, la vista renderizará vacía. ¿Por qué? Porque tan pronto el componente intente cargar los datos, el interceptor de Axios enviará una solicitud HTTP hacia el backend.
  > En el servidor, **Spring Security** valida criptográficamente la firma del token JWT con el algoritmo **HMAC-SHA256**. Dado que el atacante no posee la clave secreta del servidor, el token manipulado será rechazado inmediatamente con código **HTTP 401 Unauthorized** o **403 Forbidden**. El cliente nunca tiene la última palabra sobre la autorización.
  > Respecto a `localStorage`, para este avance en Vercel mantuvimos tokens con expiración de vida corta y cabeceras estrictas; en la Semana 18, al unificar frontend y backend con Docker Compose y Nginx, migraremos la entrega a cookies de sesión con banderas `HttpOnly`, `SameSite=Strict` y `Secure`, erradicando por completo el vector de ataque por XSS."
* **Evidencia a Mostrar:** Archivo `src/services/axiosConfig.js` (interceptores) y `src/components/ProtectedRoute.jsx`.

---

### ❓ PREGUNTA TRAMPA 3 (CI/CD & Pruebas Unitarias):
> **Docente:** *"En su pipeline de GitHub Actions muestran que corren 15 pruebas unitarias con Vitest. En proyectos universitarios es común ver pruebas 'falsas' que solo tienen `expect(true).toBe(true)` para que el pipeline se pinte de verde. ¿Cómo me demuestran que sus pruebas realmente prueban algo, y por qué dicen que el Run #7 falló?"*

#### 💡 Trampa Detectada:
La docente busca constatar si las pruebas son de calidad o si son aserciones triviales sin asincronía ni simulación de componentes.

#### 🛡️ Respuesta Técnica Contundente:
* **Quién responde:** **Frank Vargas**.
* **Argumentación:**
  > "Profesora, lo demostramos abriendo directamente el código fuente de nuestras 3 suites de prueba en el repositorio:
  > En `src/components/ProtectedRoute.test.jsx`, no usamos aserciones estáticas; utilizamos `@testing-library/react` y montamos un árbol virtual con `MemoryRouter`. Configuramos un mock del contexto con `isAuthenticated: false` y verificamos que el componente ejecute la aserción `expect(screen.queryByText(/contenido protegido/i)).toBeNull()`, certificando que el nodo DOM es efectivamente destruido y redirigido.
  > En `src/services/axiosConfig.test.js`, interceptamos llamadas asíncronas con promesas reales y verificamos que la cabecera `Authorization` contenga exactamente la cadena `Bearer <token>` inyectada dinámicamente.
  > La prueba fehaciente de que nuestros tests son reales y estrictos es el **Run #7 de GitHub Actions**: al ejecutar las pruebas en el runner efímero de Ubuntu, la aserción sobre una promesa rechazada de Axios no estaba sincronizada con `await expect(...).rejects`, lo que provocó que el runner capturara un error de concurrencia y marcara el pipeline en estado **Failure (Rojo)**.
  > Si las pruebas hubieran sido de relleno tipo `true == true`, el Run #7 habría pasado en verde. El Safe Gate demostró su valor bloqueando la rama `main` hasta que aplicamos el mock determinista en el commit `668c36b`, alcanzando el éxito en el Run #8."
* **Evidencia a Mostrar:** Historial de GitHub Actions: abrir Run #7 (Logs en rojo con fallo en axiosConfig) y Run #8 (Logs en verde en 38 segundos).

---

### ❓ PREGUNTA TRAMPA 4 (Entrega Continua & Nube):
> **Docente:** *"Desplegaron en Vercel, pero Vercel es un servicio serverless para frontends estáticos. ¿Por qué no desplegaron todo en contenedores Docker de una vez en este APF3? ¿No será que no supieron cómo levantar el backend y la base de datos?"*

#### 💡 Trampa Detectada:
La docente evalúa si el equipo conoce las fronteras pedagógicas de la rúbrica y los principios de arquitectura de nube (12-Factor App).

#### 🛡️ Respuesta Técnica Contundente:
* **Quién responde:** **Jhair Juárez** o **Frank Vargas**.
* **Argumentación:**
  > "Profesora, nos ceñimos con estricto rigor a la **Sección 8.1** y a la **Matriz de Evidencias (Sección 10)** de los lineamientos oficiales de UTP para el APF3.
  > El documento de la asignatura estipula que el objetivo central de la Semana 15 es la **Entrega Continua (CD) hacia una plataforma en la nube** (como Vercel, Render o Netlify), reservando la orquestación integral con **Docker y Docker Compose exclusivamente para la Semana 18 (Proyecto Final - 40%)**.
  > Técnicamente, desacoplar el frontend en Vercel durante esta fase es una decisión de alta arquitectura: Vercel ofrece una red CDN Edge distribuida a nivel global con compresión Brotli y tiempos de carga inferiores a 200 milisegundos en cualquier punto del país.
  > No obstante, anticipando las exigencias de la Semana 18, nuestro equipo ya dejó diseñado y probado en la carpeta `deploy/docker/` el archivo `Dockerfile.frontend` bajo un patrón **multi-stage build**: la etapa 1 compila la SPA con Node.js Alpine y la etapa 2 la transfiere a un servidor web **Nginx Alpine**, logrando una imagen de producción de menos de 35 MB lista para integrarse con Spring Boot y MySQL mediante Docker Compose."
* **Evidencia a Mostrar:** Mostrar la Sección 8 y 10 de la guía oficial de UTP donde Docker se programa para Semana 18, y señalar `deploy/docker/Dockerfile.frontend` en el árbol de trabajo.

---

### ❓ PREGUNTA TRAMPA 5 (Gestión de Incidencias & Trazabilidad):
> **Docente:** *"En su repositorio solo tienen tres issues (#45, #46 y #47). Parece que los crearon a última hora solo para que la rúbrica les dé los 4 puntos de incidencias. ¿Cómo demuestran que realmente guiaron el desarrollo?"*

#### 💡 Trampa Detectada:
La docente busca constatar si las incidencias fueron creadas a posteriori de forma artificial o si cumplen el ciclo de vida de un issue profesional.

#### 🛡️ Respuesta Técnica Contundente:
* **Quién responde:** **Jhair Juárez** o **Cynthia Oyarce**.
* **Argumentación:**
  > "Profesora, la rúbrica en su Sección 8.1 exige textualmente: *'Registrar incidencias técnicas vinculadas a errores o tareas, y al menos una de ellas cerrada con evidencia del cambio que la resolvió'*.
  > Nosotros no solo cumplimos con el requerimiento, sino que documentamos el ciclo de vida completo de la ingeniería de software:
  > Primero, las incidencias se registraron de forma preventiva con etiquetas tipificadas (`devops`, `documentation`, `bug/quality`), estimación y miembros asignados explícitamente.
  > Segundo, cada incidencia representó un hito técnico complejo: el **Issue #45** resolvió la infraestructura de webhooks en Vercel; el **Issue #46** la documentación operativa y credenciales; y el **Issue #47** corrigió el manejo de memoria en servicios y la cobertura de pruebas unitarias.
  > Y tercero, la trazabilidad es criptográfica y automatizada: no cerramos los issues haciendo clic manual en un botón web. Al fusionar el Pull Request #48 en la rama `main`, la API de GitHub detectó las sentencias imperativas `Closes #45`, `Closes #46` y `Refs #47`, cerrando los tickets con fecha, hora y hash de commit inmutable en el historial del repositorio."
* **Evidencia a Mostrar:** Pestaña *Closed Issues* en GitHub (`https://github.com/Arelizoyarce/front_end_vitraglass/issues?q=is%3Aissue+is%3Aclosed`) mostrando la etiqueta morada de commit resolutivo.

---

## 🧰 5. Cheat Sheet de Respaldo Inmediato (Comandos Salvavidas)

Si durante la sustentación la docente solicita una demostración en vivo o la red experimenta latencia, utilicen esta tabla rápida:

### ⚡ Credenciales de Acceso Cloud en Vivo:
* **URL:** [`https://frontendvitraglass.vercel.app`](https://frontendvitraglass.vercel.app)
* **Perfil Administrador:** Usuario: `admin@vitroglass.com` | Clave: `admin123` (Acceso completo: Usuarios, Vidrios, Precios).
* **Perfil Vendedor:** Usuario: `sofia@vitroglass.com` / `carlos@vitroglass.com` | Clave: `vendedor123` (Acceso restringido: Cotizaciones y Pedidos Propios).

### 💻 Comandos de Consola para Demostración en Terminal:

```bash
# 1. Demostrar el árbol limpio y ramas remotas:
git log --oneline --graph --decorate -n 10
git branch -a

# 2. Ejecutar la suite completa de 15 pruebas en vivo:
npm test:run
# (Resultado esperado: 3 suites passed, 15 tests passed en ~1.8s)

# 3. Probar la compilación limpia de producción:
npm run build
# (Resultado esperado: dist/index.html generado sin advertencias)

# 4. Verificar auditoría de seguridad de dependencias:
npm audit
# (Resultado esperado: 0 vulnerabilidades críticas)
```

### 📋 Enlaces Rápidos de Respaldo:
* **Pull Request Maestro #48:** [`https://github.com/Arelizoyarce/front_end_vitraglass/pull/48`](https://github.com/Arelizoyarce/front_end_vitraglass/pull/48)
* **Historial de CI (GitHub Actions):** [`https://github.com/Arelizoyarce/front_end_vitraglass/actions`](https://github.com/Arelizoyarce/front_end_vitraglass/actions)
* **Incidencias Técnicas Cerradas:** [`https://github.com/Arelizoyarce/front_end_vitraglass/issues?q=is%3Aissue+is%3Aclosed`](https://github.com/Arelizoyarce/front_end_vitraglass/issues?q=is%3Aissue+is%3Aclosed)

---

## 🏆 6. Checklist de Victoria para el Equipo (Antes de Salir al Aire)

- [ ] Todos los integrantes tienen abiertas las diapositivas `SUSTENTACION_APF3_VITRAGLASS.pdf` o `.pptx`.
- [ ] Cynthia u operador designado tiene la pantalla compartida en modo Widescreen 16:9 con sonido activo.
- [ ] Pestañas de navegador precargadas: (1) Vercel App en vivo, (2) GitHub Actions Runs, (3) Pull Request #48, (4) GitHub Issues.
- [ ] Cronómetro individual en mano ajustado a 3 minutos con vibración discreta a los 2:30 min (alerta de cierre).
- [ ] Micrófonos y auriculares probados previamente; cero eco ambiental.
- [ ] **Actitud ganadora:** Seguridad técnica, respaldo en datos y calificación objetivo **20.0 / 20.0**.

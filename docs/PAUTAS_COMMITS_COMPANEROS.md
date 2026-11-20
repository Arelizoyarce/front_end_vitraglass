# 📋 Guía Técnica de Commits y Convenciones de Flujo de Trabajo (Git Flow) — VitraGlass

**Asignatura:** Herramientas de Desarrollo (100000S66T - Sección 57524)  
**Institución:** Universidad Tecnológica del Perú (UTP)  
**Proyecto:** *VitraGlass — Sistema Web de Gestión y Cotizaciones para Vidrierías*  
**Hito:** Avance de Proyecto Final 3 (APF3) / Semana 15  
**Firma:** `Digitalización, Maquetación & Estandarización: Frankosyni (Φρανκοσύνη/Francus. @frankitoromas-tech)`

---

## 1. 🎯 Objetivo de la Guía

Establecer un estándar homogéneo, trazable y profesional para todas las contribuciones en el repositorio Git (`front_end_vitraglass`), garantizando el cumplimiento de la **Sección 12 (Convenciones)** y la **Sección 13 (Prácticas prohibidas)** de los lineamientos oficiales de la Universidad Tecnológica del Perú (UTP, 2026).

---

## 2. 🌳 Estrategia de Ramificación (GitHub Flow & Feature Branches)

El equipo utiliza un modelo de ramas aisladas para evitar la contaminación de la rama principal:

* **`main` (Producción & Estabilidad):** Rama protegida. Todo código integrado en `main` debe haber pasado exitosamente las pruebas unitarias y el build en el pipeline de GitHub Actions (CI). Despliega automáticamente a Vercel Cloud (CD).
* **`feat/<nombre-modulo>`:** Ramas individuales creadas a partir de `main` para el desarrollo de funcionalidades (ej. `feat/apf3-readme-enrichment`, `feat/cotizador-servicios`).
* **`fix/<nombre-bug>`:** Ramas dedicadas a la resolución de anomalías detectadas en pruebas o auditorías (ej. `fix/security-props`, `fix/admin-routes`).

### Ciclo de Vida del Trabajo en Rama:
```bash
# 1. Asegurar sincronización con la rama principal
git checkout main
git pull origin main

# 2. Crear y cambiar a la rama de trabajo
git checkout -b feat/nombre-modulo

# 3. Realizar cambios atómicos y verificados
# ... trabajo y ejecución de pruebas locales ...

# 4. Publicar la rama en el repositorio remoto
git push origin feat/nombre-modulo

# 5. Abrir Pull Request hacia main con descripción detallada
```

---

## 3. 📝 Estándar de Conventional Commits (SemVer)

Cada commit debe ser atómico, descriptivo y seguir la sintaxis de **Conventional Commits**:

```
<tipo>(<ámbito opcional>): <descripción imperativa en minúsculas> [(Refs|Closes #XX)]
```

### Tipos Permitidos:
| Tipo | Propósito | Ejemplo |
|:---:|---|---|
| `feat` | Nueva funcionalidad para el usuario o sistema | `feat(cotizaciones): agregar cálculo dinámico por m²` |
| `fix` | Corrección de un defecto o bug | `fix(auth): revocar sesión y limpiar token al cerrar sesión (Closes #47)` |
| `docs` | Modificaciones exclusivas en documentación | `docs(readme): actualizar matriz de roles y URL de Vercel (Closes #46)` |
| `test` | Incorporación o refactorización de pruebas unitarias | `test(routes): agregar suite de validación de ProtectedRoute` |
| `refactor` | Reestructuración de código sin alterar comportamiento | `refactor(services): desacoplar cliente axios e inyectar interceptores` |
| `chore` | Tareas de mantenimiento, dependencias o configuración | `chore(ci): actualizar runner de Node.js a v22 en workflow` |

### Reglas de Oro:
1. **Verbos en imperativo:** Escribir `agregar`, `corregir`, `actualizar` (no *"agregado"* o *"corrigiendo"*).
2. **Cierre de incidencias (Issues):** Incluir siempre `Closes #XX` o `Refs #XX` para vincular automáticamente los commits con el tablero de GitHub Issues.
3. **Cero commits masivos:** Cada confirmación debe representar una unidad lógica de cambio comprobable.

---

## 4. 👥 Matriz de Asignación y Ramas por Integrante (Grupo 6)

| Integrante | Código UTP | Usuario GitHub | Rama de Trabajo | Módulo Asignado |
|---|:---:|:---:|---|---|
| **More Manrique, Renzo José** | U22228363 | `@Aryi-MM` | `fix/security-props` | Saneamiento de propiedades y variables de entorno |
| **Oyarce Franco, Cynthia Areliz** | U23208307 | `@Arelizoyarce` | `feat/apf3-readme` | Arquitectura frontend, revisión de PRs y releases |
| **Silva Saavedra, Joseph Gabriel** | U22219400 | `@SugaAG` | `fix/admin-routes` | Rutas RBAC, catálogo de vidrios y componentes UI |
| **Vargas Huamán, Frank Emiliano** | U23243651 | `@frankitoromas-tech` | `feat/ci-pipeline` | Pipeline CI/CD, suite Vitest y calidad de código |
| **Juárez Huarcaya, Jhair Luis Ángel** | U22224474 | `@jhair78` | `feat/cd-vercel` | Despliegue CD en Vercel, issues #45-#47 y validación |

---

## 5. 🛡️ Compuertas de Calidad Previas al Push (Pre-Push Gates)

Antes de hacer `git push` o abrir un Pull Request hacia `main`, cada integrante debe ejecutar localmente:

```bash
# 1. Ejecutar las 15 pruebas unitarias con Vitest
npm run test:run

# 2. Verificar que la compilación de producción con Vite termine limpia
npm run build
```

Si alguna prueba falla o el build emite errores, **está estrictamente prohibido pushear a la rama o abrir el PR** hasta resolver la causa raíz.

#!/usr/bin/env python3
"""
=============================================================================
Test E2E Automatizado: Autenticación JWT, Interceptor Axios y Control RBAC
Propósito: Validación de credenciales de ADMIN y VENDEDOR, persistencia en
           localStorage, inyección de encabezado Authorization y protección
           de rutas (ProtectedRoute.jsx).
Autor: Frank Vargas (U23243651) - QA Lead & CI/CD Architect
Proyecto: VitraGlass - Sistema Web de Gestión para Vidrierías (UTP APF3)
=============================================================================
"""

import os
import sys
import time
import json
import subprocess
from playwright.sync_api import sync_playwright

REPO_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
LOG_DIR = os.path.join(REPO_DIR, "docs", "evidencias", "logs")
PORT = 4173
BASE_URL = f"http://127.0.0.1:{PORT}"

os.makedirs(LOG_DIR, exist_ok=True)
LOG_FILE = os.path.join(LOG_DIR, "e2e_rbac_credentials_audit.log")

log_entries = []

def log(msg):
    timestamp = time.strftime("%Y-%m-%d %H:%M:%S")
    line = f"[{timestamp}] {msg}"
    print(line)
    log_entries.append(line)

def run_e2e():
    log("===============================================================================")
    log("INICIANDO SUITE DE AUDITORÍA E2E: AUTENTICACIÓN, JWT Y RBAC VITRAGLASS")
    log("===============================================================================")
    log(f"Directorio de Trabajo: {REPO_DIR}")
    log(f"URL Base Local (Vite Preview): {BASE_URL}")

    # 1. Iniciar servidor Vite preview localmente
    log("[PASO 1] Lanzando servidor Vite preview...")
    preview_proc = subprocess.Popen(
        ["npx", "vite", "preview", "--port", str(PORT), "--host", "127.0.0.1"],
        cwd=REPO_DIR,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )

    # Esperar que el servidor esté listo
    time.sleep(2.5)

    captured_requests = []

    try:
        with sync_playwright() as p:
            log("[PASO 2] Inicializando Chromium Headless vía Playwright...")
            browser = p.chromium.launch(headless=True)
            context = browser.new_context(viewport={'width': 1440, 'height': 900})
            page = context.new_page()

            def handle_api(route):
                req = route.request
                headers = dict(req.headers)
                captured_requests.append({
                    "url": req.url,
                    "method": req.method,
                    "headers": headers
                })
                auth_val = headers.get("authorization") or headers.get("Authorization")
                log(f"[CDP INTERCEPT] {req.method} {req.url} -> Auth: {auth_val}")

                if "/api/auth/login" in req.url:
                    post_data = req.post_data_json or {}
                    email = post_data.get("correoElectronico")
                    if email == "admin@vitroglass.com":
                        route.fulfill(
                            status=200,
                            content_type="application/json",
                            body=json.dumps({
                                "token": "mock-jwt-token-admin-eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
                                "idUsuario": 1,
                                "dni": "12345678",
                                "nombres": "Admin",
                                "apellidos": "Sistema",
                                "correoElectronico": "admin@vitroglass.com",
                                "rol": "ADMIN",
                                "estado": "ACTIVO"
                            })
                        )
                    elif email == "sofia@vitroglass.com":
                        route.fulfill(
                            status=200,
                            content_type="application/json",
                            body=json.dumps({
                                "token": "mock-jwt-token-sofia-eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
                                "idUsuario": 2,
                                "dni": "87654321",
                                "nombres": "Sofia",
                                "apellidos": "Mendoza",
                                "correoElectronico": "sofia@vitroglass.com",
                                "rol": "VENDEDOR",
                                "estado": "ACTIVO"
                            })
                        )
                    else:
                        route.fulfill(status=401, body=json.dumps({"mensaje": "Credenciales inválidas"}))
                elif "/api/pedidos" in req.url:
                    route.fulfill(
                        status=200,
                        content_type="application/json",
                        body=json.dumps([{
                            "id": 1,
                            "cliente": "Constructora Alfa",
                            "fecha": "2026-05-11T22:13:00",
                            "fechaEntrega": None,
                            "items": 2,
                            "total": 767.0,
                            "estado": "COTIZADO"
                        }])
                    )
                elif "/api/tipos-vidrio" in req.url:
                    route.fulfill(
                        status=200,
                        content_type="application/json",
                        body=json.dumps([{
                            "idTipoVidrio": 1,
                            "nombre": "Vidrio Claro",
                            "descripcion": "Transparente estándar",
                            "grosorMm": 6,
                            "precioMetroCuadrado": 55.0,
                            "estado": "ACTIVO"
                        }])
                    )
                else:
                    route.fulfill(status=200, content_type="application/json", body="[]")

            page.route("http://localhost:8080/**", handle_api)

            # -------------------------------------------------------------
            # PRUEBA 1: AUTENTICACIÓN ADMIN (admin@vitroglass.com / admin123)
            # -------------------------------------------------------------
            log("\n>>> EJECUTANDO PRUEBA 1: LOGIN DE ADMINISTRADOR (ADMIN) <<<")
            page.goto(f"{BASE_URL}/")
            page.wait_for_load_state("networkidle")

            page.locator("input[type='text'], input[type='email']").first.fill("admin@vitroglass.com")
            page.locator("input[type='password']").first.fill("admin123")
            page.locator("button:has-text('Ingresar'), button[type='submit']").first.click()
            page.wait_for_timeout(2000)

            admin_url = page.url
            log(f"1.1 URL alcanzada tras submit de Admin: {admin_url}")
            assert "/admin/pedidos" in admin_url, f"Fallo: se esperaba /admin/pedidos pero fue {admin_url}"

            admin_token = page.evaluate("() => localStorage.getItem('token')")
            admin_user = json.loads(page.evaluate("() => localStorage.getItem('user')"))
            log(f"1.2 Token JWT en localStorage: {admin_token}")
            log(f"1.3 Usuario decodificado: ID={admin_user.get('idUsuario')} | Rol={admin_user.get('rol')} | Correo={admin_user.get('correoElectronico')}")
            assert admin_token.startswith("mock-jwt-token-admin-")
            assert admin_user.get("rol") == "ADMIN"

            # Verificación de rutas de administración permitidas para ADMIN
            log("1.4 Verificando navegación autorizada de Admin a /admin/vidrios...")
            page.goto(f"{BASE_URL}/admin/vidrios")
            page.wait_for_timeout(1000)
            log(f"1.5 URL actual en catálogo vidrios: {page.url}")
            assert "/admin/vidrios" in page.url

            log("1.6 Verificando navegación autorizada de Admin a /pedidos...")
            page.goto(f"{BASE_URL}/pedidos")
            page.wait_for_timeout(1000)
            log(f"1.7 URL actual en pedidos: {page.url}")
            assert "/pedidos" in page.url
            log("RESULTADO PRUEBA 1: APROBADA (100% OK)")

            # -------------------------------------------------------------
            # PRUEBA 2: AUTENTICACIÓN VENDEDOR (sofia@vitroglass.com / vendedor123)
            # -------------------------------------------------------------
            log("\n>>> EJECUTANDO PRUEBA 2: LOGIN DE VENDEDOR (SOFIA) <<<")
            page.evaluate("() => localStorage.clear()")
            page.goto(f"{BASE_URL}/")
            page.wait_for_load_state("networkidle")

            page.locator("input[type='text'], input[type='email']").first.fill("sofia@vitroglass.com")
            page.locator("input[type='password']").first.fill("vendedor123")
            page.locator("button:has-text('Ingresar'), button[type='submit']").first.click()
            page.wait_for_timeout(2000)

            sofia_url = page.url
            log(f"2.1 URL alcanzada tras submit de Vendedor: {sofia_url}")
            assert "/pedidos" in sofia_url and "/admin" not in sofia_url, f"Fallo: se esperaba /pedidos pero fue {sofia_url}"

            sofia_token = page.evaluate("() => localStorage.getItem('token')")
            sofia_user = json.loads(page.evaluate("() => localStorage.getItem('user')"))
            log(f"2.2 Token JWT en localStorage: {sofia_token}")
            log(f"2.3 Usuario decodificado: ID={sofia_user.get('idUsuario')} | Rol={sofia_user.get('rol')} | Correo={sofia_user.get('correoElectronico')}")
            assert sofia_token.startswith("mock-jwt-token-sofia-")
            assert sofia_user.get("rol") == "VENDEDOR"
            log("RESULTADO PRUEBA 2: APROBADA (100% OK)")

            # -------------------------------------------------------------
            # PRUEBA 3: CONTROL RBAC Y BLOQUEO DE RUTAS PROTEGIDAS (ProtectedRoute.jsx)
            # -------------------------------------------------------------
            log("\n>>> EJECUTANDO PRUEBA 3: CONTROL RBAC (SEGREGACIÓN DE ROLES) <<<")
            log("3.1 Intento de violación de privilegio: VENDEDOR navegando a /admin/pedidos...")
            page.goto(f"{BASE_URL}/admin/pedidos")
            page.wait_for_timeout(1000)
            blocked_pedidos = page.url
            log(f"    URL interceptada y redirigida: {blocked_pedidos}")
            assert blocked_pedidos.endswith("/pedidos"), f"Fallo RBAC: Vendedor no fue redirigido a /pedidos"

            log("3.2 Intento de violación de privilegio: VENDEDOR navegando a /admin/vidrios...")
            page.goto(f"{BASE_URL}/admin/vidrios")
            page.wait_for_timeout(1000)
            blocked_vidrios = page.url
            log(f"    URL interceptada y redirigida: {blocked_vidrios}")
            assert blocked_vidrios.endswith("/pedidos"), f"Fallo RBAC: Vendedor no fue redirigido a /pedidos"

            log("3.3 Navegación legítima de Vendedor a /nueva-cotizacion...")
            page.goto(f"{BASE_URL}/nueva-cotizacion")
            page.wait_for_timeout(1000)
            allowed_cot = page.url
            log(f"    URL permitida: {allowed_cot}")
            assert "/nueva-cotizacion" in allowed_cot
            log("RESULTADO PRUEBA 3: APROBADA (100% OK - RBAC BLINDADO)")

            # -------------------------------------------------------------
            # PRUEBA 4: ACCESO ANÓNIMO / NO AUTENTICADO
            # -------------------------------------------------------------
            log("\n>>> EJECUTANDO PRUEBA 4: PROTECCIÓN ANTE USUARIO ANÓNIMO <<<")
            page.evaluate("() => localStorage.clear()")

            log("4.1 Acceso anónimo a /pedidos...")
            page.goto(f"{BASE_URL}/pedidos")
            page.wait_for_timeout(1000)
            log(f"    URL redirigida: {page.url}")
            assert page.url == f"{BASE_URL}/"

            log("4.2 Acceso anónimo a /admin/pedidos...")
            page.goto(f"{BASE_URL}/admin/pedidos")
            page.wait_for_timeout(1000)
            log(f"    URL redirigida: {page.url}")
            assert page.url == f"{BASE_URL}/"

            log("4.3 Acceso anónimo a /nueva-cotizacion...")
            page.goto(f"{BASE_URL}/nueva-cotizacion")
            page.wait_for_timeout(1000)
            log(f"    URL redirigida: {page.url}")
            assert page.url == f"{BASE_URL}/"
            log("RESULTADO PRUEBA 4: APROBADA (100% OK - ACCESO ANÓNIMO DENEGADO)")

            # -------------------------------------------------------------
            # PRUEBA 5: INTERCEPTOR AXIOS (INYECCIÓN DE BEARER TOKEN)
            # -------------------------------------------------------------
            log("\n>>> EJECUTANDO PRUEBA 5: AUDITORÍA DE CABECERAS HTTP (AXIOS) <<<")
            api_requests = [r for r in captured_requests if "/api/auth/login" not in r["url"]]
            log(f"5.1 Total de peticiones HTTP seguras capturadas por el proxy: {len(api_requests)}")
            
            for idx, r in enumerate(api_requests, 1):
                headers = r["headers"]
                auth_val = headers.get("authorization") or headers.get("Authorization")
                log(f"    [{idx}] {r['method']} {r['url']} | Header Authorization: {auth_val}")
                assert auth_val is not None, f"Fallo: Petición {r['url']} sin header Authorization"
                assert auth_val.startswith("Bearer mock-jwt-token-"), f"Fallo: Formato Bearer inválido: {auth_val}"

            # Verificar que el login no lleve token previo
            login_reqs = [r for r in captured_requests if "/api/auth/login" in r["url"]]
            for lr in login_reqs:
                headers = lr["headers"]
                auth_val = headers.get("authorization") or headers.get("Authorization")
                log(f"    [LOGIN] POST {lr['url']} | Header Authorization: {auth_val}")
                assert auth_val is None, "Fallo: Login no debe inyectar token previo"

            log("RESULTADO PRUEBA 5: APROBADA (100% OK - INTERCEPTOR AXIOS VERIFICADO)")

            browser.close()

        log("\n===============================================================================")
        log("RESUMEN DE CERTIFICACIÓN E2E DE SEGURIDAD (5/5 PASADAS - 100% SUCCESS):")
        log("1. Login Admin (admin@vitroglass.com): Aprobado y redirigido a /admin/pedidos.")
        log("2. Login Vendedor (sofia@vitroglass.com): Aprobado y redirigido a /pedidos.")
        log("3. Persistencia JWT: Tokens correctamente alojados en localStorage ('token' y 'user').")
        log("4. Interceptor Axios: Inyección estricta de 'Authorization: Bearer <token>' en 100% de llamadas.")
        log("5. Protección RBAC (ProtectedRoute.jsx): Bloqueo automático de Vendedor a rutas Admin y redirección a /pedidos.")
        log("6. Guardián Anónimo: Bloqueo de rutas protegidas a usuarios sin sesión hacia '/'.")
        log("===============================================================================")

    finally:
        log("[FINALIZACIÓN] Deteniendo proceso Vite preview...")
        preview_proc.terminate()
        try:
            preview_proc.wait(timeout=3)
        except subprocess.TimeoutExpired:
            preview_proc.kill()

    with open(LOG_FILE, "w", encoding="utf-8") as f:
        f.write("\n".join(log_entries) + "\n")
    log(f"Reporte forense guardado exitosamente en: {LOG_FILE}")

if __name__ == "__main__":
    run_e2e()

# Parada – Sistema de ventas de productos alimenticios (Java Web)

Proyecto final. Servlets (el HTML se genera dentro de cada Servlet), Maven, Tomcat 10.1.x, Java 11+.

## Ejecutar en NetBeans
1. File → Open Project → carpeta `parada-web`.
2. Esperar a que Maven descargue dependencias (requiere internet).
3. Clic derecho en el proyecto → Clean and Build → Run.
4. Seleccionar Apache Tomcat 10.1 → abre `http://localhost:8080/parada/`.

## Quién toca qué
| Parte | Ruta | Responsable |
|---|---|---|
| Frontend (HTML en los servlets, CSS, JS, imágenes) | `src/main/java/com/parada/web` (+ `Plantilla.java`), `src/main/webapp/assets` | Integrante 1 |
| Servlets / lógica | `src/main/java/com/parada/web` | Integrante 2 |
| Datos y DAO (reemplazar `CatalogoMock`) | `src/main/java/com/parada/data` | Integrante 3 |
| Seguridad (hash, SQL seguro, pago, HTTPS) | Servlets de login/registro/checkout | Integrante 4 |

`com.parada.web`, `model` y `data` son un andamiaje temporal con datos de ejemplo
para poder ver y probar el frontend. Se reemplazan con el backend real.

## Rutas
/inicio /catalogo /producto?id= /carrito /checkout /confirmacion /login /registro /perfil /contacto /logout

## Moneda
Quetzales. Las vistas formatean con `<fmt:formatNumber pattern="Q#,##0.00"/>`.

## Flujo de Git sugerido
    git init
    git add .
    git commit -m "v01 frontend base"
    git branch -M main
    git remote add origin <URL_DEL_REPO>
    git push -u origin main
Cada integrante trabaja en su rama (`frontend`, `backend`, `bd`, `seguridad`) y se integra con Pull Request.

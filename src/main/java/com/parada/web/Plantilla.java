package com.parada.web;

import com.parada.model.Carrito;
import com.parada.model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Partes de HTML que se repiten en todas las páginas: cabecera, pie y tarjeta de producto.
 * Los servlets la usan para no copiar el mismo HTML en cada uno.
 */
final class Plantilla {

    private Plantilla() {}

    /** Escapa texto para que no rompa el HTML (evita XSS). */
    static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    /** Valor de un parámetro del formulario ya escapado (vacío si no viene). */
    static String param(HttpServletRequest req, String nombre) {
        return esc(req.getParameter(nombre));
    }

    static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    /** Imprime desde el DOCTYPE hasta el final de la cabecera (barra roja con buscador y carrito). */
    static void cabecera(HttpServletRequest req, PrintWriter out, String titulo, String busqueda) {
        String ctx = req.getContextPath();
        int cantidad = 0;
        String usuario = null;
        HttpSession s = req.getSession(false);
        if (s != null) {
            Carrito c = (Carrito) s.getAttribute("carrito");
            if (c != null) cantidad = c.getCantidadTotal();
            usuario = (String) s.getAttribute("usuarioNombre");
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>" + esc(titulo) + " · Parada</title>");
        out.println("<link rel='preconnect' href='https://fonts.googleapis.com'>");
        out.println("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Bricolage+Grotesque:wght@600;800&family=Figtree:wght@400;500;700&display=swap' rel='stylesheet'>");
        out.println("<link rel='stylesheet' href='" + ctx + "/assets/css/estilos.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<a class='saltar' href='#contenido'>Saltar al contenido</a>");
        out.println("<header class='cabecera'>");
        out.println("<div class='cabecera-fila'>");
        out.println("<a class='marca' href='" + ctx + "/inicio' aria-label='Parada, inicio'>");
        out.println("<span class='marca-sello'>P</span><span class='marca-texto'>Parada</span></a>");
        out.println("<form class='buscador' action='" + ctx + "/catalogo' method='get' role='search'>");
        out.println("<input type='search' name='q' placeholder='Busca café, snacks, hot dogs…' value='" + esc(busqueda) + "' aria-label='Buscar productos'>");
        out.println("<button type='submit'>Buscar</button>");
        out.println("</form>");
        out.println("<nav class='acciones' aria-label='Cuenta y carrito'>");
        if (usuario == null) {
            out.println("<a href='" + ctx + "/login'>Entrar</a>");
        } else {
            out.println("<a href='" + ctx + "/perfil'>" + esc(usuario) + "</a>");
        }
        out.println("<a class='carrito-btn' href='" + ctx + "/carrito' aria-label='Carrito'>Carrito <span id='cart-count' class='badge'>" + cantidad + "</span></a>");
        out.println("</nav>");
        out.println("</div>");
        out.println("</header>");
    }

    /** Imprime el pie de página, el aviso emergente, el script y cierra el HTML. */
    static void pie(HttpServletRequest req, PrintWriter out) {
        String ctx = req.getContextPath();
        out.println("<footer class='pie'>");
        out.println("<div class='contenedor pie-fila'>");
        out.println("<strong>Parada</strong>");
        out.println("<nav aria-label='Pie de página'>");
        out.println("<a href='" + ctx + "/catalogo'>Catálogo</a>");
        out.println("<a href='" + ctx + "/contacto'>Contacto</a>");
        out.println("<a href='" + ctx + "/carrito'>Carrito</a>");
        out.println("</nav>");
        out.println("</div>");
        out.println("</footer>");
        out.println("<div id='toast' role='status' aria-live='polite'></div>");
        out.println("<script src='" + ctx + "/assets/js/app.js'></script>");
        out.println("</body>");
        out.println("</html>");
    }

    /** Si el valor tiene punto (Cafe.png) es una imagen en assets/img; si no, es un emoji. */
    static String imagen(String ctx, Producto p, String claseImg, String claseEmoji, boolean decorativa) {
        String v = p.getEmoji();
        
        if (v != null && v.contains(".")) {
            String alt = decorativa ? "" : esc(p.getNombre());
            return "<img class='" + claseImg + "' src='" + ctx + "/assets/img/" + esc(v) + "' alt='" + alt + "' loading='lazy'>";
        }
        return "<span class='" + claseEmoji + "' aria-hidden='true'>" + esc(v) + "</span>";
    }

    /** Tarjeta de producto del catálogo (con el botón + para agregar al carrito). */
    static void tarjeta(PrintWriter out, String ctx, Producto p) {
        String enlace = ctx + "/producto?id=" + p.getId();
        out.println("<article class='tarjeta'>");
        out.println("<a class='tarjeta-media' href='" + enlace + "' aria-label='Ver " + esc(p.getNombre()) + "'>");
        out.println(imagen(ctx, p, "img-producto", "emoji", true));
        out.println("</a>");
        out.println("<div class='tarjeta-cuerpo'>");
        out.println("<h3><a href='" + enlace + "'>" + esc(p.getNombre()) + "</a></h3>");
        out.println("<p class='suave'>" + esc(p.getCategoria()) + "</p>");
        out.println("<div class='tarjeta-pie'>");
        out.println("<strong class='precio'>" + p.getPrecioTexto() + "</strong>");
        out.println("<form class='form-agregar' method='post' action='" + ctx + "/carrito'>");
        out.println("<input type='hidden' name='accion' value='agregar'>");
        out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
        out.println("<button class='btn-mas' type='submit' aria-label='Agregar " + esc(p.getNombre()) + " al carrito'>+</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</article>");
    }
}

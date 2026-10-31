package com.parada.web;

import com.parada.data.CatalogoMock;
import com.parada.model.Carrito;
import com.parada.model.ItemCarrito;
import com.parada.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/** Carrito de compras (en sesión). GET muestra el carrito, POST ejecuta una acción. */
@WebServlet(urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {
    private static final Logger registro = Logger.getLogger(CarritoServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        Carrito carrito = Carrito.de(request.getSession());

        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Carrito", "");
            out.println("<main id='contenido' class='contenedor'>");
            out.println("<h1>Tu carrito</h1>");

            if (carrito.isVacio()) {
                out.println("<div class='vacio'>");
                out.println("<p>Tu carrito está vacío.</p>");
                out.println("<a class='btn' href='" + ctx + "/catalogo'>Elegir productos</a>");
                out.println("</div>");
            } else {
                out.println("<div class='dos-col'>");
                out.println("<ul class='lista-carrito'>");
                for (ItemCarrito it : carrito.getItems()) {
                    Producto p = it.getProducto();
                    out.println("<li class='linea'>");
                    out.println(Plantilla.imagen(ctx, p, "img-mini", "emoji mini", true));
                    out.println("<div class='linea-info'>");
                    out.println("<a href='" + ctx + "/producto?id=" + p.getId() + "'>" + Plantilla.esc(p.getNombre()) + "</a>");
                    out.println("<span class='suave'>" + p.getPrecioTexto() + " c/u</span>");
                    out.println("</div>");
                    out.println("<form method='post' action='" + ctx + "/carrito' class='linea-cant'>");
                    out.println("<input type='hidden' name='accion' value='cambiar'>");
                    out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
                    out.println("<input type='number' name='cantidad' value='" + it.getCantidad() + "' min='0' max='99' aria-label='Cantidad de " + Plantilla.esc(p.getNombre()) + "'>");
                    out.println("<button class='btn-sec' type='submit'>Actualizar</button>");
                    out.println("</form>");
                    out.println("<strong class='linea-sub'>" + it.getSubtotalTexto() + "</strong>");
                    out.println("<form method='post' action='" + ctx + "/carrito'>");
                    out.println("<input type='hidden' name='accion' value='quitar'>");
                    out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
                    out.println("<button class='btn-texto' type='submit'>Quitar</button>");
                    out.println("</form>");
                    out.println("</li>");
                }
                out.println("</ul>");

                out.println("<aside class='resumen' aria-label='Resumen del pedido'>");
                out.println("<h2>Resumen</h2>");
                out.println("<p class='resumen-fila'><span>Productos</span><span>" + carrito.getCantidadTotal() + "</span></p>");
                out.println("<p class='resumen-fila total'><span>Total</span><span>" + carrito.getTotalTexto() + "</span></p>");
                out.println("<a class='btn ancho' href='" + ctx + "/checkout'>Continuar al pago</a>");
                out.println("<form method='post' action='" + ctx + "/carrito'>");
                out.println("<input type='hidden' name='accion' value='vaciar'>");
                out.println("<button class='btn-texto' type='submit'>Vaciar carrito</button>");
                out.println("</form>");
                out.println("</aside>");
                out.println("</div>");
            }

            out.println("</main>");
            Plantilla.pie(request, out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Carrito carrito = Carrito.de(request.getSession());
        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");
        int id = numero(request.getParameter("id"), 0);
        int cantidad = numero(request.getParameter("cantidad"), 1);

        switch (accion) {
            case "agregar":
                Producto p = CatalogoMock.porId(id);
                if (p != null) carrito.agregar(p, Math.max(1, cantidad));
                break;
            case "cambiar":
                carrito.cambiar(id, cantidad);
                break;
            case "quitar":
                carrito.quitar(id);
                break;
            case "vaciar":
                carrito.vaciar();
                break;
            default:
                break;
        }

        // Petición AJAX (app.js): responde JSON con el nuevo contador, sin recargar la página.
        if ("fetch".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"count\":" + carrito.getCantidadTotal() + ",\"total\":\"" + carrito.getTotalTexto() + "\"}");
            return;
        }
        String destino = "agregar".equals(accion) && request.getHeader("Referer") != null
                ? request.getHeader("Referer") : request.getContextPath() + "/carrito";
        response.sendRedirect(destino);
    }

    private static int numero(String s, int porDefecto) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return porDefecto;
        }
    }
}

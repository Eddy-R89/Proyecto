package com.parada.web;

import com.parada.data.CatalogoMock;
import com.parada.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** Detalle de un producto: /producto?id=1 */
@WebServlet(urlPatterns = {"/producto"})
public class ProductoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Producto p = null;
        try {
            p = CatalogoMock.porId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException ignorado) {
        }
        if (p == null) {
            response.sendError(404, "Producto no encontrado");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, p.getNombre(), "");
            out.println("<main id='contenido' class='contenedor'>");
            out.println("<p class='migas'><a href='" + ctx + "/catalogo'>Catálogo</a> / <a href='" + ctx + "/catalogo?cat=" + Plantilla.url(p.getCategoria()) + "'>" + Plantilla.esc(p.getCategoria()) + "</a></p>");

            out.println("<section class='detalle'>");
            out.println("<div class='detalle-media'>");
            out.println(Plantilla.imagen(ctx, p, "img-producto", "emoji grande", false));
            out.println("</div>");
            out.println("<div>");
            out.println("<h1>" + Plantilla.esc(p.getNombre()) + "</h1>");
            out.println("<p class='suave'>" + Plantilla.esc(p.getDescripcion()) + "</p>");
            out.println("<p class='precio grande-precio'>" + p.getPrecioTexto() + "</p>");
            out.println("<form class='form-agregar fila' method='post' action='" + ctx + "/carrito'>");
            out.println("<input type='hidden' name='accion' value='agregar'>");
            out.println("<input type='hidden' name='id' value='" + p.getId() + "'>");
            out.println("<label class='cantidad'>Cantidad <input type='number' name='cantidad' value='1' min='1' max='99'></label>");
            out.println("<button class='btn' type='submit'>Agregar al carrito</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</section>");

            out.println("<h2>También en " + Plantilla.esc(p.getCategoria()) + "</h2>");
            out.println("<div class='grilla'>");
            int mostrados = 0;
            for (Producto otro : CatalogoMock.filtrar(p.getCategoria(), null)) {
                if (otro.getId() != p.getId() && mostrados < 4) {
                    Plantilla.tarjeta(out, ctx, otro);
                    mostrados++;
                }
            }
            out.println("</div>");

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
        processRequest(request, response);
    }
}

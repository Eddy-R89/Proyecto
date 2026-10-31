package com.parada.web;

import com.parada.data.ConsultaProducto;
import com.parada.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Catálogo con filtro por categoría (?cat=) y búsqueda (?q=). */
@WebServlet(urlPatterns = {"/catalogo"})
public class CatalogoServlet extends HttpServlet {
    private static final Logger registro = Logger.getLogger(CatalogoServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        String cat = request.getParameter("cat") == null ? "" : request.getParameter("cat");
        String q = request.getParameter("q") == null ? "" : request.getParameter("q");
        List<Producto> productos = ConsultaProducto.filtrar(cat, q);

        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Catálogo", q);
            out.println("<main id='contenido' class='contenedor'>");
            out.println("<h1>Catálogo</h1>");

            out.println("<div class='chips' role='list'>");
            out.println("<a role='listitem' class='chip" + (cat.isEmpty() ? " activo" : "") + "' href='" + ctx + "/catalogo'>Todo</a>");
            for (String c : ConsultaProducto.categorias()) {
                String activo = c.equals(cat) ? " activo" : "";
                out.println("<a role='listitem' class='chip" + activo + "' href='" + ctx + "/catalogo?cat=" + Plantilla.url(c) + "'>" + Plantilla.esc(c) + "</a>");
            }
            out.println("</div>");

            if (productos.isEmpty()) {
                out.println("<div class='vacio'>");
                out.println("<p>No encontramos productos con esa búsqueda.</p>");
                out.println("<a class='btn' href='" + ctx + "/catalogo'>Ver todo el catálogo</a>");
                out.println("</div>");
            } else {
                out.println("<div class='grilla'>");
                for (Producto p : productos) {
                    Plantilla.tarjeta(out, ctx, p);
                }
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
        processRequest(request, response);
    }
}

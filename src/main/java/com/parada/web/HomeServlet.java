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
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Página de inicio: banner, categorías y productos destacados. */
@WebServlet(urlPatterns = {"/inicio"})
public class HomeServlet extends HttpServlet {
    private static final Logger registro = Logger.getLogger(HomeServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Inicio", "");
            out.println("<main id='contenido'>");

            out.println("<section class='hero'>");
            out.println("<div class='contenedor hero-fila'>");
            out.println("<div>");
            out.println("<h1>Tu parada de camino: café, snacks y comida lista.</h1>");
            out.println("<p class='hero-sub'>Pide en línea y recoge en tienda o recíbelo en casa.</p>");
            out.println("<a class='btn' href='" + ctx + "/catalogo'>Ver catálogo</a>");
            out.println("</div>");
            out.println("<div class='hero-pila' aria-hidden='true'>");
            out.println("<span>☕</span><span>🥐</span><span>🌭</span><span>🥤</span>");
            out.println("</div>");
            out.println("</div>");
            out.println("</section>");

            out.println("<section class='contenedor'>");
            out.println("<h2>Categorías</h2>");
            out.println("<div class='cats'>");
            for (String cat : ConsultaProducto.categorias()) {
                out.println("<a class='cat' href='" + ctx + "/catalogo?cat=" + Plantilla.url(cat) + "'>" + Plantilla.esc(cat) + "</a>");
            }
            out.println("</div>");
            out.println("</section>");

            out.println("<section class='contenedor'>");
            out.println("<h2>Lo más pedido</h2>");
            out.println("<div class='grilla'>");
            for (Producto p : ConsultaProducto.todos().subList(0, Math.min(8, ConsultaProducto.todos().size()))) {
                Plantilla.tarjeta(out, ctx, p);
            }
            out.println("</div>");
            out.println("</section>");

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

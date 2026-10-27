package com.parada.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/** Páginas sencillas: perfil, contacto y confirmación del pedido. */
@WebServlet(urlPatterns = {"/perfil", "/contacto", "/confirmacion"})
public class PaginasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ruta = request.getServletPath();
        HttpSession sesion = request.getSession();
        String ctx = request.getContextPath();

        if ("/perfil".equals(ruta) && sesion.getAttribute("usuarioEmail") == null) {
            response.sendRedirect(ctx + "/login");
            return;
        }
        if ("/confirmacion".equals(ruta) && sesion.getAttribute("pedidoNumero") == null) {
            response.sendRedirect(ctx + "/inicio");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            switch (ruta) {
                case "/perfil":
                    perfil(request, out, sesion);
                    break;
                case "/contacto":
                    contacto(request, out, null);
                    break;
                default:
                    confirmacion(request, out, sesion);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Solo el formulario de contacto envía por POST.
        if (!"/contacto".equals(request.getServletPath())) {
            response.sendError(405);
            return;
        }
        String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre").trim();
        String email = request.getParameter("email") == null ? "" : request.getParameter("email").trim();
        String mensaje = request.getParameter("mensaje") == null ? "" : request.getParameter("mensaje").trim();

        if (nombre.isEmpty() || email.isEmpty() || mensaje.isEmpty()) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                contacto(request, out, "Completa todos los campos.");
            }
            return;
        }
        // TODO: guardar o enviar el mensaje (correo / base de datos).
        response.sendRedirect(request.getContextPath() + "/contacto?enviado=1");
    }

    private void perfil(HttpServletRequest request, PrintWriter out, HttpSession sesion) {
        String ctx = request.getContextPath();
        Plantilla.cabecera(request, out, "Mi perfil", "");
        out.println("<main id='contenido' class='contenedor angosto'>");
        out.println("<h1>Mi perfil</h1>");
        out.println("<p><strong>" + Plantilla.esc((String) sesion.getAttribute("usuarioNombre")) + "</strong><br>");
        out.println("<span class='suave'>" + Plantilla.esc((String) sesion.getAttribute("usuarioEmail")) + "</span></p>");
        out.println("<h2>Historial de pedidos</h2>");
        out.println("<div class='vacio'><p>Aún no tienes pedidos.</p><a class='btn' href='" + ctx + "/catalogo'>Hacer mi primer pedido</a></div>");
        out.println("<form method='post' action='" + ctx + "/logout'><button class='btn-sec' type='submit'>Cerrar sesión</button></form>");
        out.println("</main>");
        Plantilla.pie(request, out);
    }

    private void contacto(HttpServletRequest request, PrintWriter out, String error) {
        String ctx = request.getContextPath();
        Plantilla.cabecera(request, out, "Contacto", "");
        out.println("<main id='contenido' class='contenedor angosto'>");
        out.println("<h1>Contacto</h1>");
        out.println("<p class='suave'>Escríbenos y te respondemos en un día hábil.</p>");
        if ("1".equals(request.getParameter("enviado"))) {
            out.println("<p class='ok' role='status'>Mensaje enviado. ¡Gracias!</p>");
        }
        if (error != null) {
            out.println("<p class='error' role='alert'>" + Plantilla.esc(error) + "</p>");
        }
        out.println("<form class='formulario' method='post' action='" + ctx + "/contacto'>");
        out.println("<label>Nombre <input name='nombre' value='" + Plantilla.param(request, "nombre") + "' required></label>");
        out.println("<label>Correo <input name='email' type='email' value='" + Plantilla.param(request, "email") + "' required></label>");
        out.println("<label>Mensaje <textarea name='mensaje' rows='5' required>" + Plantilla.param(request, "mensaje") + "</textarea></label>");
        out.println("<button class='btn ancho' type='submit'>Enviar mensaje</button>");
        out.println("</form>");
        out.println("</main>");
        Plantilla.pie(request, out);
    }

    private void confirmacion(HttpServletRequest request, PrintWriter out, HttpSession sesion) {
        String ctx = request.getContextPath();
        Plantilla.cabecera(request, out, "Pedido confirmado", "");
        out.println("<main id='contenido' class='contenedor centro'>");
        out.println("<div class='confirma'>");
        out.println("<span class='emoji grande' aria-hidden='true'>✅</span>");
        out.println("<h1>Pedido confirmado</h1>");
        out.println("<p>Número <strong>" + Plantilla.esc((String) sesion.getAttribute("pedidoNumero")) + "</strong> · "
                + Plantilla.esc((String) sesion.getAttribute("pedidoEntrega")) + " · "
                + Plantilla.esc((String) sesion.getAttribute("pedidoTotal")) + "</p>");
        out.println("<a class='btn' href='" + ctx + "/catalogo'>Seguir comprando</a>");
        out.println("</div>");
        out.println("</main>");
        Plantilla.pie(request, out);
    }
}

package com.parada.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/** Inicio de sesión. */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private void mostrar(HttpServletRequest request, HttpServletResponse response, String error)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Entrar", "");
            out.println("<main id='contenido' class='contenedor angosto'>");
            out.println("<h1>Entrar</h1>");
            if ("1".equals(request.getParameter("registrado"))) {
                out.println("<p class='ok' role='status'>Cuenta creada. Ya puedes entrar.</p>");
            }
            if (error != null) {
                out.println("<p class='error' role='alert'>" + Plantilla.esc(error) + "</p>");
            }
            out.println("<form class='formulario' method='post' action='" + ctx + "/login' novalidate>");
            out.println("<label>Correo <input name='email' type='email' value='" + Plantilla.param(request, "email") + "' autocomplete='email' required></label>");
            out.println("<label>Contraseña <input name='clave' type='password' autocomplete='current-password' required></label>");
            out.println("<button class='btn ancho' type='submit'>Entrar</button>");
            out.println("</form>");
            out.println("<p class='suave'>¿No tienes cuenta? <a href='" + ctx + "/registro'>Crear cuenta</a></p>");
            out.println("</main>");
            Plantilla.pie(request, out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email") == null ? "" : request.getParameter("email").trim();
        String clave = request.getParameter("clave") == null ? "" : request.getParameter("clave");

        // TODO Integrante 3/4: validar contra la BD con contraseña hasheada (BCrypt) y PreparedStatement.
        if (email.isEmpty() || clave.isEmpty()) {
            mostrar(request, response, "Escribe tu correo y contraseña.");
            return;
        }
        request.getSession();
        request.changeSessionId(); // evita fijación de sesión
        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuarioEmail", email);
        sesion.setAttribute("usuarioNombre", email.contains("@") ? email.substring(0, email.indexOf('@')) : email);
        response.sendRedirect(request.getContextPath() + "/perfil");
    }
}

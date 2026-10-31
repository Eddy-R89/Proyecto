package com.parada.web;

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

/** Registro de usuarios nuevos. */
@WebServlet(urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {
    private static final Logger registro = Logger.getLogger(RegistroServlet.class.getName());

    private void mostrar(HttpServletRequest request, HttpServletResponse response, String error)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Crear cuenta", "");
            out.println("<main id='contenido' class='contenedor angosto'>");
            out.println("<h1>Crear cuenta</h1>");
            if (error != null) {
                out.println("<p class='error' role='alert'>" + Plantilla.esc(error) + "</p>");
            }
            out.println("<form class='formulario' method='post' action='" + ctx + "/registro' novalidate>");
            out.println("<label>Nombre <input name='nombre' value='" + Plantilla.param(request, "nombre") + "' autocomplete='name' required></label>");
            out.println("<label>Correo <input name='email' type='email' value='" + Plantilla.param(request, "email") + "' autocomplete='email' required></label>");
            out.println("<label>Contraseña (mínimo 8 caracteres) <input name='clave' type='password' autocomplete='new-password' required></label>");
            out.println("<label>Repite la contraseña <input name='clave2' type='password' autocomplete='new-password' required></label>");
            out.println("<button class='btn ancho' type='submit'>Crear cuenta</button>");
            out.println("</form>");
            out.println("<p class='suave'>¿Ya tienes cuenta? <a href='" + ctx + "/login'>Entrar</a></p>");
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
        String nombre = valor(request, "nombre");
        String email = valor(request, "email");
        String clave = valor(request, "clave");
        String clave2 = valor(request, "clave2");

        String error = null;
        if (nombre.isEmpty()) error = "Escribe tu nombre.";
        else if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) error = "Escribe un correo válido.";
        else if (clave.length() < 8) error = "La contraseña debe tener al menos 8 caracteres.";
        else if (!clave.equals(clave2)) error = "Las contraseñas no coinciden.";

        if (error != null) {
            mostrar(request, response, error);
            return;
        }
        // TODO Integrante 3/4: INSERT en la tabla de usuarios con hash BCrypt.
        response.sendRedirect(request.getContextPath() + "/login?registrado=1");
    }

    private static String valor(HttpServletRequest r, String clave) {
        return r.getParameter(clave) == null ? "" : r.getParameter(clave).trim();
    }
}

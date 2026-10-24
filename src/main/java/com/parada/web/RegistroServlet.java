package com.parada.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nombre = v(req, "nombre"), email = v(req, "email"), clave = v(req, "clave"), clave2 = v(req, "clave2");
        String error = null;
        if (nombre.isEmpty()) error = "Escribe tu nombre.";
        else if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) error = "Escribe un correo válido.";
        else if (clave.length() < 8) error = "La contraseña debe tener al menos 8 caracteres.";
        else if (!clave.equals(clave2)) error = "Las contraseñas no coinciden.";

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("nombre", nombre);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, res);
            return;
        }
        // TODO Integrante 3/4: INSERT en `usuario` con hash BCrypt.
        res.sendRedirect(req.getContextPath() + "/login?registrado=1");
    }

    private static String v(HttpServletRequest r, String k) { return r.getParameter(k) == null ? "" : r.getParameter(k).trim(); }
}

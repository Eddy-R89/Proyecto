package com.parada.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email") == null ? "" : req.getParameter("email").trim();
        String clave = req.getParameter("clave") == null ? "" : req.getParameter("clave");
        // TODO Integrante 3/4: validar contra BD con contraseña hasheada (BCrypt) y PreparedStatement.
        if (email.isEmpty() || clave.isEmpty()) {
            req.setAttribute("error", "Escribe tu correo y contraseña.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
            return;
        }
        HttpSession s = req.getSession();
        req.changeSessionId(); // evita fijación de sesión
        s.setAttribute("usuarioEmail", email);
        s.setAttribute("usuarioNombre", email.contains("@") ? email.substring(0, email.indexOf('@')) : email);
        res.sendRedirect(req.getContextPath() + "/perfil");
    }
}

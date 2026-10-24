package com.parada.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/** Páginas simples sin lógica propia: perfil, contacto, confirmación. */
@WebServlet({"/perfil", "/contacto", "/confirmacion"})
public class PaginasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String ruta = req.getServletPath();
        HttpSession s = req.getSession();
        if ("/perfil".equals(ruta) && s.getAttribute("usuarioEmail") == null) {
            res.sendRedirect(req.getContextPath() + "/login"); return;
        }
        if ("/confirmacion".equals(ruta) && s.getAttribute("pedidoNumero") == null) {
            res.sendRedirect(req.getContextPath() + "/inicio"); return;
        }
        req.getRequestDispatcher("/WEB-INF/views" + ruta + ".jsp").forward(req, res);
    }
}

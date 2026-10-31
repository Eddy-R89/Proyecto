package com.parada.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger registro = Logger.getLogger(LogoutServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
       try {
            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/inicio");
        } catch (Exception e) {
            registro.log(Level.SEVERE, "Error al cerrar la sesión", e);
            ManejoErrores.mostrar(req, res);
           }
        }
    }

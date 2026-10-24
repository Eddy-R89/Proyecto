package com.parada.web;

import com.parada.data.CatalogoMock;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cat = req.getParameter("cat");
        String q = req.getParameter("q");
        req.setAttribute("categorias", CatalogoMock.categorias());
        req.setAttribute("productos", CatalogoMock.filtrar(cat, q));
        req.setAttribute("catActual", cat == null ? "" : cat);
        req.setAttribute("q", q == null ? "" : q);
        req.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(req, res);
    }
}

package com.parada.web;

import com.parada.data.CatalogoMock;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/inicio")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("categorias", CatalogoMock.categorias());
        req.setAttribute("destacados", CatalogoMock.todos().subList(0, 8));
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, res);
    }
}

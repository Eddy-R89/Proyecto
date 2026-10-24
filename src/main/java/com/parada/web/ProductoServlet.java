package com.parada.web;

import com.parada.data.CatalogoMock;
import com.parada.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Producto p = null;
        try { p = CatalogoMock.porId(Integer.parseInt(req.getParameter("id"))); } catch (NumberFormatException ignored) {}
        if (p == null) { res.sendError(404, "Producto no encontrado"); return; }
        final int idActual = p.getId();
        req.setAttribute("p", p);
        req.setAttribute("relacionados", CatalogoMock.filtrar(p.getCategoria(), null).stream()
            .filter(x -> x.getId() != idActual).limit(4).collect(Collectors.toList()));
        req.getRequestDispatcher("/WEB-INF/views/producto.jsp").forward(req, res);
    }
}

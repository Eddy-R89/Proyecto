package com.parada.web;

import com.parada.data.CatalogoMock;
import com.parada.model.Carrito;
import com.parada.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Carrito.de(req.getSession());
        req.getRequestDispatcher("/WEB-INF/views/carrito.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Carrito c = Carrito.de(req.getSession());
        String accion = req.getParameter("accion") == null ? "" : req.getParameter("accion");
        int id = num(req.getParameter("id"), 0);
        int cant = num(req.getParameter("cantidad"), 1);

        switch (accion) {
            case "agregar":
                Producto p = CatalogoMock.porId(id);
                if (p != null) c.agregar(p, Math.max(1, cant));
                break;
            case "cambiar":
                c.cambiar(id, cant);
                break;
            case "quitar":
                c.quitar(id);
                break;
            case "vaciar":
                c.vaciar();
                break;
            default:
                break;
        }

        if ("fetch".equals(req.getHeader("X-Requested-With"))) {
            res.setContentType("application/json");
            res.getWriter().write("{\"count\":" + c.getCantidadTotal() + ",\"total\":\"" + c.getTotalTexto() + "\"}");
            return;
        }
        String destino = "agregar".equals(accion) && req.getHeader("Referer") != null
            ? req.getHeader("Referer") : req.getContextPath() + "/carrito";
        res.sendRedirect(destino);
    }

    private static int num(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}

package com.parada.web;

import com.parada.model.Carrito;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (Carrito.de(req.getSession()).isVacio()) { res.sendRedirect(req.getContextPath() + "/carrito"); return; }
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Carrito c = Carrito.de(s);
        if (c.isVacio()) { res.sendRedirect(req.getContextPath() + "/carrito"); return; }

        String nombre = trim(req.getParameter("nombre"));
        String telefono = trim(req.getParameter("telefono"));
        String entrega = trim(req.getParameter("entrega"));
        String direccion = trim(req.getParameter("direccion"));
        String pago = trim(req.getParameter("pago"));

        String error = null;
        if (nombre.isEmpty()) error = "Escribe tu nombre.";
        else if (!telefono.matches("[0-9 +\\-]{8,15}")) error = "Escribe un teléfono válido (8 a 15 dígitos).";
        else if ("domicilio".equals(entrega) && direccion.isEmpty()) error = "Escribe la dirección de entrega.";
        else if (pago.isEmpty()) error = "Elige un método de pago.";

        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, res);
            return;
        }

        // TODO Integrante 3: guardar pedido + detalle con PedidoDAO (transacción).
        // TODO Integrante 4: pasarela de pago de terceros y validación/saneo de datos.
        s.setAttribute("pedidoNumero", "PED-" + (System.currentTimeMillis() % 100000));
        s.setAttribute("pedidoTotal", c.getTotalTexto());
        s.setAttribute("pedidoEntrega", "domicilio".equals(entrega) ? "A domicilio" : "Recoger en tienda");
        c.vaciar();
        res.sendRedirect(req.getContextPath() + "/confirmacion");
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }
}

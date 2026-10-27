package com.parada.web;

import com.parada.model.Carrito;
import com.parada.model.ItemCarrito;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/** Finalizar pedido: datos del cliente, entrega y forma de pago. */
@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private void mostrar(HttpServletRequest request, HttpServletResponse response, String error)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ctx = request.getContextPath();
        Carrito carrito = Carrito.de(request.getSession());
        boolean domicilio = "domicilio".equals(request.getParameter("entrega"));
        boolean tarjeta = "tarjeta".equals(request.getParameter("pago"));

        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Pago", "");
            out.println("<main id='contenido' class='contenedor'>");
            out.println("<h1>Finalizar pedido</h1>");
            out.println("<div class='dos-col'>");

            out.println("<form class='formulario' method='post' action='" + ctx + "/checkout' novalidate>");
            if (error != null) {
                out.println("<p class='error' role='alert'>" + Plantilla.esc(error) + "</p>");
            }
            out.println("<fieldset>");
            out.println("<legend>Tus datos</legend>");
            out.println("<label>Nombre <input name='nombre' value='" + Plantilla.param(request, "nombre") + "' autocomplete='name' required></label>");
            out.println("<label>Teléfono <input name='telefono' type='tel' value='" + Plantilla.param(request, "telefono") + "' autocomplete='tel' required></label>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<legend>Entrega</legend>");
            out.println("<label class='opcion'><input type='radio' name='entrega' value='recoger'" + (domicilio ? "" : " checked") + "> Recoger en tienda</label>");
            out.println("<label class='opcion'><input type='radio' name='entrega' value='domicilio'" + (domicilio ? " checked" : "") + "> A domicilio</label>");
            out.println("<label>Dirección (solo a domicilio) <input name='direccion' value='" + Plantilla.param(request, "direccion") + "' autocomplete='street-address'></label>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<legend>Pago</legend>");
            out.println("<label class='opcion'><input type='radio' name='pago' value='efectivo'" + (tarjeta ? "" : " checked") + "> Efectivo al recibir</label>");
            out.println("<label class='opcion'><input type='radio' name='pago' value='tarjeta'" + (tarjeta ? " checked" : "") + "> Tarjeta (pasarela de pago)</label>");
            out.println("</fieldset>");

            out.println("<button class='btn ancho' type='submit'>Confirmar pedido</button>");
            out.println("</form>");

            out.println("<aside class='resumen' aria-label='Resumen del pedido'>");
            out.println("<h2>Tu pedido</h2>");
            for (ItemCarrito it : carrito.getItems()) {
                out.println("<p class='resumen-fila'><span>" + it.getCantidad() + " × " + Plantilla.esc(it.getProducto().getNombre()) + "</span><span>" + it.getSubtotalTexto() + "</span></p>");
            }
            out.println("<p class='resumen-fila total'><span>Total</span><span>" + carrito.getTotalTexto() + "</span></p>");
            out.println("</aside>");

            out.println("</div>");
            out.println("</main>");
            Plantilla.pie(request, out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (Carrito.de(request.getSession()).isVacio()) {
            response.sendRedirect(request.getContextPath() + "/carrito");
            return;
        }
        mostrar(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        Carrito carrito = Carrito.de(sesion);
        if (carrito.isVacio()) {
            response.sendRedirect(request.getContextPath() + "/carrito");
            return;
        }

        String nombre = limpio(request.getParameter("nombre"));
        String telefono = limpio(request.getParameter("telefono"));
        String entrega = limpio(request.getParameter("entrega"));
        String direccion = limpio(request.getParameter("direccion"));
        String pago = limpio(request.getParameter("pago"));

        String error = null;
        if (nombre.isEmpty()) error = "Escribe tu nombre.";
        else if (!telefono.matches("[0-9 +\\-]{8,15}")) error = "Escribe un teléfono válido (8 a 15 dígitos).";
        else if ("domicilio".equals(entrega) && direccion.isEmpty()) error = "Escribe la dirección de entrega.";
        else if (pago.isEmpty()) error = "Elige un método de pago.";

        if (error != null) {
            mostrar(request, response, error);
            return;
        }

        // TODO Integrante 3: guardar pedido + detalle con PedidoDAO (transacción).
        // TODO Integrante 4: pasarela de pago de terceros y validación/saneo de datos.
        sesion.setAttribute("pedidoNumero", "PED-" + (System.currentTimeMillis() % 100000));
        sesion.setAttribute("pedidoTotal", carrito.getTotalTexto());
        sesion.setAttribute("pedidoEntrega", "domicilio".equals(entrega) ? "A domicilio" : "Recoger en tienda");
        carrito.vaciar();
        response.sendRedirect(request.getContextPath() + "/confirmacion");
    }

    private static String limpio(String s) {
        return s == null ? "" : s.trim();
    }
}

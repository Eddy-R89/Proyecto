/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.parada.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ManejoErrores {

    public static void mostrar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (response.isCommitted()) return;
        response.resetBuffer();
        response.setStatus(500);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Plantilla.cabecera(request, out, "Error", "");
            out.println("<main id='contenido' class='contenedor'>");
            out.println("<h1>Algo salió mal</h1>");
            out.println("<p>No pudimos completar tu solicitud. Intenta de nuevo en unos minutos.</p>");
            out.println("<a class='btn' href='" + request.getContextPath() + "/catalogo'>Volver al catálogo</a>");
            out.println("</main>");
            Plantilla.pie(request, out);
        }
    }
}
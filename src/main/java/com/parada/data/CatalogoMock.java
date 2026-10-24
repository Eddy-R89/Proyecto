package com.parada.data;

import com.parada.model.Producto;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Datos de ejemplo para maquetar.
 * TODO Integrante 3: reemplazar por ProductoDAO (misma firma: todos, porId, filtrar, categorias).
 */
public final class CatalogoMock {
    private static final List<Producto> TODOS = List.of(
        new Producto(1,  "Café americano grande", "Café",            "12.00", "Cafe.png", "Café recién hecho, 16 oz."),
        new Producto(2,  "Capuchino",             "Café",            "18.00", "Capuccino.jpg", "Espresso con leche espumada."),
        new Producto(3,  "Croissant de mantequilla","Panadería",     "10.00", "crossait.jpg", "Horneado en la mañana."),
        new Producto(4,  "Dona glaseada",         "Panadería",       "7.00", "dona.jpg", "Clásica, con glaseado de vainilla."),
        new Producto(5,  "Hot dog completo",      "Comida preparada","22.00", "hotdog.jpg", "Salchicha, pan suave y aderezos."),
        new Producto(6,  "Sándwich de pollo",     "Comida preparada","28.00", "sandwich.jpg", "Pollo, lechuga y tomate."),
        new Producto(7,  "Pizza personal",        "Comida preparada","32.00", "pizza.jpg", "Queso y pepperoni."),
        new Producto(8,  "Agua pura 600 ml",      "Bebidas",         "6.00", "agua.jpg", "Botella individual."),
        new Producto(9,  "Gaseosa de cola 500 ml","Bebidas",         "9.00", "Gaseosa.jpg", "Bien fría."),
        new Producto(10, "Jugo de naranja",       "Bebidas",         "12.00", "Jugo.jpg", "100% jugo, 355 ml."),
        new Producto(11, "Papas fritas",          "Snacks",          "8.00", "papas.jpg", "Bolsa 45 g, sabor original."),
        new Producto(12, "Barra de granola",      "Snacks",          "7.00", "Granola.jpg", "Avena y miel."),
        new Producto(13, "Chocolate con maní",    "Snacks",          "10.00", "Chocolate.jpg", "Barra 50 g."),
        new Producto(14, "Galletas de avena",     "Despensa",        "14.00", "Galleta.jpg", "Paquete familiar."),
        new Producto(15, "Pan de caja",           "Despensa",        "20.00", "Pan.jpg", "Rebanado, 600 g."),
        new Producto(16, "Leche entera 1 L",      "Despensa",        "12.00", "Leche.jpg", "Pasteurizada.")
    );

    private CatalogoMock() {}

    public static List<Producto> todos() { return TODOS; }

    public static Producto porId(int id) {
        return TODOS.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public static List<String> categorias() {
        return TODOS.stream().map(Producto::getCategoria).distinct().collect(Collectors.toList());
    }

    public static List<Producto> filtrar(String categoria, String texto) {
        String q = texto == null ? "" : texto.trim().toLowerCase();
        return TODOS.stream()
            .filter(p -> categoria == null || categoria.isBlank() || p.getCategoria().equalsIgnoreCase(categoria))
            .filter(p -> q.isEmpty() || p.getNombre().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }
}

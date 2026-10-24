package com.parada.model;

import java.math.BigDecimal;

/** TODO Integrante 3: alinear campos con la tabla `producto`. */
public class Producto {
    private final int id;
    private final String nombre;
    private final String categoria;
    private final BigDecimal precio;
    private final String emoji;       // placeholder; luego reemplazar por URL de imagen
    private final String descripcion;

    public Producto(int id, String nombre, String categoria, String precio, String emoji, String descripcion) {
        this.id = id; this.nombre = nombre; this.categoria = categoria;
        this.precio = new BigDecimal(precio); this.emoji = emoji; this.descripcion = descripcion;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPrecio() { return precio; }
    public String getEmoji() { return emoji; }
    public String getDescripcion() { return descripcion; }
    public String getPrecioTexto() { return Moneda.fmt(precio); }
}

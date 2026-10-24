package com.parada.model;

import java.math.BigDecimal;

public class ItemCarrito {
    private final Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) { this.producto = producto; this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int c) { this.cantidad = c; }
    public BigDecimal getSubtotal() { return producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)); }
    public String getSubtotalTexto() { return Moneda.fmt(getSubtotal()); }
}

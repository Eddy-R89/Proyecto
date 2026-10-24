package com.parada.model;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Carrito en HttpSession (atributo "carrito"). */
public class Carrito {
    private final Map<Integer, ItemCarrito> items = new LinkedHashMap<>();

    public static Carrito de(HttpSession s) {
        Carrito c = (Carrito) s.getAttribute("carrito");
        if (c == null) { c = new Carrito(); s.setAttribute("carrito", c); }
        return c;
    }
    public void agregar(Producto p, int cant) {
        ItemCarrito it = items.get(p.getId());
        if (it == null) items.put(p.getId(), new ItemCarrito(p, Math.min(cant, 99)));
        else it.setCantidad(Math.min(it.getCantidad() + cant, 99));
    }
    public void quitar(int id) { items.remove(id); }
    public void cambiar(int id, int cant) {
        if (cant <= 0) { items.remove(id); return; }
        ItemCarrito it = items.get(id);
        if (it != null) it.setCantidad(Math.min(cant, 99));
    }
    public void vaciar() { items.clear(); }
    public Collection<ItemCarrito> getItems() { return items.values(); }
    public boolean isVacio() { return items.isEmpty(); }
    public int getCantidadTotal() { return items.values().stream().mapToInt(ItemCarrito::getCantidad).sum(); }
    public BigDecimal getTotal() {
        return items.values().stream().map(ItemCarrito::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public String getTotalTexto() { return Moneda.fmt(getTotal()); }
}

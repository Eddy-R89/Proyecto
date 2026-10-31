package com.parada.data;

import com.parada.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ConsultaProducto {

    private ConsultaProducto() {}

    private static final String COLUMNAS =
            "SELECT id, nombre, categoria, precio, emoji, descripcion FROM productos";

    // Convierte una fila de la base en un Producto (el precio se pasa como texto "12.00")
    private static Producto desdeFila(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getBigDecimal("precio").setScale(2).toPlainString(),
                rs.getString("emoji"),
                rs.getString("descripcion"));
    }

    public static List<Producto> todos() {
        List<Producto> lista = new ArrayList<>();
        try (Connection con = ConexionDB.abrir();
             PreparedStatement ps = con.prepareStatement(COLUMNAS + " ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(desdeFila(rs));
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron leer los productos", e);
        }
        return lista;
    }

    public static Producto porId(int id) {
        try (Connection con = ConexionDB.abrir();
             PreparedStatement ps = con.prepareStatement(COLUMNAS + " WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? desdeFila(rs) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo leer el producto " + id, e);
        }
    }

    public static List<String> categorias() {
        List<String> lista = new ArrayList<>();
        try (Connection con = ConexionDB.abrir();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT categoria FROM productos GROUP BY categoria ORDER BY MIN(id)");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(rs.getString("categoria"));
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron leer las categorías", e);
        }
        return lista;
    }

    public static List<Producto> filtrar(String categoria, String texto) {
        String cat = categoria == null ? "" : categoria.trim();
        String q = texto == null ? "" : texto.trim().toLowerCase();
        List<Producto> lista = new ArrayList<>();
        String sql = COLUMNAS + " WHERE (? = '' OR categoria = ?) AND LOWER(nombre) LIKE ? ORDER BY id";
        try (Connection con = ConexionDB.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cat);
            ps.setString(2, cat);
            ps.setString(3, "%" + q + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(desdeFila(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo filtrar el catálogo", e);
        }
        return lista;
    }
}



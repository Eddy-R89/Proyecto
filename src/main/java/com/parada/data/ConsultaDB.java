package com.parada.data;

import com.parada.model.Carrito;
import com.parada.model.ItemCarrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public final class ConsultaDB {

    private ConsultaDB() {}

    /** Guarda el pedido y su detalle. Devuelve el número, por ejemplo PED-00007. */
    public static String guardar(String nombre, String telefono, String entrega,
                                 String direccion, String pago, Carrito carrito) {
        try (Connection con = ConexionDB.abrir()) {
            con.setAutoCommit(false);
            try {
                // 1. Pedido (número temporal; el total se calcula al final)
                int idPedido;
                String sqlPedido = "INSERT INTO pedidos (numero, nombre_cliente, telefono, entrega, direccion, pago, total) "
                        + "VALUES (?, ?, ?, ?, ?, ?, 0)";
                try (PreparedStatement ps = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, "TMP-" + UUID.randomUUID().toString().substring(0, 12));
                    ps.setString(2, nombre);
                    ps.setString(3, telefono);
                    ps.setString(4, entrega);
                    ps.setString(5, direccion.isEmpty() ? null : direccion);
                    ps.setString(6, pago);
                    ps.executeUpdate();
                    try (ResultSet claves = ps.getGeneratedKeys()) {
                        claves.next();
                        idPedido = claves.getInt(1);
                    }
                }

                // 2. Detalle: el precio se toma de la base, no del navegador
                String sqlDetalle = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario) "
                        + "SELECT ?, id, ?, precio FROM productos WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                    for (ItemCarrito it : carrito.getItems()) {
                        ps.setInt(1, idPedido);
                        ps.setInt(2, it.getCantidad());
                        ps.setInt(3, it.getProducto().getId());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                // 3. Total y número definitivo
                String numero = String.format("PED-%05d", idPedido);
                String sqlFinal = "UPDATE pedidos SET numero = ?, "
                        + "total = (SELECT COALESCE(SUM(cantidad * precio_unitario), 0) FROM detalle_pedido WHERE pedido_id = ?) "
                        + "WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlFinal)) {
                    ps.setString(1, numero);
                    ps.setInt(2, idPedido);
                    ps.setInt(3, idPedido);
                    ps.executeUpdate();
                }

                con.commit();
                return numero;
            } catch (SQLException e) {
                con.rollback();   // si algo falla, no queda un pedido a medias
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar el pedido", e);
        }
    }
}
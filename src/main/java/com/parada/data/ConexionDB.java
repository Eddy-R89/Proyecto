package com.parada.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionDB {

    // CAMBIAR: tu usuario y contraseña de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/parada_db"
        + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/Guatemala";
    private static final String USUARIO = "root";
    private static final String CLAVE = "fcbarsa123";

    private ConexionDB() {}

        public static Connection abrir() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   // registra el driver de MySQL
        } catch (ClassNotFoundException e) {
            throw new SQLException("Falta el driver de MySQL en el proyecto", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
package com.danife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/danife?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "Finanzas11.";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}


package com.danife;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();

        try {
            System.out.println("PRODUCTOS DE LA TIENDA DANIFE");
            System.out.println("-----------------------------");

            for (Producto p : dao.consultar()) {
                System.out.println(
                    "ID: " + p.getId()
                    + " | Nombre: " + p.getNombre()
                    + " | Categoría: " + p.getCategoria()
                    + " | Precio: $" + p.getPrecio()
                    + " | Cantidad: " + p.getCantidad()
                );
            }

        } catch (SQLException e) {
            System.err.println(
                "Error al consultar los productos: " + e.getMessage()
            );
        }
    }
}

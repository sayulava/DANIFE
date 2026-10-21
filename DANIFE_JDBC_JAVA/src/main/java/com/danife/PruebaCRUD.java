
package com.danife;

import java.sql.SQLException;
import java.util.List;

public class PruebaCRUD {

    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();

        try {
            // Buscar la cartera de prueba
            List<Producto> productos = dao.consultar();
            int idProducto = -1;

            for (Producto p : productos) {
                if (p.getNombre().equals("Cartera de prueba")
                        && p.getDescripcion() != null
                        && p.getDescripcion().equals("Producto de prueba CRUD")) {
                    idProducto = p.getId();
                    break;
                }
            }

            if (idProducto != -1) {
                boolean eliminado = dao.eliminar(idProducto);

                if (eliminado) {
                    System.out.println("¡Producto eliminado correctamente!");
                    System.out.println("ID eliminado: " + idProducto);
                    System.out.println("Producto: Cartera de prueba");
                } else {
                    System.out.println("No se pudo eliminar el producto.");
                }
            } else {
                System.out.println("No se encontró la cartera de prueba.");
                System.out.println("Puede que ya haya sido eliminada.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}


package com.danife;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // CREATE: registrar un producto
    public boolean insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos "
                + "(nombre, categoria, precio, cantidad, descripcion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidad());
            ps.setString(5, p.getDescripcion());

            return ps.executeUpdate() > 0;
        }
    }

    // READ: consultar todos los productos
    public List<Producto> consultar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad"),
                    rs.getString("descripcion")
                ));
            }
        }

        return productos;
    }

    // UPDATE: actualizar un producto
    public boolean actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET nombre=?, categoria=?, "
                + "precio=?, cantidad=?, descripcion=? WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidad());
            ps.setString(5, p.getDescripcion());
            ps.setInt(6, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE: eliminar un producto por su identificador
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

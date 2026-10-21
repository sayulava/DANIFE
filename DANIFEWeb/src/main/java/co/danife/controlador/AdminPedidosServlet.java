
package co.danife.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminPedidosServlet", urlPatterns = {"/adminPedidos"})
public class AdminPedidosServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/danife";
    private static final String USUARIO = "root";
    private static final String CLAVE = "Finanzas11.";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedidos";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(
                        URL, USUARIO, CLAVE);
                 PreparedStatement sentencia =
                        conexion.prepareStatement(sql);
                 ResultSet resultado = sentencia.executeQuery()) {

                ResultSetMetaData columnas = resultado.getMetaData();
                int cantidadColumnas = columnas.getColumnCount();

                while (resultado.next()) {
                    Map<String, Object> pedido = new LinkedHashMap<>();

                    for (int i = 1; i <= cantidadColumnas; i++) {
                        String nombre = columnas.getColumnLabel(i);
                        Object valor = resultado.getObject(i);
                        pedido.put(nombre, valor);
                    }

                    pedidos.add(pedido);
                }
            }

            request.setAttribute("pedidos", pedidos);

            request.getRequestDispatcher("/adminPedidos.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Error al consultar los pedidos de DANIFE.", e);
        }
    }
}

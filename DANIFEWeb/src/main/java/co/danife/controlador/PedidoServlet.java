package co.danife.controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedido"})
public class PedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println(
            "<h1>DANIFE - Formulario de pedidos</h1>" +
            "<p>El servlet funciona correctamente.</p>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cliente = request.getParameter("cliente");
        String producto = request.getParameter("producto");
        String cantidadTexto = request.getParameter("cantidad");

        // Validar los datos recibidos
        if (cliente == null || cliente.isBlank()
                || producto == null || producto.isBlank()
                || cantidadTexto == null || cantidadTexto.isBlank()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Debes completar los datos del pedido."
            );
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);

            if (cantidad < 1 || cantidad > 100) {
                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La cantidad debe estar entre 1 y 100."
                );
                return;
            }

            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Consulta para guardar el pedido
            String sql = "INSERT INTO pedidos "
                       + "(cliente, producto, cantidad) "
                       + "VALUES (?, ?, ?)";

            String url =
                "jdbc:mysql://localhost:3306/danife?serverTimezone=UTC";

            // Obtener la contraseña desde la variable de entorno
            String password = System.getenv("DANIFE_DB_PASSWORD");

            if (password == null || password.isBlank()) {
                getServletContext().log(
                    "La variable de entorno DANIFE_DB_PASSWORD no está configurada."
                );

                response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Falta configurar la contraseña de la base de datos."
                );
                return;
            }

            // Conectar y guardar el pedido
            try (Connection conexion = DriverManager.getConnection(
                        url, "root", password);
                 PreparedStatement sentencia =
                        conexion.prepareStatement(sql)) {

                sentencia.setString(1, cliente.trim());
                sentencia.setString(2, producto.trim());
                sentencia.setInt(3, cantidad);

                int filas = sentencia.executeUpdate();

                if (filas != 1) {
                    throw new SQLException(
                        "No se pudo confirmar el guardado del pedido."
                    );
                }
            }

            // Enviar los datos a la página de confirmación
            request.setAttribute("cliente", escapar(cliente.trim()));
            request.setAttribute("producto", escapar(producto.trim()));
            request.setAttribute("cantidad", cantidad);

            request.getRequestDispatcher("/resultado.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "La cantidad debe ser un número entero."
            );

        } catch (ClassNotFoundException e) {

            getServletContext().log(
                "No se encontró el controlador JDBC de MySQL.", e
            );

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se encuentra el controlador de MySQL."
            );

        } catch (SQLException e) {

            getServletContext().log(
                "Error al guardar el pedido de DANIFE en MySQL.", e
            );

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No fue posible guardar el pedido. Intenta nuevamente."
            );
        }
    }

    // Escapar caracteres especiales antes de mostrarlos en HTML
    private String escapar(String texto) {
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
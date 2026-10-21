
package co.danife.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedido"})
public class PedidoServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/danife?serverTimezone=UTC";

    private static final String USUARIO = "root";

    private static final int CANTIDAD_MINIMA = 1;
    private static final int CANTIDAD_MAXIMA = 100;

    /**
     * Muestra el acceso al módulo de pedidos.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    /**
     * Recibe, valida y registra un pedido en MySQL.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Obtener y limpiar los datos enviados por el formulario.
        String cliente = limpiar(request.getParameter("cliente"));
        String producto = limpiar(request.getParameter("producto"));
        String cantidadTexto = limpiar(request.getParameter("cantidad"));

        // Validar los campos obligatorios.
        if (cliente == null || cliente.isBlank()
                || producto == null || producto.isBlank()
                || cantidadTexto == null || cantidadTexto.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Debes completar todos los datos del pedido."
            );
            return;
        }

        // Validar que el nombre del cliente sea válido.
        // Permite letras Unicode, tildes, espacios, guiones y apóstrofes.
        if (cliente.length() < 2
                || cliente.length() > 100
                || !cliente.matches(
                    "^[\\p{L}]+(?:[ '-][\\p{L}]+)*$")) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El nombre del cliente debe contener letras "
                    + "y tener entre 2 y 100 caracteres."
            );
            return;
        }

        // Validar que el producto sea una opción permitida.
        if (!"Vestido elegante".equals(producto)
                && !"Zapatos de dama".equals(producto)
                && !"Bolso moderno".equals(producto)
                && !"Set de accesorios".equals(producto)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Debes seleccionar un producto válido."
            );
            return;
        }

        // Validar la cantidad solicitada.
        final int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);

            if (cantidad < CANTIDAD_MINIMA
                    || cantidad > CANTIDAD_MAXIMA) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "La cantidad debe estar entre 1 y 100."
                );
                return;
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La cantidad debe ser un número entero válido."
            );
            return;
        }

        // Leer la contraseña sin incluirla directamente en el código.
        String password = System.getenv("DANIFE_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            getServletContext().log(
                    "No está configurada DANIFE_DB_PASSWORD."
            );

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No está disponible la configuración de la base de datos."
            );
            return;
        }

        // Consulta parametrizada para prevenir inyección SQL.
        String sql = "INSERT INTO pedidos "
                   + "(cliente, producto, cantidad) "
                   + "VALUES (?, ?, ?)";

        try {
            // Cargar el controlador JDBC de MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Abrir la conexión y ejecutar la consulta.
            try (Connection conexion = DriverManager.getConnection(
                        URL, USUARIO, password);
                 PreparedStatement sentencia =
                        conexion.prepareStatement(sql)) {

                sentencia.setString(1, cliente);
                sentencia.setString(2, producto);
                sentencia.setInt(3, cantidad);

                int filas = sentencia.executeUpdate();

                if (filas != 1) {
                    throw new SQLException(
                            "El registro del pedido no fue confirmado."
                    );
                }
            }

            // Preparar los datos para el resumen del pedido.
            request.setAttribute("cliente", escaparHTML(cliente));
            request.setAttribute("producto", escaparHTML(producto));
            request.setAttribute("cantidad", cantidad);

            // Mostrar la confirmación del pedido registrado.
            request.getRequestDispatcher("/resultado.jsp")
                   .forward(request, response);

        } catch (ClassNotFoundException e) {
            getServletContext().log(
                    "No se encontró el controlador JDBC de MySQL.", e
            );

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No está disponible el controlador de base de datos."
            );

        } catch (SQLException e) {
            getServletContext().log(
                    "Error al registrar el pedido en MySQL.", e
            );

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No fue posible guardar el pedido. Intenta nuevamente."
            );
        }
    }

    /**
     * Elimina espacios innecesarios de los datos recibidos.
     */
    private String limpiar(String texto) {
        return texto == null ? null : texto.trim();
    }

    /**
     * Codifica caracteres especiales para mostrarlos como texto HTML.
     */
    private String escaparHTML(String texto) {
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}

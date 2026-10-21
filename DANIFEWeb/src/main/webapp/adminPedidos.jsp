
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administración de pedidos | DANIFE</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fff5f7;
            margin: 0;
            padding: 25px;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #a33b64;
        }

        p {
            text-align: center;
        }

        .contenedor {
            max-width: 1100px;
            margin: 25px auto;
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 2px 10px #00000015;
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }

        th {
            background: #a33b64;
            color: white;
        }

        tr:hover {
            background: #fff0f5;
        }

        .volver {
            display: inline-block;
            background: #a33b64;
            color: white;
            padding: 10px 18px;
            border-radius: 6px;
            text-decoration: none;
        }
    </style>
</head>

<body>
    <h1>DANIFE | Administración de pedidos</h1>
    <p>Consulta de pedidos registrados en la tienda virtual</p>

    <div class="contenedor">
        <p>
            <a class="volver" href="index.html">Volver a la tienda</a>
        </p>

        <h2>Pedidos registrados</h2>

        <%
            List<Map<String, Object>> pedidos =
                (List<Map<String, Object>>) request.getAttribute("pedidos");

            if (pedidos == null) {
        %>
            <p>Para consultar los pedidos, debemos conectar esta página con el controlador.</p>
        <%
            } else if (pedidos.isEmpty()) {
        %>
            <p>No hay pedidos registrados.</p>
        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Datos del pedido</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Map<String, Object> pedido : pedidos) { %>
                        <tr>
                            <td>
                                <%
                                    for (Map.Entry<String, Object> dato
                                            : pedido.entrySet()) {
                                %>
                                    <strong><%= dato.getKey() %>:</strong>
                                    <%= dato.getValue() == null
                                            ? "" : dato.getValue() %>
                                    &nbsp; | &nbsp;
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>
</body>
</html>

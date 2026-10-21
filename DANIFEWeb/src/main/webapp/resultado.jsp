
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumen del pedido | DANIFE</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/estilos.css">
</head>
<body>

    <header>
        <div class="logo">DANIFE</div>
    </header>

    <main class="seccion">
        <h2>¡Pedido recibido!</h2>

        <p>Cliente:
            <strong><%= request.getAttribute("cliente") != null
                    ? request.getAttribute("cliente") : "" %></strong>
        </p>

        <p>Producto:
            <strong><%= request.getAttribute("producto") != null
                    ? request.getAttribute("producto") : "" %></strong>
        </p>

        <p>Cantidad:
            <strong><%= request.getAttribute("cantidad") != null
                    ? request.getAttribute("cantidad") : "" %></strong>
        </p>

        <p>
            Gracias por comprar en DANIFE.
            Aquí puedes consultar el resumen de tu pedido.
        </p>

        <a class="boton"
           href="${pageContext.request.contextPath}/index.html">
            Volver a la tienda
        </a>
    </main>

    <footer>
        <p>DANIFE - Moda y estilo para dama</p>
    </footer>

</body>
</html>

// DANIFE - FUNCIONES DEL FRONTEND

const productos = [
    {
        id: 1,
        nombre: "Vestido elegante",
        categoria: "ropa",
        precio: 89000,
        descripcion: "Vestido para ocasiones especiales.",
        imagen: "https://images.unsplash.com/photo-1539008835657-9e8e9680c956?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 2,
        nombre: "Blusa casual",
        categoria: "ropa",
        precio: 45000,
        descripcion: "Una opción versátil para el día a día.",
        imagen: "https://images.unsplash.com/photo-1551163943-3f6a855d1153?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 3,
        nombre: "Bolso moderno",
        categoria: "bolsos",
        precio: 72000,
        descripcion: "Un complemento para tus conjuntos favoritos.",
        imagen: "https://images.unsplash.com/photo-1584917865442-de89df76afd3?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 4,
        nombre: "Zapatos elegantes",
        categoria: "zapatos",
        precio: 95000,
        descripcion: "Diseño para complementar tu estilo.",
        imagen: "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 5,
        nombre: "Tenis casuales",
        categoria: "zapatos",
        precio: 78000,
        descripcion: "Comodidad para tus actividades diarias.",
        imagen: "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 6,
        nombre: "Aretes delicados",
        categoria: "accesorios",
        precio: 25000,
        descripcion: "Un detalle sencillo para completar tu look.",
        imagen: "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 7,
        nombre: "Cartera pequeña",
        categoria: "bolsos",
        precio: 52000,
        descripcion: "Práctica para llevar tus objetos esenciales.",
        imagen: "https://images.unsplash.com/photo-1590874103328-eac38a683ce7?auto=format&fit=crop&w=700&q=80"
    },
    {
        id: 8,
        nombre: "Collar moderno",
        categoria: "accesorios",
        precio: 32000,
        descripcion: "Un accesorio para ocasiones especiales.",
        imagen: "https://images.unsplash.com/photo-1611591437281-460bfbe1220a?auto=format&fit=crop&w=700&q=80"
    }
];

const carrito = [];

const listaProductos = document.getElementById("listaProductos");
const busqueda = document.getElementById("busqueda");
const filtroCategoria = document.getElementById("filtroCategoria");
const mensajeResultados = document.getElementById("mensajeResultados");
const contadorCarrito = document.getElementById("contadorCarrito");
const modalCarrito = document.getElementById("modalCarrito");
const modalPedido = document.getElementById("modalPedido");
const contenidoCarrito = document.getElementById("contenidoCarrito");
const subtotalElemento = document.getElementById("subtotal");
const notificacion = document.getElementById("notificacion");

// Formato de precios en pesos colombianos.
function formatoPrecio(valor) {
    return valor.toLocaleString("es-CO", {
        style: "currency",
        currency: "COP",
        maximumFractionDigits: 0
    });
}

// Mostrar mensajes breves al usuario.
function mostrarNotificacion(mensaje) {
    notificacion.textContent = mensaje;
}

// Crear las tarjetas del catálogo.
function mostrarProductos() {
    const texto = busqueda.value.trim().toLowerCase();
    const categoria = filtroCategoria.value;

    const filtrados = productos.filter(function (producto) {
        const coincideTexto =
            producto.nombre.toLowerCase().includes(texto) ||
            producto.descripcion.toLowerCase().includes(texto);

        const coincideCategoria =
            categoria === "todos" || producto.categoria === categoria;

        return coincideTexto && coincideCategoria;
    });

    mensajeResultados.textContent =
        filtrados.length + (filtrados.length === 1
            ? " producto encontrado"
            : " productos encontrados");

    if (filtrados.length === 0) {
        listaProductos.innerHTML =
            '<p class="sin-resultados">No encontramos productos. Prueba con otra búsqueda.</p>';
        return;
    }

    listaProductos.innerHTML = filtrados.map(function (producto) {
        return `
            <article class="tarjeta">
                <img
                    src="${producto.imagen}"
                    alt="${producto.nombre}"
                    loading="lazy">
                <div class="tarjeta-contenido">
                    <span class="categoria">${producto.categoria}</span>
                    <h3>${producto.nombre}</h3>
                    <p class="descripcion">${producto.descripcion}</p>
                    <p class="precio">${formatoPrecio(producto.precio)}</p>
                    <button
                        class="boton principal agregar"
                        type="button"
                        data-id="${producto.id}">
                        Agregar al carrito
                    </button>
                </div>
            </article>
        `;
    }).join("");
}

// Agregar un producto o aumentar su cantidad.
function agregarAlCarrito(id) {
    const producto = productos.find(function (item) {
        return item.id === id;
    });

    if (!producto) return;

    const existente = carrito.find(function (item) {
        return item.id === id;
    });

    if (existente) {
        existente.cantidad++;
    } else {
        carrito.push({
            id: producto.id,
            nombre: producto.nombre,
            precio: producto.precio,
            cantidad: 1
        });
    }

    actualizarCarrito();
    mostrarNotificacion(producto.nombre + " se agregó al carrito.");
}

// Calcular el total de artículos.
function calcularCantidad() {
    return carrito.reduce(function (total, item) {
        return total + item.cantidad;
    }, 0);
}

// Calcular el subtotal del carrito.
function calcularSubtotal() {
    return carrito.reduce(function (total, item) {
        return total + item.precio * item.cantidad;
    }, 0);
}

// Actualizar el contenido visual del carrito.
function actualizarCarrito() {
    contadorCarrito.textContent = calcularCantidad();
    subtotalElemento.textContent = formatoPrecio(calcularSubtotal());

    if (carrito.length === 0) {
        contenidoCarrito.innerHTML =
            "<p>Tu carrito está vacío. Explora el catálogo para agregar productos.</p>";
        return;
    }

    contenidoCarrito.innerHTML = carrito.map(function (item) {
        return `
            <article class="item-carrito">
                <div>
                    <strong>${item.nombre}</strong>
                    <p>${formatoPrecio(item.precio)} por unidad</p>
                    <div class="controles-cantidad">
                        <button type="button" data-accion="menos" data-id="${item.id}"
                            aria-label="Disminuir cantidad de ${item.nombre}">−</button>
                        <span>Cantidad: ${item.cantidad}</span>
                        <button type="button" data-accion="mas" data-id="${item.id}"
                            aria-label="Aumentar cantidad de ${item.nombre}">+</button>
                    </div>
                </div>
                <div>
                    <p><strong>${formatoPrecio(item.precio * item.cantidad)}</strong></p>
                    <button class="eliminar" type="button"
                        data-accion="eliminar" data-id="${item.id}">
                        Eliminar
                    </button>
                </div>
            </article>
        `;
    }).join("");
}

// Modificar las cantidades y retirar productos.
function cambiarCantidad(id, accion) {
    const indice = carrito.findIndex(function (item) {
        return item.id === id;
    });

    if (indice === -1) return;

    if (accion === "mas") {
        carrito[indice].cantidad++;
    } else if (accion === "menos") {
        carrito[indice].cantidad--;

        if (carrito[indice].cantidad <= 0) {
            carrito.splice(indice, 1);
        }
    } else if (accion === "eliminar") {
        carrito.splice(indice, 1);
    }

    actualizarCarrito();
}

// Abrir y cerrar ventanas.
function abrirModal(modal) {
    modal.hidden = false;
}

function cerrarModal(modal) {
    modal.hidden = true;
}

document.getElementById("abrirCarrito").addEventListener("click", function () {
    actualizarCarrito();
    abrirModal(modalCarrito);
});

document.getElementById("cerrarCarrito").addEventListener("click", function () {
    cerrarModal(modalCarrito);
});

document.getElementById("cerrarPedido").addEventListener("click", function () {
    cerrarModal(modalPedido);
});

modalCarrito.addEventListener("click", function (evento) {
    if (evento.target === modalCarrito) cerrarModal(modalCarrito);
});

modalPedido.addEventListener("click", function (evento) {
    if (evento.target === modalPedido) cerrarModal(modalPedido);
});

// Detectar clics en los botones de las tarjetas.
listaProductos.addEventListener("click", function (evento) {
    const boton = evento.target.closest(".agregar");

    if (boton) {
        agregarAlCarrito(Number(boton.dataset.id));
    }
});

// Detectar acciones dentro del carrito.
contenidoCarrito.addEventListener("click", function (evento) {
    const boton = evento.target.closest("button[data-accion]");

    if (boton) {
        cambiarCantidad(Number(boton.dataset.id), boton.dataset.accion);
    }
});

// Buscador y filtro de categorías.
busqueda.addEventListener("input", mostrarProductos);
filtroCategoria.addEventListener("change", mostrarProductos);

// Vaciar el carrito.
document.getElementById("vaciarCarrito").addEventListener("click", function () {
    carrito.length = 0;
    actualizarCarrito();
    mostrarNotificacion("El carrito está vacío.");
});

// Abrir el formulario del pedido.
document.getElementById("irPedido").addEventListener("click", function () {
    if (carrito.length === 0) {
        mostrarNotificacion("Agrega al menos un producto antes de continuar.");
        return;
    }

    cerrarModal(modalCarrito);
    abrirModal(modalPedido);
});

// Validación de formulario de pedido de demostración.
document.getElementById("formPedido").addEventListener("submit", function (evento) {
    evento.preventDefault();

    if (carrito.length === 0) {
        document.getElementById("resultadoPedido").textContent =
            "El carrito está vacío. Agrega productos antes de continuar.";
        return;
    }

    document.getElementById("resultadoPedido").textContent =
        "Formulario validado. Total de demostración: " +
        formatoPrecio(calcularSubtotal()) +
        ". No se realizó una compra real.";

    mostrarNotificacion("Datos de demostración validados.");
});

// Validación del formulario de contacto.
document.getElementById("formContacto").addEventListener("submit", function (evento) {
    evento.preventDefault();

    document.getElementById("resultadoContacto").textContent =
        "El formulario se validó correctamente. Esta demostración no envía mensajes.";
});

// Menú adaptable para teléfonos.
const botonMenu = document.getElementById("botonMenu");
const menuPrincipal = document.getElementById("menuPrincipal");

botonMenu.addEventListener("click", function () {
    const abierto = menuPrincipal.classList.toggle("abierto");
    botonMenu.setAttribute("aria-expanded", String(abierto));
});

menuPrincipal.querySelectorAll("a").forEach(function (enlace) {
    enlace.addEventListener("click", function () {
        menuPrincipal.classList.remove("abierto");
        botonMenu.setAttribute("aria-expanded", "false");
    });
});

// Cerrar ventanas con la tecla Escape.
document.addEventListener("keydown", function (evento) {
    if (evento.key === "Escape") {
        cerrarModal(modalCarrito);
        cerrarModal(modalPedido);
    }
});

// Cargar productos al abrir la página.
mostrarProductos();
actualizarCarrito();
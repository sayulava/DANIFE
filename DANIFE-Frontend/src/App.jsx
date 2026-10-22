
import { useMemo, useState } from 'react';
import './App.css';

const productosIniciales = [
  { id: 1, nombre: 'Vestido elegante', categoria: 'Ropa', precio: 89900, imagen: '/vestido.jpg', descripcion: 'Elegancia para cada ocasión.' },
  { id: 2, nombre: 'Zapatos de dama', categoria: 'Zapatos', precio: 119900, imagen: '/zapatos.jpg', descripcion: 'Comodidad con mucho estilo.' },
  { id: 3, nombre: 'Bolso moderno', categoria: 'Bolsos', precio: 79900, imagen: '/bolso.jpg', descripcion: 'Tu complemento ideal.' },
  { id: 4, nombre: 'Set de accesorios', categoria: 'Accesorios', precio: 39900, imagen: '/Accesorio.jpg', descripcion: 'Detalles que resaltan tu esencia.' },
];

const formatoPrecio = (precio) =>
  precio.toLocaleString('es-CO', {
    style: 'currency',
    currency: 'COP',
    maximumFractionDigits: 0,
  });

function App() {
  const [busqueda, setBusqueda] = useState('');
  const [categoria, setCategoria] = useState('Todos');
  const [carrito, setCarrito] = useState([]);

  const productosFiltrados = useMemo(() => {
    return productosIniciales.filter((producto) => {
      const coincideNombre = producto.nombre
        .toLowerCase()
        .includes(busqueda.toLowerCase());

      const coincideCategoria =
        categoria === 'Todos' || producto.categoria === categoria;

      return coincideNombre && coincideCategoria;
    });
  }, [busqueda, categoria]);

  const agregarAlCarrito = (producto) => {
    setCarrito((actual) => [...actual, producto]);
  };

  const total = carrito.reduce((suma, producto) => suma + producto.precio, 0);

  return (
    <div className="tienda">
      <header className="encabezado">
        <a className="logo" href="#inicio">DANIFE<span>.</span></a>
        <nav>
          <a href="#inicio">Inicio</a>
          <a href="#productos">Productos</a>
          <a href="#nosotros">Nosotros</a>
          <a href="#contacto">Contacto</a>
        </nav>
        <a className="enlace-carrito" href="#carrito">
          🛍️ Mi bolsa ({carrito.length})
        </a>
      </header>

      <main>
        <section className="hero" id="inicio">
          <div className="hero-contenido">
            <p className="etiqueta">NUEVA COLECCIÓN</p>
            <h1>Tu estilo,<br />tu esencia.</h1>
            <p>Descubre prendas, bolsos, zapatos y accesorios que hablan de ti.</p>
            <a className="boton-principal" href="#productos">Explorar colección</a>
          </div>
          <div className="hero-decoracion" aria-hidden="true">
            <span>D</span>
            <p>VISTE TU ESENCIA</p>
          </div>
        </section>

        <section className="seccion-productos" id="productos">
          <div className="titulo-seccion">
            <p className="etiqueta">ENCUENTRA TU ESTILO</p>
            <h2>Nuestros productos</h2>
            <p>Detalles especiales para cada momento.</p>
          </div>

          <div className="controles">
            <input
              type="search"
              placeholder="Buscar un producto..."
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
              aria-label="Buscar productos"
            />
            <select
              value={categoria}
              onChange={(e) => setCategoria(e.target.value)}
              aria-label="Filtrar por categoría"
            >
              <option>Todos</option>
              <option>Ropa</option>
              <option>Bolsos</option>
              <option>Zapatos</option>
              <option>Accesorios</option>
            </select>
          </div>

          <div className="rejilla-productos">
            {productosFiltrados.map((producto) => (
              <article className="tarjeta-producto" key={producto.id}>
                <div className="producto-imagen">
                  <img
                    src={producto.imagen}
                    alt={producto.nombre}
                    onError={(e) => {
                      e.currentTarget.style.display = 'none';
                    }}
                  />
                  <span className="etiqueta-categoria">{producto.categoria}</span>
                </div>
                <div className="producto-info">
                  <h3>{producto.nombre}</h3>
                  <p>{producto.descripcion}</p>
                  <div className="producto-pie">
                    <strong>{formatoPrecio(producto.precio)}</strong>
                    <button onClick={() => agregarAlCarrito(producto)}>
                      Agregar +
                    </button>
                  </div>
                </div>
              </article>
            ))}
          </div>

          {productosFiltrados.length === 0 && (
            <p className="sin-resultados">No encontramos productos con esa búsqueda.</p>
          )}
        </section>

        <section className="carrito-seccion" id="carrito">
          <div>
            <p className="etiqueta">TU SELECCIÓN</p>
            <h2>Mi bolsa de compras</h2>
            {carrito.length === 0 ? (
              <p>Aún no has agregado productos.</p>
            ) : (
              <ul className="lista-carrito">
                {carrito.map((producto, indice) => (
                  <li key={`${producto.id}-${indice}`}>
                    {producto.nombre} — {formatoPrecio(producto.precio)}
                    <button
                      className="quitar"
                      onClick={() =>
                        setCarrito((actual) =>
                          actual.filter((_, i) => i !== indice)
                        )
                      }
                    >
                      Quitar
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>
          <div className="total-carrito">
            <span>Total</span>
            <strong>{formatoPrecio(total)}</strong>
            <a className="boton-principal" href="#contacto">Continuar</a>
          </div>
        </section>

        <section className="nosotros" id="nosotros">
          <p className="etiqueta">SOBRE DANIFE</p>
          <h2>Moda que refleja quién eres.</h2>
          <p>Queremos acompañarte con opciones de moda femenina para expresar tu personalidad con confianza.</p>
        </section>

        <section className="contacto" id="contacto">
          <div>
            <p className="etiqueta">¿LISTA PARA TU PRÓXIMO LOOK?</p>
            <h2>Haz tu pedido con DANIFE</h2>
            <p>Completa tu selección y comunícate con nuestra tienda para confirmar tu pedido.</p>
          </div>
          <a className="boton-principal" href="http://localhost:8080/DANIFEWeb/pedido.html">
            Ir al formulario de pedido
          </a>
        </section>
      </main>

      <footer>
        <a className="logo" href="#inicio">DANIFE<span>.</span></a>
        <p>Moda femenina para expresar tu esencia.</p>
        <small>© {new Date().getFullYear()} DANIFE. Proyecto formativo.</small>
      </footer>
    </div>
  );
}

export default App;

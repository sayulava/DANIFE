package com.danife;

public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;
    private String descripcion;

    public Producto(int id, String nombre, String categoria,
                    double precio, int cantidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }
}

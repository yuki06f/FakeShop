package modelo;

import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;
    private boolean descuento; // true/false
    private double porcentajeDescuento; // valor del desc

    public Producto(String id, String nombre, double precio, int stock, String categoria, boolean descuento, double porcentajeDescuento) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.descuento = descuento;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // obtener el precio real aplicando el descuento si existe
    public double getPrecioEfectivo() {
        if (descuento) {
            return precio - (precio * porcentajeDescuento);
        }
        return precio;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategoria() { return categoria; }
    public boolean isTieneDescuento() { return descuento; }
    public double getPorcentajeDescuento() { return porcentajeDescuento; }
}
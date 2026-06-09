package modelo;

import java.util.ArrayList;

public class Cliente extends Usuario {
    private String domicilio;
    private ArrayList<Producto> carrito;

    public Cliente(String id, String nombre, String email, String password, String domicilio) {
        //constructor del padre con admin  = false porque es un usuario normal
        super(id, nombre, email, password, false); 
        this.domicilio = domicilio;
        this.carrito = new ArrayList<>(); // iniciar el carrito vacio
    }

    // cliente edita su perfil con esta funcion
    public void editarPerfil(String nuevoNombre, String nuevaPassword, String nuevoDomicilio) {
        setNombre(nuevoNombre);
        setPassword(nuevaPassword);
        this.domicilio = nuevoDomicilio;
    }

    // metodos de carrito
    public void agregarAlCarrito(Producto prod) { this.carrito.add(prod); }
    public void limpiarCarrito() { this.carrito.clear(); }
    
    // Getters y Setters
    public String getContraseña() { return getPassword(); }
    public void setContraseña(String p) { setPassword(p); }
    
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public ArrayList<Producto> getCarrito() { return carrito; }
}
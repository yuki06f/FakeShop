package modelo;

import java.io.Serializable;

//asbatrcta ya que será la plantilla que hereda sus propiedades a clase Cliente y Admin
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L; //  versión de la serializacion
    
    private String id;
    private String nombre;
    private String email;
    private String password;
    private boolean esAdmin; // diferenciar los permisos

    public Usuario(String id, String nombre, String email, String password, boolean esAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.esAdmin = esAdmin;
    }

    // Getters y Setters 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean esAdmin() { return esAdmin; }
    public void setAdmin(boolean esAdmin) { this.esAdmin = esAdmin; }
}

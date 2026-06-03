package modelo;

import java.io.*;
import java.util.ArrayList;

public class GestionTienda {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Producto> listaProductos;

    //archivos binarios
    private final String ARCHIVO_USUARIOS  = "usuarios.dat";
    private final String ARCHIVO_PRODUCTOS = "productos.dat";

    public GestionTienda() {
        listaUsuarios  = new ArrayList<>();
        listaProductos = new ArrayList<>();
        cargarDatos();

        // Admin por defecto si no hay usuarios
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Cliente("1", "Admin", "admin@tiendamax.com", "ola123", "tienda"));
            listaUsuarios.get(0).setEsAdministrador(true);
            guardarUsuarios();
        }
    }

    public void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(listaUsuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public void guardarProductos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PRODUCTOS))) {
            oos.writeObject(listaProductos);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File fileUser = new File(ARCHIVO_USUARIOS);
        if (fileUser.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileUser))) {
                listaUsuarios = (ArrayList<Usuario>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
        File fileProd = new File(ARCHIVO_PRODUCTOS);
        if (fileProd.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileProd))) {
                listaProductos = (ArrayList<Producto>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error al cargar productos: " + e.getMessage());
            }
        }
    }

    // autenticacion
    public Usuario login(String emailONombre, String password) {
        for (Usuario u : listaUsuarios) {
            boolean matchEmail  = u.getEmail().equalsIgnoreCase(emailONombre);
            boolean matchNombre = u.getNombre().equalsIgnoreCase(emailONombre);
            if ((matchEmail || matchNombre) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // registro
    public boolean registrarCliente(String id, String nombre, String email, String password, String domicilio) {
        for (Usuario u : listaUsuarios) {
            // validar si no esta duplicado
            if (u.getEmail().equalsIgnoreCase(email) || u.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        listaUsuarios.add(new Cliente(id, nombre, email, password, domicilio));
        guardarUsuarios();
        return true;
    }

    // getters
    public ArrayList<Producto> getListaProductos() { return listaProductos; }
    public ArrayList<Usuario>  getListaUsuarios()  { return listaUsuarios; }
}
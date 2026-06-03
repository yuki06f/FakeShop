package main;

import modelo.GestionTienda;

public class main {
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception e) { }

        javax.swing.SwingUtilities.invokeLater(() -> {
            GestionTienda gestion = new GestionTienda();

            if (gestion.getListaProductos().isEmpty()) {
                cargarProductosDePrueba(gestion);
            }

            // pasar al gestor como parametro
            gui.VentanaPrincipal vp = new gui.VentanaPrincipal(gestion);
            vp.setVisible(true);

            
        });
    }

    private static void cargarProductosDePrueba(GestionTienda g) {
        java.util.ArrayList<modelo.Producto> lista = g.getListaProductos();
        lista.add(new modelo.Producto("P001", "iPhone 15 Pro",   22999, 10, "Electrónica", true,  0.10));
        lista.add(new modelo.Producto("P002", "Laptop Dell XPS", 35000,  5, "Electrónica", false, 0));
        lista.add(new modelo.Producto("P003", "Audífonos Sony",   1899, 20, "Electrónica", true,  0.15));
        lista.add(new modelo.Producto("P004", "Playera Nike",      599, 50, "Ropa",        false, 0));
        lista.add(new modelo.Producto("P005", "Jeans Levi's",     1200, 30, "Ropa",        true,  0.20));
        lista.add(new modelo.Producto("P006", "Cafetera Oster",    899, 15, "Hogar",       true,  0.05));
        lista.add(new modelo.Producto("P007", "Silla Gamer",      4500,  8, "Hogar",       false, 0));
        lista.add(new modelo.Producto("P008", "Balón de Fútbol",   450, 40, "Deportes",    false, 0));
        lista.add(new modelo.Producto("P009", "Pesas 10kg",        780, 25, "Deportes",    true,  0.10));
        lista.add(new modelo.Producto("P010", "Clean Code",        380, 60, "Libros",      false, 0));
        lista.add(new modelo.Producto("P011", "El Quijote",        220, 45, "Libros",      true,  0.25));
        lista.add(new modelo.Producto("P012", "Tablet Samsung",   8500, 12, "Electrónica", true,  0.12));
        g.guardarProductos();
    }
}
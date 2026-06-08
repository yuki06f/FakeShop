package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;

    // estados de un pedido
    public enum Estado {
        PENDIENTE_PAGO   ("Pendiente de pago"),
        PAGO_CONFIRMADO  ("Pago confirmado"),
        PREPARANDO       ("Preparando envío"),
        EN_CAMINO        ("En camino"),
        ENTREGADO        ("Entregado");

        private final String etiqueta;
        Estado(String etiqueta) { this.etiqueta = etiqueta; }
        public String getEtiqueta() { return etiqueta; }
    }

    private String              folio;
    private String              idCliente;
    private String              nombreCliente;
    private String              emailCliente;
    private ArrayList<Producto> productos;
    private double              total;
    private String              metodoPago;      
    private String              referenciaPago;  //tarjeta u efec
    private LocalDateTime       fecha;
    private Estado              estado;

    
    public Venta(String idCliente, String nombreCliente, String emailCliente, ArrayList<Producto> productos, String metodoPago, String referenciaPago) {

        this.folio = generarFolio();
        this.idCliente = idCliente;
        this.nombreCliente= nombreCliente;
        this.emailCliente = emailCliente;
        this.productos = new ArrayList<>(productos);
        this.metodoPago  = metodoPago;
        this.referenciaPago = referenciaPago;
        this.fecha = LocalDateTime.now();
        this.estado = metodoPago.equals("EFECTIVO") ? Estado.PENDIENTE_PAGO : Estado.PAGO_CONFIRMADO;

        // Calcular total con descuentos 
        this.total = productos.stream().mapToDouble(Producto::getPrecioEfectivo).sum();
    }

    // folio
    private String generarFolio() {
        return "AF-" + System.currentTimeMillis();
    }

    // hilo sguimiento
    public boolean avanzarEstado() {
        Estado[] estados = Estado.values();
        int idx = estado.ordinal();
        if (idx < estados.length - 1) {
            estado = estados[idx + 1];
            return true;   // hubo cambio
        }
        return false;      // ya esta entregado
    }

    // getters y setters
    public String getFolio() { return folio; }
    public String getIdCliente() { return idCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public String getEmailCliente() { return emailCliente; }
    public ArrayList<Producto> getProductos(){ return productos; }
    public double getTotal() { return total; }
    public String getMetodoPago() { return metodoPago; }
    public String getReferenciaPago() { return referenciaPago; }
    public LocalDateTime getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado e) { this.estado = e; }

    // fecha
    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
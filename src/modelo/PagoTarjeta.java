package modelo;

// Subclase Tarjeta
public class PagoTarjeta extends MetodoPago {
    private String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public String procesarPago(double monto) {
        // Simulación de validación y cobro bancario
        return "Pago completado! Total Cargado: $" + String.format("%.2f", monto) + " a la tarjeta con terminacion " + numeroTarjeta.substring(numeroTarjeta.length() - 4);
    }
}
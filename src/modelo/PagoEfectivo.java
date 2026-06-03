package modelo;

import java.util.Random;

// como en oxxo
public class PagoEfectivo extends MetodoPago {
    @Override
    public String procesarPago(double monto) {
        // referencia aleatoria de 12 dígitos
        Random rd = new Random();
        long referencia = 100000000000L + (long)(rd.nextDouble() * 900000000000L);
        return "Muestrale la siguiente referencia al cajero:  " + referencia + " \n y pagale: $" + String.format("%.2f", monto);
    }
}

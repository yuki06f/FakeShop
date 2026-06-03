package modelo;

import java.io.Serializable;

// Clase Base Abstracta
public abstract class MetodoPago implements Serializable {
    public abstract String procesarPago(double monto); 
}
//aqui aplicamos polimorifsmo segun el metodo de pago
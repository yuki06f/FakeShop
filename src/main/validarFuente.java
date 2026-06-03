package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class validarFuente {
    public void aplicarFuentePersonalizada() {
    try {
        // Carga el archivo de fuente
        File archivoFuente = new File("img/Montserrat.ttf ");
        Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, archivoFuente);
        
        // Ajusta el tamaño de la fuente (ejemplo: tamaño 18)
        Font fuenteFinal = fuenteBase.deriveFont(18f);
        
        // Aplica la fuente a tu componente (por ejemplo, un JLabel)
        // tuJLabel.setFont(fuenteFinal);
        
    } catch (FontFormatException | IOException e) {
        System.err.println("Error al cargar la fuente: " + e.getMessage());
    }
}
}

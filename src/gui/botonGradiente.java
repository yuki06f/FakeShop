package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class botonGradiente extends JButton {

    public botonGradiente() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE); // Color del texto
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setText("Botón"); // Texto por defecto
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Degradado de morado a naranja (Ajusta los RGB si quieres)
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(110, 30, 90), 
            getWidth(), 0, new Color(225, 100, 60)
        );
        g2.setPaint(gp);
        
        // Borde redondeado
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        
        // Dibujar el texto del botón
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}
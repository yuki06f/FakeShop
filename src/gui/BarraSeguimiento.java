package gui;

import javax.swing.*;
import java.awt.*;

public class BarraSeguimiento extends JPanel {

    private final Color COLOR_MORADO_BASE = new Color(65, 20, 60);
    private final Color COLOR_NARANJA     = new Color(225, 120, 80);
    private final Color COLOR_GRIS        = new Color(120, 110, 130);
    private final Color COLOR_VERDE       = new Color(30, 140, 70);

    public BarraSeguimiento() {
        setOpaque(false);
        setLayout(new GridLayout(1, 5, 0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        setPreferredSize(new Dimension(450, 56));
        setEstado(0); // 
    }

    public void setEstado(int idxActual) {
        removeAll();
        String[] etapas = {"Pendiente", "Pagado", "Preparando", "En camino", "Entregado"};
        for (int i = 0; i < etapas.length; i++) {
            add(crearEtapa(etapas[i], i, idxActual, etapas.length));
        }
        revalidate();
        repaint();
    }

    private JPanel crearEtapa(String nombre, int idx, int idxActual, int totalEtapas) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        boolean completado = idx <= idxActual;
        boolean activo     = idx == idxActual;

        // Círculo
        JLabel circulo = new JLabel(completado ? "●" : "○", SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int d = 18;
                int x = (getWidth() - d) / 2;
                int y = 2;
                if (activo) {
                    g2.setPaint(new GradientPaint(x, y, COLOR_MORADO_BASE, x + d, y, COLOR_NARANJA));
                    g2.fillOval(x, y, d, d);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2.getFontMetrics();
                    //g2.drawString("✔", x + (d - fm.stringWidth("✔")) / 2, y + d - fm.getDescent() - 2);
                } else if (completado) {
                    g2.setColor(new Color(160, 200, 160));
                    g2.fillOval(x, y, d, d);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2.getFontMetrics();
                    //g2.drawString("✔", x + (d - fm.stringWidth("✔")) / 2, y + d - fm.getDescent() - 2);
                } else {
                    g2.setColor(new Color(210, 205, 220));
                    g2.fillOval(x, y, d, d);
                }
                // Línea conectora
                if (idx < totalEtapas - 1) {
                    g2.setColor(completado ? new Color(160, 200, 160) : new Color(210, 205, 220));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x + d, y + d / 2, getWidth(), y + d / 2);
                }
                g2.dispose();
            }
        };
        circulo.setPreferredSize(new Dimension(0, 24));
        circulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Texto
        JLabel texto = new JLabel(nombre, SwingConstants.CENTER);
        texto.setFont(new Font("Segoe UI", activo ? Font.BOLD : Font.PLAIN, 10));
        texto.setForeground(activo ? COLOR_MORADO_BASE : completado ? COLOR_VERDE : COLOR_GRIS);
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(circulo);
        p.add(Box.createVerticalStrut(4));
        p.add(texto);
        return p;
    }
}
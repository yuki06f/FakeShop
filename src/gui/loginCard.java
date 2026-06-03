package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class loginCard extends JPanel {

    private final Color COLOR_TARJETA = new Color(248, 246, 240);

    public loginCard() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
       
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fill(new RoundRectangle2D.Double(5, 5, getWidth() - 10, getHeight() - 10, 30, 30));
        
        g2.setColor(COLOR_TARJETA);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 10, getHeight() - 10, 30, 30));
        
        g2.dispose();
    }
}
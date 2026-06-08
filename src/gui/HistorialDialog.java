package gui;

import modelo.Cliente;
import modelo.GestionVentas;
import modelo.Producto;
import modelo.Venta;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class HistorialDialog extends JDialog {

    private final Color COLOR_MORADO_OSCURO = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE   = new Color(65, 20, 60);
    private final Color COLOR_NARANJA       = new Color(225, 120, 80);
    private final Color COLOR_FONDO         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO        = Color.WHITE;
    private final Color COLOR_TEXTO         = new Color(50, 20, 50);
    private final Color COLOR_GRIS          = new Color(120, 110, 130);
    private final Color COLOR_VERDE         = new Color(30, 140, 70);

    private Cliente       cliente;
    private GestionVentas gestionVentas;

    public HistorialDialog(Frame parent, Cliente cliente,GestionVentas gestionVentas) {
        super(parent, "Mis pedidos", true);
        this.cliente       = cliente;
        this.gestionVentas = gestionVentas;

        setSize(600, 640);
        setResizable(false);
        setLocationRelativeTo(parent);
        construirUI();
    }

   private void construirUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_FONDO);
        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearCuerpo(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel crearHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_MORADO_OSCURO);
        h.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Mis pedidos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        ArrayList<Venta> ventas = gestionVentas.getVentasDeCliente(cliente.getId());
        JLabel contador = new JLabel(ventas.size() + " pedido(s)");
        contador.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contador.setForeground(new Color(200, 185, 210));

        JLabel cerrar = new JLabel("✕");
        cerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cerrar.setForeground(new Color(200, 180, 210));
        cerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
            public void mouseEntered(MouseEvent e) { cerrar.setForeground(COLOR_NARANJA); }
            public void mouseExited(MouseEvent e)  { cerrar.setForeground(new Color(200,180,210)); }
        });

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izq.setOpaque(false);
        izq.add(titulo);
        izq.add(Box.createHorizontalStrut(16));
        izq.add(contador);

        h.add(izq,    BorderLayout.WEST);
        h.add(cerrar, BorderLayout.EAST);
        return h;
    }

    private JScrollPane crearCuerpo() {
        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(COLOR_FONDO);
        cuerpo.setBorder(new EmptyBorder(20, 24, 20, 24));

        ArrayList<Venta> ventas = gestionVentas.getVentasDeCliente(cliente.getId());

        if (ventas.isEmpty()) {
            // Estado vacío

            JLabel msg = new JLabel("Aún no has realizado ningún pedido");
            msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            msg.setForeground(COLOR_GRIS);
            msg.setAlignmentX(Component.CENTER_ALIGNMENT);

            cuerpo.add(Box.createVerticalStrut(60));
            cuerpo.add(Box.createVerticalStrut(12));
            cuerpo.add(msg);
        } else {
            // Mostrar del más reciente al más antiguo
            for (int i = ventas.size() - 1; i >= 0; i--) {
                cuerpo.add(crearTarjetaVenta(ventas.get(i)));
                cuerpo.add(Box.createVerticalStrut(16));
            }
        }

        JScrollPane scroll = new JScrollPane(cuerpo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private JPanel crearTarjetaVenta(Venta v) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra
                g2.setColor(new Color(0,0,0,15));
                g2.fill(new RoundRectangle2D.Double(3,3,
                    getWidth()-3,getHeight()-3,16,16));
                // Fondo
                g2.setColor(COLOR_BLANCO);
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth()-5,getHeight()-5,16,16));
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 999));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));

        //
        JPanel filaSup = new JPanel(new BorderLayout());
        filaSup.setOpaque(false);

        JLabel lblFolio = new JLabel(v.getFolio());
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFolio.setForeground(COLOR_MORADO_BASE);

        JLabel lblFecha = new JLabel(v.getFechaFormateada());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFecha.setForeground(COLOR_GRIS);

        JLabel lblTotal = new JLabel("$" + String.format("%,.2f", v.getTotal()));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTotal.setForeground(COLOR_TEXTO);

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);
        izq.add(lblFolio);
        izq.add(Box.createVerticalStrut(2));
        izq.add(lblFecha);

        filaSup.add(izq,BorderLayout.WEST);
        filaSup.add(lblTotal, BorderLayout.EAST);
        card.add(filaSup);
        card.add(Box.createVerticalStrut(12));

        for (Producto p : v.getProductos()) {
            JPanel filaProd = new JPanel(new BorderLayout());
            filaProd.setOpaque(false);
            filaProd.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

            JLabel nomProd = new JLabel("• " + p.getNombre());
            nomProd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            nomProd.setForeground(COLOR_TEXTO);

            JLabel precProd = new JLabel("$" + String.format("%,.2f",
                p.getPrecioEfectivo()));
            precProd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            precProd.setForeground(COLOR_GRIS);

            filaProd.add(nomProd,  BorderLayout.WEST);
            filaProd.add(precProd, BorderLayout.EAST);
            card.add(filaProd);
            card.add(Box.createVerticalStrut(3));
        }

        card.add(Box.createVerticalStrut(14));

        JLabel lblMetodo = new JLabel(v.getMetodoPago().equals("EFECTIVO") ? "Efectivo (OXXO)  —  Ref: " + v.getReferenciaPago() : "Tarjeta  terminación: " + v.getReferenciaPago().substring( Math.max(0, v.getReferenciaPago().length()-4)));
        lblMetodo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMetodo.setForeground(COLOR_GRIS);
        lblMetodo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblMetodo);
        card.add(Box.createVerticalStrut(14));

        card.add(crearBarraSeguimiento(v.getEstado()));

        return card;
    }

    private JPanel crearBarraSeguimiento(Venta.Estado estadoActual) {
        Venta.Estado[] etapas = Venta.Estado.values();
        int idxActual = estadoActual.ordinal();

        JPanel barra = new JPanel(new GridLayout(1, etapas.length, 0, 0));
        barra.setOpaque(false);
        barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < etapas.length; i++) {
            barra.add(crearEtapa(etapas[i], i, idxActual));
        }

        return barra;
    }

    private JPanel crearEtapa(Venta.Estado etapa, int idx, int idxActual) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        boolean completado = idx <= idxActual;
        boolean activo     = idx == idxActual;

        // Círculo indicador
        JLabel circulo = new JLabel(completado ? "●" : "○", SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                int d = 18;
                int x = (getWidth()-d)/2;
                int y = 2;
                if (activo) {
                    // Círculo degradado para el estado actual
                    g2.setPaint(new GradientPaint(
                        x,y,COLOR_MORADO_BASE, x+d,y,COLOR_NARANJA));
                    g2.fillOval(x, y, d, d);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString("✔", x+(d-fm.stringWidth("✔"))/2,
                        y+d-fm.getDescent()-2);
                } else if (completado) {
                    g2.setColor(new Color(160, 200, 160));
                    g2.fillOval(x, y, d, d);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString("✔", x+(d-fm.stringWidth("✔"))/2,
                        y+d-fm.getDescent()-2);
                } else {
                    g2.setColor(new Color(210, 205, 220));
                    g2.fillOval(x, y, d, d);
                }
                // Línea conectora (no en el último)
                if (idx < Venta.Estado.values().length - 1) {
                    g2.setColor(completado
                        ? new Color(160, 200, 160)
                        : new Color(210, 205, 220));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x+d, y+d/2, getWidth(), y+d/2);
                }
                g2.dispose();
            }
        };
        circulo.setPreferredSize(new Dimension(0, 24));
        circulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etiqueta de texto
        // Acortar el texto para que quepa
        String textoCorto = etapa.getEtiqueta().replace("Pendiente de pago", "Pendiente")
            .replace("Pago confirmado",   "Pagado")
            .replace("Preparando envío",  "Preparando")
            .replace("En camino",         "En camino")
            .replace("Entregado",         "Entregado");

        JLabel texto = new JLabel(textoCorto, SwingConstants.CENTER);
        texto.setFont(new Font("Segoe UI", activo ? Font.BOLD : Font.PLAIN, 9));
        texto.setForeground(activo ? COLOR_MORADO_BASE
            : completado ? COLOR_VERDE : COLOR_GRIS);
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(circulo);
        p.add(Box.createVerticalStrut(4));
        p.add(texto);
        return p;
    }
}
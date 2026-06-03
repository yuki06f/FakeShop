package gui;

import modelo.Cliente;
import modelo.GestionTienda;
import modelo.GestionVentas;
import modelo.Producto;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class CarritoDialog extends JDialog {
    private final Color COLOR_MORADO_OSCURO = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE   = new Color(65, 20, 60);
    private final Color COLOR_NARANJA       = new Color(225, 120, 80);
    private final Color COLOR_FONDO         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO        = Color.WHITE;
    private final Color COLOR_TEXTO         = new Color(50, 20, 50);
    private final Color COLOR_GRIS          = new Color(120, 110, 130);

    private Cliente         cliente;
    private GestionTienda   gestion;
    private GestionVentas   gestionVentas;
    private VentanaPrincipal ventana;

    private JPanel   panelItems;
    private JLabel   lblTotal;
    private JButton  btnPagar;

    public CarritoDialog(VentanaPrincipal parent, Cliente cliente, GestionTienda gestion, GestionVentas gestionVentas) {
        super(parent, "Mi carrito", true);
        this.ventana       = parent;
        this.cliente       = cliente;
        this.gestion       = gestion;
        this.gestionVentas = gestionVentas;

        setSize(520, 620);
        setResizable(false);
        setLocationRelativeTo(parent);
        setBackground(COLOR_FONDO);

        construirUI();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_FONDO);

        root.add(crearHeader(),  BorderLayout.NORTH);
        root.add(crearCuerpo(),  BorderLayout.CENTER);
        root.add(crearFooter(),  BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel crearHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_MORADO_OSCURO);
        h.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Mi carrito");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(COLOR_BLANCO);

        JLabel cerrar = new JLabel("✕");
        cerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cerrar.setForeground(new Color(200, 180, 210));
        cerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
            public void mouseEntered(MouseEvent e) { cerrar.setForeground(COLOR_NARANJA); }
            public void mouseExited(MouseEvent e)  { cerrar.setForeground(new Color(200,180,210)); }
        });

        h.add(titulo, BorderLayout.WEST);
        h.add(cerrar, BorderLayout.EAST);
        return h;
    }

    private JScrollPane crearCuerpo() {
        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setBackground(COLOR_FONDO);
        panelItems.setBorder(new EmptyBorder(16, 20, 16, 20));

        poblarItems();

        JScrollPane scroll = new JScrollPane(panelItems);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        return scroll;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(COLOR_BLANCO);
        footer.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(220, 215, 230)),
            new EmptyBorder(16, 24, 20, 24)
        ));

        // Total
        double total = calcularTotal();
        lblTotal = new JLabel("Total:  $" + String.format("%,.2f", total));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setForeground(COLOR_MORADO_BASE);

        // Botón pagar
        btnPagar = crearBoton("Proceder al pago →", !cliente.getCarrito().isEmpty());
        btnPagar.addActionListener(e -> abrirPago());

        // Botón vaciar
        JButton btnVaciar = new JButton("Vaciar carrito");
        btnVaciar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnVaciar.setForeground(COLOR_GRIS);
        btnVaciar.setBorderPainted(false);
        btnVaciar.setContentAreaFilled(false);
        btnVaciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVaciar.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres vaciar el carrito?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                cliente.limpiarCarrito();
                ventana.actualizarContadorCarrito();
                refrescar();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnVaciar);
        panelBotones.add(btnPagar);

        footer.add(lblTotal,      BorderLayout.WEST);
        footer.add(panelBotones,  BorderLayout.EAST);
        return footer;
    }

    
    private void poblarItems() {
        panelItems.removeAll();
        ArrayList<Producto> carrito = cliente.getCarrito();

        if (carrito.isEmpty()) {
            JLabel vacio = new JLabel("Tu carrito está vacío.");
            vacio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vacio.setForeground(COLOR_GRIS);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelItems.add(Box.createVerticalStrut(40));
            panelItems.add(vacio);
        } else {
            for (int i = 0; i < carrito.size(); i++) {
                panelItems.add(crearItemCarrito(carrito.get(i), i));
                panelItems.add(Box.createVerticalStrut(10));
            }
        }

        panelItems.revalidate();
        panelItems.repaint();
    }

    private JPanel crearItemCarrito(Producto p, int index) {
        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setBackground(COLOR_BLANCO);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        item.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 225, 240), 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));

        // Ícono

        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel nombre = new JLabel(p.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nombre.setForeground(COLOR_TEXTO);

        JLabel cat = new JLabel(p.getCategoria());
        cat.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cat.setForeground(COLOR_GRIS);

        JLabel precio = new JLabel("$" + String.format("%,.2f", p.getPrecioEfectivo()));
        precio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        precio.setForeground(COLOR_MORADO_BASE);

        info.add(nombre);
        info.add(Box.createVerticalStrut(2));
        info.add(cat);
        info.add(Box.createVerticalStrut(4));
        info.add(precio);

        // Botón eliminar
        JLabel btnEliminar = new JLabel("🗑");
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setToolTipText("Eliminar del carrito");
        btnEliminar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cliente.getCarrito().remove(index);
                ventana.actualizarContadorCarrito();
                refrescar();
            }
        });

        item.add(info,        BorderLayout.CENTER);
        item.add(btnEliminar, BorderLayout.EAST);

        return item;
    }

    // ─── Refrescar tras cambios ───────────────────────────────────
    private void refrescar() {
        poblarItems();
        double total = calcularTotal();
        if (lblTotal != null)
            lblTotal.setText("Total:  $" + String.format("%,.2f", total));
        if (btnPagar != null)
            btnPagar.setEnabled(!cliente.getCarrito().isEmpty());
    }

    // ─── Abrir diálogo de pago ────────────────────────────────────
    private void abrirPago() {
        PagoDialog pago = new PagoDialog(
            (Frame) getOwner(), cliente, gestion, gestionVentas, ventana);
        dispose(); // cierra el carrito
        pago.setVisible(true);
    }

    // ─── Helpers ─────────────────────────────────────────────────
    private double calcularTotal() {
        return cliente.getCarrito().stream()
            .mapToDouble(Producto::getPrecioEfectivo).sum();
    }

    private JButton crearBoton(String texto, boolean habilitado) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = isEnabled() ? COLOR_MORADO_BASE : new Color(180,170,190);
                Color c2 = isEnabled() ? COLOR_NARANJA     : new Color(190,185,195);
                g2.setPaint(new GradientPaint(0,0,c1,getWidth(),0,c2));
                g2.fill(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),12,12));
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(180, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setEnabled(habilitado);
        return btn;
    }
}
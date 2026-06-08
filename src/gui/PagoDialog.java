package gui;

import modelo.Cliente;
import modelo.GestionTienda;
import modelo.GestionVentas;
import modelo.MetodoPago;
import modelo.PagoEfectivo;
import modelo.PagoTarjeta;
import modelo.Venta;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class PagoDialog extends JDialog {

    private final Color COLOR_MORADO_OSCURO = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE   = new Color(65, 20, 60);
    private final Color COLOR_NARANJA       = new Color(225, 120, 80);
    private final Color COLOR_FONDO         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO        = Color.WHITE;
    private final Color COLOR_TEXTO         = new Color(50, 20, 50);
    private final Color COLOR_GRIS          = new Color(120, 110, 130);
    private final Color COLOR_VERDE         = new Color(30, 140, 70);

    private Cliente          cliente;
    private GestionTienda    gestion;
    private GestionVentas    gestionVentas;
    private VentanaPrincipal ventana;

    private JPanel      panelContenido;  // CardLayout para pasos
    private CardLayout  pasos;
    private ButtonGroup grupoMetodo;
    private JTextField  txtTarjeta;
    private JTextField  txtNombre;
    private JTextField  txtCVV;
    private JTextField  txtExpiracion;
    private JLabel      lblResumenTotal;

    private static final String PASO_METODO = "metodo";
    private static final String PASO_DATOS = "datos";
    private static final String PASO_CONFIRMAR = "confirmar";
    private static final String PASO_EXITO = "exito";

    public PagoDialog(Frame parent, Cliente cliente, GestionTienda gestion, GestionVentas gestionVentas, VentanaPrincipal ventana) {
        super(parent, "Proceso de pago", true);
        this.cliente  = cliente;
        this.gestion  = gestion;
        this.gestionVentas = gestionVentas;
        this.ventana = ventana;

        setSize(500, 580);
        setResizable(false);
        setLocationRelativeTo(parent);

        construirUI();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_FONDO);

        root.add(crearHeader(), BorderLayout.NORTH);

        pasos = new CardLayout();
        panelContenido = new JPanel(pasos);
        panelContenido.setBackground(COLOR_FONDO);

        panelContenido.add(crearPasoMetodo(),    PASO_METODO);
        panelContenido.add(crearPasoDatos(),     PASO_DATOS);
        panelContenido.add(crearPasoConfirmar(), PASO_CONFIRMAR);
        panelContenido.add(crearPasoExito(),     PASO_EXITO);

        root.add(panelContenido, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel crearHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_MORADO_OSCURO);
        h.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Proceso de pago");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(COLOR_BLANCO);

        double total = cliente.getCarrito().stream()
            .mapToDouble(modelo.Producto::getPrecioEfectivo).sum();
        JLabel lTotal = new JLabel("Total: $" + String.format("%,.2f", total));
        lTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lTotal.setForeground(new Color(255, 210, 160));

        h.add(titulo, BorderLayout.WEST);
        h.add(lTotal, BorderLayout.EAST);
        return h;
    }

    //elegir metodo de pago
    private JPanel crearPasoMetodo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_FONDO);
        p.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lbl = new JLabel("Elige tu método de pago: ");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(24));

        grupoMetodo = new ButtonGroup();

        // Opción OXXO / Efectivo
        JPanel cardEfectivo = crearOpcionPago(
            "oxxo", "Pago en efectivo (OXXO)",
            "Genera una referencia de 12 dígitos para pagar en caja");
        p.add(cardEfectivo);
        p.add(Box.createVerticalStrut(16));

        // Opción Tarjeta
        JPanel cardTarjeta = crearOpcionPago(
            "tarjeta", "Tarjeta de débito / crédito",
            "Ingresa los datos de tu tarjeta de forma segura");
        p.add(cardTarjeta);
        p.add(Box.createVerticalGlue());

        // Botón siguiente
        JButton btnSig = crearBotonGradiente("Continuar");
        btnSig.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSig.addActionListener(e -> {
            String sel = grupoMetodo.getSelection() != null
                ? grupoMetodo.getSelection().getActionCommand() : null;
            if (sel == null) {
                JOptionPane.showMessageDialog(this,
                    "Selecciona un método de pago.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (sel.equals("tarjeta")) {
                pasos.show(panelContenido, PASO_DATOS);
            } else {
                pasos.show(panelContenido, PASO_CONFIRMAR);
                actualizarResumen("EFECTIVO", "");
            }
        });
        p.add(Box.createVerticalStrut(20));
        p.add(btnSig);
        return p;
    }

    private JPanel crearOpcionPago(String cmd, String titulo, String desc) {
        JRadioButton radio = new JRadioButton();
        radio.setActionCommand(cmd);
        radio.setOpaque(false);
        grupoMetodo.add(radio);

        JPanel card = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_BLANCO);
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth(),getHeight(),14,14));
                if (radio.isSelected()) {
                    g2.setColor(new Color(225,120,80,60));
                    g2.fill(new RoundRectangle2D.Double(0,0,
                        getWidth(),getHeight(),14,14));
                    g2.setColor(COLOR_NARANJA);
                    g2.setStroke(new BasicStroke(2));
                    g2.draw(new RoundRectangle2D.Double(1,1,
                        getWidth()-2,getHeight()-2,14,14));
                }
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTit.setForeground(COLOR_TEXTO);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(COLOR_GRIS);

        textos.add(lblTit);
        textos.add(Box.createVerticalStrut(3));
        textos.add(lblDesc);

        card.add(radio,  BorderLayout.WEST);
        card.add(textos, BorderLayout.CENTER);

        // Click en la tarjeta selecciona el radio y redibuja
        MouseAdapter ma = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                radio.setSelected(true);
                card.repaint();
            }
        };
        card.addMouseListener(ma);
        textos.addMouseListener(ma);

        return card;
    }

    //si eligio tarjeta
    private JPanel crearPasoDatos() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_FONDO);
        p.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lbl = new JLabel("Datos de tu tarjeta");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lbl);
        p.add(Box.createVerticalStrut(20));

        txtNombre    = agregarCampo(p, "Nombre en la tarjeta", "JUAN PÉREZ");
        txtTarjeta   = agregarCampo(p, "Número de tarjeta",    "0000 0000 0000 0000");
        txtExpiracion= agregarCampo(p, "Fecha de expiración",  "MM/AA");
        txtCVV       = agregarCampo(p, "CVV",                  "•••");

        p.add(Box.createVerticalGlue());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        botones.setOpaque(false);
        botones.setAlignmentX(Component.LEFT_ALIGNMENT);
        botones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton btnAtras = new JButton("← Atrás");
        btnAtras.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAtras.setForeground(COLOR_GRIS);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAtras.addActionListener(e -> pasos.show(panelContenido, PASO_METODO));

        JButton btnSig = crearBotonGradiente("Confirmar →");
        btnSig.addActionListener(e -> {
            if (!validarDatosTarjeta()) return;
            String num = txtTarjeta.getText().replaceAll("\\s", "");
            actualizarResumen("TARJETA", num);
            pasos.show(panelContenido, PASO_CONFIRMAR);
        });

        botones.add(btnAtras);
        botones.add(btnSig);
        p.add(botones);
        return p;
    }

    private JTextField agregarCampo(JPanel parent, String label, String ph) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(COLOR_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lbl);
        parent.add(Box.createVerticalStrut(4));

        JTextField campo = new JTextField(ph);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(COLOR_GRIS);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 200, 220), 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));

        String placeholder = ph;
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_GRIS);
                }
            }
        });

        parent.add(campo);
        parent.add(Box.createVerticalStrut(14));
        return campo;
    }

    private boolean validarDatosTarjeta() {
        String num  = txtTarjeta.getText().replaceAll("\\s", "");
        String nombre = txtNombre.getText().trim();
        String exp  = txtExpiracion.getText().trim();
        String cvv  = txtCVV.getText().trim();

        if (nombre.isEmpty() || nombre.equals("JUAN PÉREZ")) {
            error("Ingresa el nombre en la tarjeta."); return false; }
        if (num.length() < 13 || !num.matches("\\d+")) {
            error("Número de tarjeta inválido (solo dígitos, mín. 13)."); return false; }
        if (!exp.matches("\\d{2}/\\d{2}")) {
            error("Fecha inválida. Usa el formato MM/AA."); return false; }
        if (cvv.length() < 3 || !cvv.matches("\\d+")) {
            error("CVV inválido."); return false; }
        return true;
    }
    
    //confirmacion
    private JPanel panelResumen; // actualizarResumen()

    private JPanel crearPasoConfirmar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_FONDO);
        p.setBorder(new EmptyBorder(24, 40, 24, 40));

        panelResumen = new JPanel();
        panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));
        panelResumen.setBackground(COLOR_FONDO);

        JScrollPane scroll = new JScrollPane(panelResumen);
        scroll.setBorder(null);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        botones.setOpaque(false);

        JButton btnAtras = new JButton("Atrás");
        btnAtras.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAtras.setForeground(COLOR_GRIS);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAtras.addActionListener(e -> {
            String sel = grupoMetodo.getSelection() != null ? grupoMetodo.getSelection().getActionCommand() : "oxxo";
            pasos.show(panelContenido, sel.equals("tarjeta") ? PASO_DATOS : PASO_METODO);
        });

        JButton btnConfirmar = crearBotonGradiente("Confirmar compra");
        btnConfirmar.addActionListener(e -> procesarCompra());

        botones.add(btnAtras);
        botones.add(btnConfirmar);

        p.add(scroll,   BorderLayout.CENTER);
        p.add(botones,  BorderLayout.SOUTH);
        return p;
    }

    private void actualizarResumen(String metodo, String refExtra) {
        panelResumen.removeAll();

        JLabel titulo = new JLabel("Resumen del pedido");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResumen.add(titulo);
        panelResumen.add(Box.createVerticalStrut(14));

        // Productos
        for (modelo.Producto prod : cliente.getCarrito()) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setOpaque(false);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            JLabel nombre = new JLabel("• " + prod.getNombre());
            nombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            JLabel precio = new JLabel("$" + String.format("%,.2f",
                prod.getPrecioEfectivo()));
            precio.setFont(new Font("Segoe UI", Font.BOLD, 13));

            fila.add(nombre, BorderLayout.WEST);
            fila.add(precio, BorderLayout.EAST);
            panelResumen.add(fila);
            panelResumen.add(Box.createVerticalStrut(4));
        }

        // Separador
        panelResumen.add(Box.createVerticalStrut(10));
        panelResumen.add(new JSeparator());
        panelResumen.add(Box.createVerticalStrut(10));

        // Total
        double total = cliente.getCarrito().stream().mapToDouble(modelo.Producto::getPrecioEfectivo).sum();
        JPanel filaTot = new JPanel(new BorderLayout());
        filaTot.setOpaque(false);
        filaTot.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        JLabel lTot = new JLabel("TOTAL");
        lTot.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblResumenTotal = new JLabel("$" + String.format("%,.2f", total));
        lblResumenTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResumenTotal.setForeground(COLOR_MORADO_BASE);
        filaTot.add(lTot,           BorderLayout.WEST);
        filaTot.add(lblResumenTotal, BorderLayout.EAST);
        panelResumen.add(filaTot);
        panelResumen.add(Box.createVerticalStrut(16));

        // Método
        JLabel lMet = new JLabel("Método: " + (metodo.equals("EFECTIVO") ? "Efectivo (OXXO)" : "Tarjeta"));
        lMet.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lMet.setForeground(COLOR_GRIS);
        lMet.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResumen.add(lMet);

        if (metodo.equals("TARJETA") && refExtra.length() >= 4) {
            JLabel lCard = new JLabel("Tarjeta terminación: "
                + refExtra.substring(refExtra.length()-4));
            lCard.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lCard.setForeground(COLOR_GRIS);
            lCard.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelResumen.add(lCard);
        }

        panelResumen.revalidate();
        panelResumen.repaint();
    }

    //exitooso
    private JLabel lblFolio;
    private JLabel lblRefPago;

    private JPanel crearPasoExito() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_FONDO);
        p.setBorder(new EmptyBorder(40, 50, 40, 50));

        JLabel ico = new JLabel("", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.PLAIN, 56));
        ico.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Compra realizada correctamente");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(COLOR_VERDE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblFolio = new JLabel("Folio: ---");
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFolio.setForeground(COLOR_TEXTO);
        lblFolio.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblRefPago = new JLabel(" ");
        lblRefPago.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRefPago.setForeground(COLOR_GRIS);
        lblRefPago.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCorreo = new JLabel("Se enviará un comprobante a tu correo.");
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCorreo.setForeground(COLOR_GRIS);
        lblCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTicket = new JLabel("Ticket guardado en carpeta /tickets/");
        lblTicket.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTicket.setForeground(new Color(160, 150, 170));
        lblTicket.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCerrar = crearBotonGradiente("Cerrar");
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> {
            ventana.actualizarContadorCarrito();
            dispose();
        });

        p.add(ico);
        p.add(Box.createVerticalStrut(12));
        p.add(titulo);
        p.add(Box.createVerticalStrut(16));
        p.add(lblFolio);
        p.add(Box.createVerticalStrut(8));
        p.add(lblRefPago);
        p.add(Box.createVerticalStrut(8));
        p.add(lblCorreo);
        p.add(Box.createVerticalStrut(4));
        p.add(lblTicket);
        p.add(Box.createVerticalGlue());
        p.add(btnCerrar);
        return p;
    }

    //procesar copra
    private void procesarCompra() {
        String metodo = grupoMetodo.getSelection().getActionCommand()
            .equals("tarjeta") ? "TARJETA" : "EFECTIVO";

        // Usar las clases MetodoPago ya existentes en el modelo
        MetodoPago mp;
        String referencia;

        if (metodo.equals("TARJETA")) {
            String num = txtTarjeta.getText().replaceAll("\\s", "");
            mp = new PagoTarjeta(num);
            referencia = num;
        } else {
            mp = new PagoEfectivo();
            referencia = generarReferenciaOXXO();
        }

        String mensajePago = mp.procesarPago(
            cliente.getCarrito().stream()
                .mapToDouble(modelo.Producto::getPrecioEfectivo).sum()
        );

        // Registrar la venta en el modelo
        Venta venta = gestionVentas.registrarVenta(cliente, metodo, referencia);

        // Guardar cambios de stock //excepcion
        gestion.guardarProductos();

        // Limpiar el carrito
        cliente.limpiarCarrito();
        gestion.guardarUsuarios();

        // Mostrar pantalla de confrmacion
        lblFolio.setText("Folio: " + venta.getFolio());
        lblRefPago.setText("<html><center>" + mensajePago + "</center></html>");
        pasos.show(panelContenido, PASO_EXITO);
    }

    private String generarReferenciaOXXO() {
        long r = 100000000000L +
            (long)(new java.util.Random().nextDouble() * 900000000000L);
        return String.valueOf(r);
    }

    private JButton crearBotonGradiente(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(
                    0,0,COLOR_MORADO_BASE, getWidth(),0,COLOR_NARANJA));
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth(),getHeight(),12,12));
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
        btn.setPreferredSize(new Dimension(190, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.WARNING_MESSAGE);
    }
}
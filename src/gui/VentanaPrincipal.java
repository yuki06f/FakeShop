package gui;

import modelo.Cliente;
import modelo.GestionTienda;
import modelo.Producto;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {

    private final Color COLOR_MORADO_OSCURO     = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE       = new Color(65, 20, 60);
    private final Color COLOR_NARANJA           = new Color(225, 120, 80);
    private final Color COLOR_FONDO_APP         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO            = Color.WHITE;
    private final Color COLOR_TEXTO             = new Color(50, 20, 50);
    private final Color COLOR_TEXTO_SECUNDARIO  = new Color(120, 110, 130);

    private GestionTienda gestion;
    private Usuario       usuarioActual = null;   // null = invitado

    private JLabel    lblLoginRef;      // botón "Mi Cuenta" / nombre usuario
    private JLabel    lblCarritoRef;    // botón "Carrito (N)"
    private JTextField txtBuscar;       // buscador en tiempo real
    private JPanel    gridOfertas;      // grid de "Ofertas Flash"
    private JPanel    gridVendidos;     // grid de "Lo más vendido"
    private JLabel    lblBannerTitulo;  // título del banner (carrusel)
    private JLabel    lblBannerSub;     // subtítulo del banner
    private int       carruselIdx = 0;  // índice del hilo del carrusel

    public VentanaPrincipal(GestionTienda gestion) {
        this.gestion = gestion;
        setTitle("TiendaMax - Inicio");
        setSize(1200, 800);
        setMinimumSize(new Dimension(900, 600));   // responsivo mínimo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO_APP);

        iniciarComponentes();
        iniciarCarrusel();   // hilo en segundo plano
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        add(crearCabecera(), BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(COLOR_FONDO_APP);
        panelCentral.setBorder(new EmptyBorder(20, 40, 20, 40));

        panelCentral.add(crearBannerPromocional());
        panelCentral.add(Box.createVerticalStrut(30));
        panelCentral.add(crearSeccionConGrid("Ofertas Flash",  true));
        panelCentral.add(Box.createVerticalStrut(30));
        panelCentral.add(crearSeccionConGrid("Lo más vendido", false));

        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearCabecera() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_MORADO_OSCURO);
        header.setBorder(new EmptyBorder(15, 40, 15, 40));

        // Logo
        JLabel lblLogo = new JLabel("TiendaMax");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setForeground(COLOR_BLANCO);

        // Buscador
        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBuscador.setOpaque(false);

        txtBuscar = new JTextField(" Buscar productos...");
        txtBuscar.setPreferredSize(new Dimension(400, 40));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setForeground(COLOR_TEXTO_SECUNDARIO);
        txtBuscar.setBorder(new LineBorder(COLOR_MORADO_OSCURO, 2));

        // Placeholder y búsqueda en tiempo real
        txtBuscar.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().trim().equals("Buscar productos...")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(COLOR_TEXTO);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText(" Buscar productos...");
                    txtBuscar.setForeground(COLOR_TEXTO_SECUNDARIO);
                }
            }
        });
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrarProductos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrarProductos(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarProductos(); }
        });

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(80, 40));
        btnBuscar.setBackground(COLOR_NARANJA);
        btnBuscar.setForeground(COLOR_BLANCO);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> filtrarProductos());

        panelBuscador.add(txtBuscar);
        panelBuscador.add(btnBuscar);

        // Botones derecha — guardamos referencias para actualizarlos
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 5));
        panelBotones.setOpaque(false);

        lblLoginRef = new JLabel("Mi Cuenta");
        lblLoginRef.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLoginRef.setForeground(COLOR_BLANCO);
        lblLoginRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLoginRef.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)  { accionMiCuenta(); }
            public void mouseEntered(MouseEvent e)  { lblLoginRef.setForeground(new Color(255, 200, 120)); }
            public void mouseExited(MouseEvent e)   { lblLoginRef.setForeground(COLOR_BLANCO); }
        });

        lblCarritoRef = new JLabel("Carrito (0)");
        lblCarritoRef.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCarritoRef.setForeground(COLOR_BLANCO);
        lblCarritoRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCarritoRef.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)  { accionCarrito(); }
            public void mouseEntered(MouseEvent e)  { lblCarritoRef.setForeground(new Color(255, 200, 120)); }
            public void mouseExited(MouseEvent e)   { lblCarritoRef.setForeground(COLOR_BLANCO); }
        });

        panelBotones.add(lblLoginRef);
        panelBotones.add(lblCarritoRef);

        header.add(lblLogo,        BorderLayout.WEST);
        header.add(panelBuscador,  BorderLayout.CENTER);
        header.add(panelBotones,   BorderLayout.EAST);

        return header;
    }
       
    //carrusel
    private static final String[][] BANNERS = {
        {"?Ofertas del día",         "Hasta 50% OFF en productos seleccionados — solo hoy"},
        {"Envío gratis",            "En compras mayores a $500 este fin de semana"},
        {"Bienvenida",              "Regístrate y obtén descuento exclusivo en tu primera compra"}
    };

    private JPanel crearBannerPromocional() {
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, COLOR_MORADO_BASE,
                    getWidth(), 0, COLOR_NARANJA
                );
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setOpaque(false);
        banner.setPreferredSize(new Dimension(0, 220));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        banner.setBorder(new EmptyBorder(40, 50, 40, 50));

        lblBannerTitulo = new JLabel(BANNERS[0][0]);
        lblBannerTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBannerTitulo.setForeground(COLOR_BLANCO);

        lblBannerSub = new JLabel(BANNERS[0][1]);
        lblBannerSub.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBannerSub.setForeground(new Color(255, 255, 255, 210));

        // Puntos indicadores
        JPanel puntos = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        puntos.setOpaque(false);
        puntos.setName("puntosBanner");
        for (int i = 0; i < BANNERS.length; i++) {
            JLabel p = new JLabel(i == 0 ? "●" : "○");
            p.setFont(new Font("SansSerif", Font.PLAIN, 14));
            p.setForeground(Color.WHITE);
            puntos.add(p);
        }

        banner.add(lblBannerTitulo);
        banner.add(Box.createVerticalStrut(10));
        banner.add(lblBannerSub);
        banner.add(Box.createVerticalStrut(16));
        banner.add(puntos);

        return banner;
    }

    private JPanel crearSeccionConGrid(String titulo, boolean soloOfertas) {
        JPanel seccion = new JPanel(new BorderLayout(0, 15));
        seccion.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO);
        seccion.add(lblTitulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        grid.setOpaque(false);

        // Guardar referencia según la sección
        if (soloOfertas) {
            gridOfertas = grid;
        } else {
            gridVendidos = grid;
        }

        // Poblar con datos reales
        List<Producto> productos = gestion.getListaProductos();
        if (soloOfertas) {
            productos = productos.stream()
                .filter(Producto::isTieneDescuento)
                .collect(Collectors.toList());
        }

        if (productos.isEmpty()) {
            grid.add(new JLabel("No hay productos disponibles aún."));
        } else {
            for (Producto p : productos) {
                grid.add(crearTarjetaProducto(p));
            }
        }

        seccion.add(grid, BorderLayout.CENTER);
        return seccion;
    }
    
    private JPanel crearTarjetaProducto(Producto p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(COLOR_BLANCO);
        tarjeta.setPreferredSize(new Dimension(200, 290));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(235, 230, 240), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Área de imagen / ícono
        JPanel imgPlaceholder = new JPanel(new BorderLayout());
        imgPlaceholder.setBackground(new Color(250, 245, 252));
        imgPlaceholder.setPreferredSize(new Dimension(170, 140));
        imgPlaceholder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // Badge de descuento (solo si tiene)
        if (p.isTieneDescuento()) {
            JLabel badge = new JLabel(" -" + (int)(p.getPorcentajeDescuento() * 100) + "% ");
            badge.setOpaque(true);
            badge.setBackground(COLOR_NARANJA);
            badge.setForeground(COLOR_BLANCO);
            badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
            imgPlaceholder.add(badge, BorderLayout.NORTH);
        }

        // Nombre
        JLabel lblNombre = new JLabel(
            "<html><div style='text-align:center;width:160px'>" + p.getNombre() + "</div></html>",
            SwingConstants.CENTER);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(COLOR_TEXTO);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Precio efectivo
        JLabel lblPrecio = new JLabel("$" + String.format("%.2f", p.getPrecioEfectivo()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblPrecio.setForeground(COLOR_MORADO_BASE);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Precio tachado si tiene descuento
        JLabel lblOriginal = new JLabel(p.isTieneDescuento()
            ? "<html><strike>$" + String.format("%.2f", p.getPrecio()) + "</strike></html>"
            : " ");
        lblOriginal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblOriginal.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblOriginal.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stock
        JLabel lblStock = new JLabel(p.getStock() > 0
            ? "En stock (" + p.getStock() + ")"
            : "Agotado");
        lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStock.setForeground(p.getStock() > 0
            ? new Color(40, 140, 70) : new Color(190, 50, 50));
        lblStock.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(imgPlaceholder);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblPrecio);
        tarjeta.add(lblOriginal);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblStock);

        // Hover: borde naranja (igual que tu compañero)
        tarjeta.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(COLOR_NARANJA, 1, true),
                    new EmptyBorder(15, 15, 15, 15)));
            }
            public void mouseExited(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(235, 230, 240), 1, true),
                    new EmptyBorder(15, 15, 15, 15)));
            }
            // Clic: abrir detalle o pedir login
            public void mouseClicked(MouseEvent e) {
                accionVerProducto(p);
            }
        });

        return tarjeta;
    }

    //hilo carrusel
    private void iniciarCarrusel() {
        Thread hilo = new Thread(() -> {
            while (true) {
                try { Thread.sleep(4000); } catch (InterruptedException ex) { break; }
                carruselIdx = (carruselIdx + 1) % BANNERS.length;
                final int idx = carruselIdx;

                SwingUtilities.invokeLater(() -> {
                    lblBannerTitulo.setText(BANNERS[idx][0]);
                    lblBannerSub.setText(BANNERS[idx][1]);

                    // Actualizar puntos indicadores
                    Component contenido = ((BorderLayout) getContentPane()
                        .getLayout()).getLayoutComponent(BorderLayout.CENTER);
                    if (contenido instanceof JScrollPane) {
                        actualizarPuntos(
                            ((JScrollPane) contenido).getViewport().getView(), idx);
                    }
                });
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    private void actualizarPuntos(Component c, int idx) {
        if (c instanceof JPanel) {
            JPanel p = (JPanel) c;
            if ("puntosBanner".equals(p.getName())) {
                Component[] hijos = p.getComponents();
                for (int i = 0; i < hijos.length; i++) {
                    ((JLabel) hijos[i]).setText(i == idx ? "●" : "○");
                }
                return;
            }
            for (Component hijo : p.getComponents()) {
                actualizarPuntos(hijo, idx);
            }
        }
    }

    //entregado
    private void filtrarProductos() {
        String query = txtBuscar.getText().trim().toLowerCase();
        boolean buscando = !query.isEmpty() && !query.equals("buscar productos...");

        List<Producto> todos = gestion.getListaProductos();

        List<Producto> ofertas = todos.stream()
            .filter(Producto::isTieneDescuento)
            .filter(p -> !buscando
                || p.getNombre().toLowerCase().contains(query)
                || p.getCategoria().toLowerCase().contains(query))
            .collect(Collectors.toList());

        List<Producto> todos2 = todos.stream()
            .filter(p -> !buscando
                || p.getNombre().toLowerCase().contains(query)
                || p.getCategoria().toLowerCase().contains(query))
            .collect(Collectors.toList());

        repoblarGrid(gridOfertas, ofertas);
        repoblarGrid(gridVendidos, todos2);
    }

    private void repoblarGrid(JPanel grid, List<Producto> lista) {
        if (grid == null) return;
        grid.removeAll();
        if (lista.isEmpty()) {
            JLabel vacio = new JLabel("No se encontraron productos.");
            vacio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            vacio.setForeground(COLOR_TEXTO_SECUNDARIO);
            grid.add(vacio);
        } else {
            for (Producto p : lista) {
                grid.add(crearTarjetaProducto(p));
            }
        }
        grid.revalidate();
        grid.repaint();
    }

    /** Llamado desde autenticacion.java tras login exitoso */
    public void setUsuario(Usuario u) {
        this.usuarioActual = u;
        if (u != null) {
            lblLoginRef.setText("👤 " + u.getNombre());
        } else {
            lblLoginRef.setText("👤 Mi Cuenta");
            lblCarritoRef.setText("Carrito (0)");
        }
    }

    /** Actualiza el contador del carrito en la barra */
    public void actualizarContadorCarrito() {
        if (usuarioActual instanceof Cliente) {
            int n = ((Cliente) usuarioActual).getCarrito().size();
            lblCarritoRef.setText("🛒 Carrito (" + n + ")");
        }
    }

    private void accionMiCuenta() {
        if (usuarioActual == null) {
            // Abrir ventana de autenticación
            autenticacion auth = new autenticacion(gestion, this);
            auth.setVisible(true);
        } else {
            String[] opciones = {"Mi perfil", "Mis pedidos", "Cerrar sesión"};
            int r = JOptionPane.showOptionDialog(this,
                "Bienvenido, " + usuarioActual.getNombre(),
                "Mi cuenta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);

            if (r == 0) {
                // TODO: new PerfilDialog(this, usuarioActual, gestion).setVisible(true);
                JOptionPane.showMessageDialog(this, "PerfilDialog — próximamente");
            } else if (r == 1) {
                // TODO: new HistorialDialog(this, usuarioActual).setVisible(true);
                JOptionPane.showMessageDialog(this, "HistorialDialog — próximamente");
            } else if (r == 2) {
                setUsuario(null);
            }
        }
    }

    private void accionCarrito() {
        if (usuarioActual == null) {
            int r = JOptionPane.showConfirmDialog(this,
                "Necesitas iniciar sesión para ver tu carrito.\n¿Deseas ingresar?",
                "Inicia sesión",
                JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) accionMiCuenta();
        } else {
            // TODO: new CarritoDialog(this, (Cliente) usuarioActual, gestion).setVisible(true);
            JOptionPane.showMessageDialog(this, "CarritoDialog — próximamente");
        }
    }

    private void accionVerProducto(Producto p) {
        if (usuarioActual == null) {
            // Invitado: puede ver detalle pero no comprar
            int r = JOptionPane.showOptionDialog(this,
                p.getNombre() + "\nPrecio: $" + String.format("%.2f", p.getPrecioEfectivo())
                + "\nStock: " + p.getStock()
                + "\n\nInicia sesión para agregar al carrito.",
                "Detalle del producto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Iniciar sesión", "Cerrar"},
                "Cerrar");
            if (r == 0) accionMiCuenta();
        } else {
            // TODO: new ProductoDetalleDialog(this, p, (Cliente) usuarioActual, gestion).setVisible(true);
            // Por ahora agrega directo al carrito como demo
            if (usuarioActual instanceof Cliente) {
                ((Cliente) usuarioActual).agregarAlCarrito(p);
                actualizarContadorCarrito();
                JOptionPane.showMessageDialog(this,
                    "\"" + p.getNombre() + "\" agregado al carrito ✔",
                    "Carrito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
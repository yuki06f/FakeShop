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
import modelo.GestionVentas;

public class VentanaPrincipal extends JFrame {

    private final Color COLOR_MORADO_OSCURO = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE = new Color(65, 20, 60);
    private final Color COLOR_NARANJA = new Color(225, 120, 80);
    private final Color COLOR_FONDO_APP = new Color(248, 246, 250);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_TEXTO = new Color(50, 20, 50);
    private final Color COLOR_TEXTO_SECUNDARIO = new Color(120, 110, 130);

    private GestionTienda gestion;
    private Usuario       usuarioActual = null;   // null = invitado

    private JLabel    lblLoginRef;      // botón cuenta con nombre de usuario
    private JLabel    lblCarritoRef;    // botón carrito
    private JTextField txtBuscar;       // buscador con hilo
    private JPanel    gridOfertas;      // grid ofertas
    private JPanel    gridVendidos;     // grid mas vendido
    private JLabel    lblBannerTitulo;  // título del banner (carrusel)
    private JLabel    lblBannerSub;     // subtítulo del banner
    private int       carruselIdx = 0;  // índice del hilo del carrusel
    
    private GestionVentas gestionV;
    private String categoriaActual = "Todos"; // Categoría por defecto

    public VentanaPrincipal(GestionTienda gestion, GestionVentas gestionV) {
        this.gestion = gestion;
        this.gestionV = gestionV;
        setTitle("Amazonasxd");
        setSize(1200, 800);
        setMinimumSize(new Dimension(900, 600));   // responsivo mínimo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO_APP);
        
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/img/carro.ico")));

        iniciarComponentes();
        iniciarCarrusel();   // hilo en segundo plano
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        add(crearCabecera(), BorderLayout.NORTH);

        add(crearSidebarCategorias(), BorderLayout.WEST);
        // grid layout
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO_APP);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // expade horzontal
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 40, 20, 40); // margen de arriba y lateral

        // fila 0 carrusel
        panelCentral.add(crearBannerPromocional(), gbc);

        // fila 1 ofertas
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 40, 20, 40);
        panelCentral.add(crearSeccionConGrid("Ofertas", true), gbc);

        //fila 2 popular
        gbc.gridy = 2;
        gbc.weighty = 1.0; // crece arriba
        gbc.fill = GridBagConstraints.BOTH;
        panelCentral.add(crearSeccionConGrid("Lo + vendido", false), gbc);

        // para poder respetar alturas
        JPanel panelWrapper = new JPanel(new BorderLayout());
        panelWrapper.setBackground(COLOR_FONDO_APP);
        panelWrapper.add(panelCentral, BorderLayout.NORTH);

        // scroll 
        JScrollPane scroll = new JScrollPane(panelWrapper);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20); // Scroll fluido
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // limpiar pixeles viejitos
        scroll.getViewport().setBackground(COLOR_FONDO_APP);
        scroll.getViewport().setOpaque(true);

        add(scroll, BorderLayout.CENTER);
    }
    
    private JPanel crearCabecera() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_MORADO_OSCURO);
        header.setBorder(new EmptyBorder(15, 40, 15, 40));

        // Logo
        JLabel lblLogo = new JLabel("Amazonas xd");
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

        // Placeholder y búsqueda hilo
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

       
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 5));
        panelBotones.setOpaque(false);

        lblLoginRef = new JLabel("Mi Cuenta");
        lblLoginRef.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLoginRef.setForeground(COLOR_BLANCO);
        lblLoginRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLoginRef.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)  { miCuenta(); }
            public void mouseEntered(MouseEvent e)  { lblLoginRef.setForeground(new Color(255, 200, 120)); }
            public void mouseExited(MouseEvent e)   { lblLoginRef.setForeground(COLOR_BLANCO); }
        });

        lblCarritoRef = new JLabel("Carrito (0)");
        lblCarritoRef.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCarritoRef.setForeground(COLOR_BLANCO);
        lblCarritoRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCarritoRef.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)  { verCarrito(); }
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
    
    private JPanel crearSidebarCategorias() {
        // 1. Panel principal de la barra lateral
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_BLANCO); // Fondo blanco limpio
        sidebar.setPreferredSize(new Dimension(200, 0)); // Ancho fijo de 200px
        sidebar.setBorder(new EmptyBorder(30, 25, 20, 20)); // Márgenes

        // 2. Título de la sección
        JLabel titulo = new JLabel("Categorías");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_MORADO_OSCURO);
        sidebar.add(titulo);
        sidebar.add(Box.createVerticalStrut(20)); // Espacio

        // 3. Lista de categorías (Ajusta los nombres a los que uses en tu clase Producto)
        String[] categorias = {"Todos", "Electrónica", "Ropa", "Hogar", "Deportes", "Libros"};

        for (String cat : categorias) {
            JLabel lblCat = new JLabel(cat);
            lblCat.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            lblCat.setForeground(COLOR_TEXTO_SECUNDARIO);
            lblCat.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblCat.setBorder(new EmptyBorder(8, 0, 8, 0));

            // Efecto Hover y Clic
            lblCat.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    categoriaActual = cat; // Actualizamos la variable global
                    filtrarProductos();    // Llamamos a tu filtro inteligente
                }
                public void mouseEntered(MouseEvent e) {
                    lblCat.setForeground(COLOR_NARANJA);
                }
                public void mouseExited(MouseEvent e) {
                    // Solo regresa a gris si no es la categoría activa
                    lblCat.setForeground(COLOR_TEXTO_SECUNDARIO);
                }
            });

            sidebar.add(lblCat);
            sidebar.add(Box.createVerticalStrut(5));
        }

        return sidebar;
    }
       
    //carrusel
    private static final String[][] BANNERS = {
        {"?Ofertas del día",         "Hasta 50% OFF en productos seleccionados SOLO HOY"},
        {"Envío gratis",            "En compras mayores a $1000 este fin de semana"},
        {"Bienvenida",              "Regístrate y obtén descuento exclusivo en tu primera compra"}
    };

    private JPanel crearBannerPromocional() {
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COLOR_MORADO_BASE,getWidth(), 0, COLOR_NARANJA);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
       banner.setOpaque(false);
        banner.setPreferredSize(new Dimension(800, 220));
        
        //evitar que el carrusel desaparezca por falta de dimensiones
        banner.setMinimumSize(new Dimension(800, 220)); 
        
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        banner.setBorder(new EmptyBorder(40, 50, 40, 50));

        lblBannerTitulo = new JLabel(BANNERS[0][0]);
        lblBannerTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBannerTitulo.setForeground(COLOR_BLANCO);

        lblBannerSub = new JLabel(BANNERS[0][1]);
        lblBannerSub.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBannerSub.setForeground(new Color(255, 255, 255, 210));

        // dotsitos
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

        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        grid.setOpaque(false);

        // Guardar referencia según la sección
        if (soloOfertas) {
            gridOfertas = grid;
        } else {
            gridVendidos = grid;
        }

        // mostrar los productos
        List<Producto> productos = gestion.getListaProductos();
        if (soloOfertas) {
            productos = productos.stream().filter(Producto::isTieneDescuento) .collect(Collectors.toList());
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

        // para imagen
        JPanel imgPlaceholder = new JPanel(new BorderLayout());
        imgPlaceholder.setBackground(new Color(250, 245, 252));
        imgPlaceholder.setPreferredSize(new Dimension(170, 140));
        imgPlaceholder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // etiqueta descuento
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
        JLabel lblOriginal = new JLabel(p.isTieneDescuento()? "<html><strike>$" + String.format("%.2f", p.getPrecio()) + "</strike></html>": " ");
        lblOriginal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblOriginal.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblOriginal.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stock
        JLabel lblStock = new JLabel(p.getStock() > 0? "En stock (" + p.getStock() + ")": "Agotado");
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

        // hacer hover y mostrar borde naranja
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
                verProducto(p);
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

        // 1. PRIMER FILTRO: Por Categoría (La idea de Hector)
        List<Producto> filtradosPorCat = todos.stream()
            .filter(p -> categoriaActual.equals("Todos") || p.getCategoria().equalsIgnoreCase(categoriaActual))
            .collect(Collectors.toList());

        // 2. SEGUNDO FILTRO: Separar Ofertas y aplicar texto de búsqueda
        List<Producto> ofertas = filtradosPorCat.stream()
            .filter(Producto::isTieneDescuento)
            .filter(p -> !buscando
                || p.getNombre().toLowerCase().contains(query)
                || p.getCategoria().toLowerCase().contains(query))
            .collect(Collectors.toList());

        // 3. TERCER FILTRO: Lo más vendido + búsqueda
        List<Producto> todos2 = filtradosPorCat.stream()
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

    //se usa en autenticacion tras loggear
    public void setUsuario(Usuario u) {
        this.usuarioActual = u;
        
        if (u != null) {
            if (u.esAdmin()) {
                this.setVisible(false); 
                
                // 3. Abrimos directamente el Panel de Administrador
                new PanelAdmin(gestion, gestionV).setVisible(true);
            } else {
                // Es un cliente normal, solo actualizamos su nombre en la cabecera
                lblLoginRef.setText(u.getNombre());
            }
        } else {
            lblLoginRef.setText("Mi Cuenta");
            lblCarritoRef.setText("Carrito (0)");
        }
    }

    //actualizar contador del carrito
    public void actualizarContadorCarrito() {
        if (usuarioActual instanceof Cliente) {
            int n = ((Cliente) usuarioActual).getCarrito().size();
            lblCarritoRef.setText("Carrito (" + n + ")");
        }
    }

    private void miCuenta() {
        if (usuarioActual == null) {
            // Abrir ventana de autenticación
            autenticacion auth = new autenticacion(gestion, this);
            auth.setVisible(true);
        } else {
            
            if (usuarioActual.esAdmin()) {
                // Admin ve su propio menú
                String[] opciones = {"Panel de administración", "Cerrar sesión"};
                int r = JOptionPane.showOptionDialog(this,
                    "Bienvenido, " + usuarioActual.getNombre() + " (Admin)",
                    "Mi cuenta", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
                if (r == 0) {
                    new PanelAdmin(gestion, gestionV).setVisible(true);
                } else if (r == 1) {
                    setUsuario(null);
                }
            } else{
                String[] opciones = {"Mi perfil", "Mis pedidos", "Cerrar sesión"};
                int r = JOptionPane.showOptionDialog(this, "Bienvenido, " + usuarioActual.getNombre(),
                    "Mi cuenta",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

                if (r == 0 && !usuarioActual.esAdmin()) {
                    new PerfilDialog(this, (Cliente) usuarioActual, gestion).setVisible(true);

                    lblLoginRef.setText(usuarioActual.getNombre());

                } else if (r == 1 && !usuarioActual.esAdmin()) {
                    new HistorialDialog(this, (Cliente) usuarioActual, gestionV).setVisible(true);

                } else if (r == 2) {
                    setUsuario(null);
                }
            }
            
        }
    }

    private void verCarrito() {
        if (usuarioActual == null) {
            int r = JOptionPane.showConfirmDialog(this,
                "Necesitas iniciar sesión para ver tu carrito, ¿deseas ingresar?",
                "Inicia sesión", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) miCuenta();
        } else if (!(usuarioActual instanceof Cliente)) {
            JOptionPane.showMessageDialog(this, "Solo clientes pueden usar el carrito");
        } else {
            CarritoDialog carrito = new CarritoDialog(this, (Cliente) usuarioActual, gestion, gestionV);
            carrito.setVisible(true);
            actualizarContadorCarrito(); 
        }
    }

    private void verProducto(Producto p) {
        if (usuarioActual == null) {
            int r = JOptionPane.showOptionDialog(this,
                p.getNombre() + "\nPrecio: $" + String.format("%.2f", p.getPrecioEfectivo())
                + "\nStock: " + p.getStock()
                + "\n\nInicia sesión para agregar al carrito.",
                "Detalle del producto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Iniciar sesión", "Cerrar"}, "Cerrar");
            if (r == 0) miCuenta();
        } else if (usuarioActual instanceof Cliente) {
            Cliente cliente = (Cliente) usuarioActual;
            if (p.getStock() <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Este producto está agotado.", "Sin stock",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            cliente.agregarAlCarrito(p);
            actualizarContadorCarrito();
            // Mostrar snack pequeño sin bloquear
            JOptionPane.showMessageDialog(this, "\"" + p.getNombre() + "\" agregado al carrito","Carrito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //wrap con grid y box
    static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }
        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }
        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;
                int x = 0, y = insets.top + vgap, rowH = 0;
                for (Component m : target.getComponents()) {
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (x == 0 || x + d.width <= maxWidth) {
                            x += d.width + hgap;
                        } else {
                            y += rowH + vgap;
                            x = d.width + hgap;
                            rowH = 0;
                        }
                        rowH = Math.max(rowH, d.height);
                    }
                }
                y += rowH + vgap + insets.bottom;
                return new Dimension(targetWidth, y);
            }
        }
    }
}
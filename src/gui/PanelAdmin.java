package gui;

import modelo.GestionTienda;
import modelo.GestionVentas;
import modelo.Producto;
import modelo.Venta;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelAdmin extends JFrame {

    private final Color COLOR_SIDEBAR       = new Color(30, 10, 45);
    private final Color COLOR_SIDEBAR_SEL   = new Color(65, 20, 60);
    private final Color COLOR_MORADO_BASE   = new Color(65, 20, 60);
    private final Color COLOR_NARANJA       = new Color(225, 120, 80);
    private final Color COLOR_FONDO         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO        = Color.WHITE;
    private final Color COLOR_TEXTO         = new Color(50, 20, 50);
    private final Color COLOR_GRIS          = new Color(120, 110, 130);
    private final Color COLOR_VERDE         = new Color(30, 140, 70);
    private final Color COLOR_ERROR         = new Color(200, 50, 50);

    private GestionTienda gestion;
    private GestionVentas gestionVentas;
    private String        seccionActiva = "productos";

    private JPanel      panelContenido;
    private CardLayout  cardLayout;
    private JLabel[]    itemsMenu;

    private JTable          tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField      txtFiltroProducto;
    private JComboBox<String> cbCategoria;

    private JTable          tablaVentas;
    private DefaultTableModel modeloTablaVentas;
    private JComboBox<String> cbFiltroCategoria;
    private JLabel          lblTotalIngresos;

    private static final String[] CATEGORIAS = {"Todas", "Electrónica", "Ropa", "Hogar", "Deportes", "Libros"};

    public PanelAdmin(GestionTienda gestion, GestionVentas gestionVentas) {
        this.gestion       = gestion;
        this.gestionVentas = gestionVentas;

        setTitle("TiendaMax — Panel de Administración");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 580));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        construirUI();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_FONDO);
        root.add(crearSidebar(),    BorderLayout.WEST);
        root.add(crearContenido(),  BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(200, 0));

        // Logo
        JLabel logo = new JLabel("Administracion");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setBorder(new EmptyBorder(24, 20, 8, 20));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLogo = new JLabel("Panel Admin");
        subLogo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLogo.setForeground(new Color(160, 140, 175));
        subLogo.setBorder(new EmptyBorder(0, 20, 20, 20));
        subLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(subLogo);
        sidebar.add(new JSeparator() {{
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            setForeground(new Color(60, 40, 70));
        }});
        sidebar.add(Box.createVerticalStrut(12));

        // Ítems del menú
        String[][] menu = {
            {"productos", "Productos"},
            {"ventas",    "Ventas e ingresos"}
        };
        itemsMenu = new JLabel[menu.length];

        for (int i = 0; i < menu.length; i++) {
            final String id    = menu[i][0];
            final String texto = menu[i][1];
            itemsMenu[i] = crearItemMenu(id, texto, i);
            sidebar.add(itemsMenu[i]);
        }

        sidebar.add(Box.createVerticalGlue());

        // Botón cerrar sesión
        JLabel btnSalir = new JLabel("← Volver a la tienda");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSalir.setForeground(new Color(160, 140, 175));
        btnSalir.setBorder(new EmptyBorder(16, 20, 20, 20));
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSalir.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
            public void mouseEntered(MouseEvent e) { btnSalir.setForeground(COLOR_NARANJA); }
            public void mouseExited(MouseEvent e)  { btnSalir.setForeground(new Color(160,140,175)); }
        });
        sidebar.add(btnSalir);

        return sidebar;
    }

    private JLabel crearItemMenu(String id, String texto, int idx) {
        JLabel item = new JLabel(texto) {
            @Override protected void paintComponent(Graphics g) {
                if (id.equals(seccionActiva)) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(COLOR_SIDEBAR_SEL);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(COLOR_NARANJA);
                    g2.fillRect(0, 0, 4, getHeight());
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        item.setFont(new Font("Segoe UI",
            id.equals(seccionActiva) ? Font.BOLD : Font.PLAIN, 13));
        item.setForeground(id.equals(seccionActiva)
            ? Color.WHITE : new Color(190, 175, 205));
        item.setBorder(new EmptyBorder(13, 20, 13, 20));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                seccionActiva = id;
                cardLayout.show(panelContenido, id);
                // Refrescar fuentes y colores de todos los ítems
                for (JLabel it : itemsMenu) it.repaint();
                item.setFont(new Font("Segoe UI", Font.BOLD, 13));
                item.setForeground(Color.WHITE);
            }
            public void mouseEntered(MouseEvent e) {
                if (!id.equals(seccionActiva))
                    item.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                if (!id.equals(seccionActiva))
                    item.setForeground(new Color(190,175,205));
            }
        });
        return item;
    }
    private JPanel crearContenido() {
        cardLayout    = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(COLOR_FONDO);

        panelContenido.add(crearSeccionProductos(), "productos");
        panelContenido.add(crearSeccionVentas(),    "ventas");

        cardLayout.show(panelContenido, "productos");
        return panelContenido;
    }

    private JPanel crearSeccionProductos() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(COLOR_FONDO);

        // ── Toolbar ──
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Gestión de productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(COLOR_TEXTO);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controles.setOpaque(false);

        // Buscador
        txtFiltroProducto = new JTextField("Buscar producto...");
        txtFiltroProducto.setPreferredSize(new Dimension(200, 34));
        txtFiltroProducto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFiltroProducto.setForeground(COLOR_GRIS);
        txtFiltroProducto.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210,200,220), 1, true),
            new EmptyBorder(4,10,4,10)));
        txtFiltroProducto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtFiltroProducto.getText().equals("Buscar producto...")) {
                    txtFiltroProducto.setText("");
                    txtFiltroProducto.setForeground(COLOR_TEXTO);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtFiltroProducto.getText().isEmpty()) {
                    txtFiltroProducto.setText("Buscar producto...");
                    txtFiltroProducto.setForeground(COLOR_GRIS);
                }
            }
        });
        txtFiltroProducto.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrarTablaProductos(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrarTablaProductos(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTablaProductos(); }
            });

        // Combo categoría
        cbCategoria = new JComboBox<>(CATEGORIAS);
        cbCategoria.setPreferredSize(new Dimension(130, 34));
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbCategoria.addActionListener(e -> filtrarTablaProductos());

        // Botón agregar
        JButton btnAgregar = crearBoton("+ Agregar producto", true);
        btnAgregar.addActionListener(e -> abrirFormProducto(null));

        controles.add(txtFiltroProducto);
        controles.add(cbCategoria);
        controles.add(btnAgregar);

        toolbar.add(titulo,    BorderLayout.WEST);
        toolbar.add(controles, BorderLayout.EAST);

        // ── Tabla ──
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio",
                             "Descuento", "Precio final", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaProductos = new JTable(modeloTabla);
        estilizarTabla(tablaProductos);

        // Doble clic → editar
        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablaProductos.getSelectedRow() >= 0) {
                    String id = (String) modeloTabla.getValueAt(
                        tablaProductos.getSelectedRow(), 0);
                    Producto prod = buscarProductoPorId(id);
                    if (prod != null) abrirFormProducto(prod);
                }
            }
        });

        cargarTablaProductos(gestion.getListaProductos());

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(new EmptyBorder(0,0,0,0));

        // ── Panel de acciones (debajo de la tabla) ──
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        acciones.setBackground(COLOR_BLANCO);
        acciones.setBorder(new MatteBorder(1,0,0,0, new Color(220,215,230)));

        JButton btnEditar = crearBoton("✏ Editar", false);
        btnEditar.addActionListener(e -> {
            int row = tablaProductos.getSelectedRow();
            if (row < 0) { info("Selecciona un producto."); return; }
            String id = (String) modeloTabla.getValueAt(row, 0);
            abrirFormProducto(buscarProductoPorId(id));
        });

        JButton btnEliminar = crearBoton("🗑 Eliminar", false);
        btnEliminar.setBackground(COLOR_ERROR);
        btnEliminar.addActionListener(e -> eliminarProductoSeleccionado());

        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(COLOR_FONDO);
        centro.setBorder(new EmptyBorder(16, 24, 0, 24));
        centro.add(scroll,   BorderLayout.CENTER);
        centro.add(acciones, BorderLayout.SOUTH);

        p.add(toolbar, BorderLayout.NORTH);
        p.add(centro,  BorderLayout.CENTER);
        return p;
    }

    // ── Cargar / filtrar tabla productos ──────────────────────────
    private void cargarTablaProductos(List<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                String.format("$%,.2f", p.getPrecio()),
                p.isTieneDescuento()
                    ? "-" + (int)(p.getPorcentajeDescuento()*100) + "%" : "—",
                String.format("$%,.2f", p.getPrecioEfectivo()),
                p.getStock()
            });
        }
    }

    private void filtrarTablaProductos() {
        String query = txtFiltroProducto.getText().toLowerCase().trim();
        String cat   = (String) cbCategoria.getSelectedItem();
        boolean buscando = !query.isEmpty() && !query.equals("buscar producto...");

        List<Producto> filtrados = gestion.getListaProductos().stream()
            .filter(p -> (cat.equals("Todas") || p.getCategoria().equalsIgnoreCase(cat))
                && (!buscando || p.getNombre().toLowerCase().contains(query)
                             || p.getId().toLowerCase().contains(query)))
            .collect(Collectors.toList());

        cargarTablaProductos(filtrados);
    }

    // ── Formulario agregar / editar producto ──────────────────────
    private void abrirFormProducto(Producto prodEditar) {
        boolean esNuevo = (prodEditar == null);
        JDialog form = new JDialog(this,
            esNuevo ? "Agregar producto" : "Editar producto", true);
        form.setSize(420, 500);
        form.setLocationRelativeTo(this);
        form.setResizable(false);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(COLOR_FONDO);
        root.setBorder(new EmptyBorder(24, 32, 24, 32));

        JLabel titulo = new JLabel(esNuevo ? "Nuevo producto" : "Editar producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(titulo);
        root.add(Box.createVerticalStrut(20));

        // Campos
        JTextField fId       = campo(root, "ID del producto",
            esNuevo ? "" : prodEditar.getId(), esNuevo);
        JTextField fNombre   = campo(root, "Nombre",
            esNuevo ? "" : prodEditar.getNombre(), true);
        JTextField fPrecio   = campo(root, "Precio ($)",
            esNuevo ? "" : String.valueOf(prodEditar.getPrecio()), true);
        JTextField fStock    = campo(root, "Stock",
            esNuevo ? "" : String.valueOf(prodEditar.getStock()), true);

        // Categoría
        etiquetaForm(root, "Categoría");
        String[] cats = {"Electrónica", "Ropa", "Hogar", "Deportes", "Libros"};
        JComboBox<String> fCat = new JComboBox<>(cats);
        fCat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fCat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        fCat.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!esNuevo) fCat.setSelectedItem(prodEditar.getCategoria());
        root.add(fCat);
        root.add(Box.createVerticalStrut(12));

        // Descuento
        etiquetaForm(root, "Descuento (0.0 a 0.99, o 0 si no hay)");
        JTextField fDesc = new JTextField(
            esNuevo ? "0" : String.valueOf(prodEditar.getPorcentajeDescuento()));
        fDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        fDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        fDesc.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210,200,220),1,true),
            new EmptyBorder(6,10,6,10)));
        root.add(fDesc);
        root.add(Box.createVerticalStrut(20));

        // Mensaje error
        JLabel lblErr = new JLabel(" ");
        lblErr.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblErr.setForeground(COLOR_ERROR);
        lblErr.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(lblErr);
        root.add(Box.createVerticalStrut(8));

        // Botón guardar
        JButton btnGuardar = crearBoton(esNuevo ? "Agregar" : "Guardar", true);
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGuardar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnGuardar.addActionListener(ev -> {
            try {
                String id     = fId.getText().trim();
                String nombre = fNombre.getText().trim();
                double precio = Double.parseDouble(fPrecio.getText().trim());
                int    stock  = Integer.parseInt(fStock.getText().trim());
                String cat2   = (String) fCat.getSelectedItem();
                double desc   = Double.parseDouble(fDesc.getText().trim());

                if (id.isEmpty() || nombre.isEmpty()) {
                    lblErr.setText("ID y nombre son obligatorios.");
                    return;
                }
                if (precio < 0 || stock < 0 || desc < 0 || desc >= 1) {
                    lblErr.setText("Valores numéricos inválidos.");
                    return;
                }

                if (esNuevo) {
                    // Verificar ID duplicado
                    if (buscarProductoPorId(id) != null) {
                        lblErr.setText("Ya existe un producto con ese ID.");
                        return;
                    }
                    gestion.getListaProductos().add(new Producto(
                        id, nombre, precio, stock, cat2, desc > 0, desc));
                } else {
                    // Editar in-place
                    ArrayList<Producto> lista = gestion.getListaProductos();
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getId().equals(prodEditar.getId())) {
                            lista.set(i, new Producto(
                                id, nombre, precio, stock, cat2, desc > 0, desc));
                            break;
                        }
                    }
                }

                gestion.guardarProductos();
                filtrarTablaProductos();
                form.dispose();

            } catch (NumberFormatException ex) {
                lblErr.setText("Precio, stock y descuento deben ser números.");
            }
        });
        root.add(btnGuardar);

        form.setContentPane(new JScrollPane(root));
        form.setVisible(true);
    }

    private void eliminarProductoSeleccionado() {
        int row = tablaProductos.getSelectedRow();
        if (row < 0) { info("Selecciona un producto."); return; }
        String id = (String) modeloTabla.getValueAt(row, 0);
        int r = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el producto \"" + modeloTabla.getValueAt(row,1) + "\"?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            gestion.getListaProductos().removeIf(p -> p.getId().equals(id));
            gestion.guardarProductos();
            filtrarTablaProductos();
        }
    }

    private JPanel crearSeccionVentas() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(COLOR_FONDO);

        // ── Toolbar ──
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(COLOR_BLANCO);
        toolbar.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Ventas e ingresos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(COLOR_TEXTO);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controles.setOpaque(false);

        cbFiltroCategoria = new JComboBox<>(CATEGORIAS);
        cbFiltroCategoria.setPreferredSize(new Dimension(150, 34));
        cbFiltroCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbFiltroCategoria.addActionListener(e -> filtrarTablaVentas());

        JButton btnReporte = crearBoton("Exportar reporte", false);
        btnReporte.addActionListener(e -> exportarReporte());

        controles.add(new JLabel("Filtrar por categoría:") {{
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(COLOR_GRIS);
        }});
        controles.add(cbFiltroCategoria);
        controles.add(btnReporte);

        toolbar.add(titulo,    BorderLayout.WEST);
        toolbar.add(controles, BorderLayout.EAST);

        // ── Tarjeta de total ──
        JPanel tarjetaTotal = crearTarjetaTotal();

        // ── Tabla ventas ──
        String[] cols = {"Folio", "Cliente", "Fecha", "Método", "Total", "Estado"};
        modeloTablaVentas = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaVentas = new JTable(modeloTablaVentas);
        estilizarTabla(tablaVentas);
        cargarTablaVentas(gestionVentas.getTodasLasVentas());

        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.setBorder(null);

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setBackground(COLOR_FONDO);
        centro.setBorder(new EmptyBorder(16, 24, 16, 24));
        centro.add(tarjetaTotal, BorderLayout.NORTH);
        centro.add(scroll,       BorderLayout.CENTER);

        p.add(toolbar, BorderLayout.NORTH);
        p.add(centro,  BorderLayout.CENTER);
        return p;
    }

    private JPanel crearTarjetaTotal() {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(
                    0,0,COLOR_MORADO_BASE, getWidth(),0,COLOR_NARANJA));
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth(),getHeight(),16,16));
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBorder(new EmptyBorder(14, 24, 14, 24));

        double total = gestionVentas.getTotalIngresos();
        int numVentas = gestionVentas.getTodasLasVentas().size();

        JLabel lbl1 = new JLabel("Ingresos totales · " + numVentas + " venta(s)");
        lbl1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl1.setForeground(new Color(255,255,255,200));
        lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblTotalIngresos = new JLabel("$" + String.format("%,.2f", total));
        lblTotalIngresos.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTotalIngresos.setForeground(Color.WHITE);
        lblTotalIngresos.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lbl1);
        card.add(lblTotalIngresos);
        return card;
    }

    private void cargarTablaVentas(List<Venta> lista) {
        modeloTablaVentas.setRowCount(0);
        for (Venta v : lista) {
            modeloTablaVentas.addRow(new Object[]{
                v.getFolio(),
                v.getNombreCliente(),
                v.getFechaFormateada(),
                v.getMetodoPago(),
                String.format("$%,.2f", v.getTotal()),
                v.getEstado().getEtiqueta()
            });
        }
    }

    private void filtrarTablaVentas() {
        String cat = (String) cbFiltroCategoria.getSelectedItem();

        List<Venta> todas = gestionVentas.getTodasLasVentas();

        if (cat.equals("Todas")) {
            cargarTablaVentas(todas);
            actualizarTotalFiltrado(todas);
            return;
        }

        // Filtrar ventas que contengan al menos un producto de esa categoría
        List<Venta> filtradas = todas.stream()
            .filter(v -> v.getProductos().stream()
                .anyMatch(p -> p.getCategoria().equalsIgnoreCase(cat)))
            .collect(Collectors.toList());

        cargarTablaVentas(filtradas);
        actualizarTotalFiltrado(filtradas);
    }

    private void actualizarTotalFiltrado(List<Venta> lista) {
        double total = lista.stream().mapToDouble(Venta::getTotal).sum();
        lblTotalIngresos.setText("$" + String.format("%,.2f", total));
    }

    // ── Exportar reporte TXT ──────────────────────────────────────
    private void exportarReporte() {
        String cat = (String) cbFiltroCategoria.getSelectedItem();
        List<Venta> lista = gestionVentas.getTodasLasVentas().stream()
            .filter(v -> cat.equals("Todas") || v.getProductos().stream()
                .anyMatch(p -> p.getCategoria().equalsIgnoreCase(cat)))
            .collect(Collectors.toList());

        String nombre = "reporte_" + System.currentTimeMillis() + ".txt";
        try (java.io.PrintWriter pw =
                new java.io.PrintWriter(new java.io.FileWriter(nombre))) {

            pw.println("╔══════════════════════════════════════════╗");
            pw.println("║                 Reporte             ║");
            pw.println("╚══════════════════════════════════════════╝");
            pw.println("Categoría filtrada : " + cat);
            pw.println("Fecha del reporte  : " +
                java.time.LocalDateTime.now().format(
                    java.time.format.DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm")));
            pw.println("Total de ventas    : " + lista.size());
            double tot = lista.stream().mapToDouble(Venta::getTotal).sum();
            pw.printf("Ingresos totales   : $%,.2f%n", tot);
            pw.println("──────────────────────────────────────────");
            for (Venta v : lista) {
                pw.println("Folio   : " + v.getFolio());
                pw.println("Cliente : " + v.getNombreCliente());
                pw.println("Fecha   : " + v.getFechaFormateada());
                pw.printf ("Total   : $%,.2f%n", v.getTotal());
                pw.println("Estado  : " + v.getEstado().getEtiqueta());
                pw.println("──────────────────────────────────────────");
            }
            pw.println("FIN DEL REPORTE");

            JOptionPane.showMessageDialog(this,
                "Reporte exportado como:\n" + nombre,
                "Reporte generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al exportar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Producto buscarProductoPorId(String id) {
        return gestion.getListaProductos().stream()
            .filter(p -> p.getId().equals(id))
            .findFirst().orElse(null);
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(36);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(235, 230, 245));
        tabla.setSelectionBackground(new Color(225, 200, 240));
        tabla.setSelectionForeground(COLOR_TEXTO);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setFillsViewportHeight(true);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(240, 235, 248));
        header.setForeground(COLOR_MORADO_BASE);
        header.setBorder(new MatteBorder(0,0,2,0, new Color(210,200,230)));
        header.setReorderingAllowed(false);
    }

    private JButton crearBoton(String texto, boolean primario) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                if (primario) {
                    g2.setPaint(new GradientPaint(
                        0,0,COLOR_MORADO_BASE, getWidth(),0,COLOR_NARANJA));
                } else {
                    g2.setColor(new Color(240, 235, 248));
                }
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth(),getHeight(),10,10));
                g2.setColor(primario ? Color.WHITE : COLOR_MORADO_BASE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(primario ? 160 : 140, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void etiquetaForm(JPanel parent, String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(COLOR_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lbl);
        parent.add(Box.createVerticalStrut(4));
    }

    private JTextField campo(JPanel parent, String label,
                              String valor, boolean editable) {
        etiquetaForm(parent, label);
        JTextField f = new JTextField(valor);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        f.setEditable(editable);
        f.setBackground(editable ? COLOR_BLANCO : new Color(240,237,245));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210,200,220),1,true),
            new EmptyBorder(6,10,6,10)));
        parent.add(f);
        parent.add(Box.createVerticalStrut(12));
        return f;
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    
   
private void corteDeCaja() {
    String hoy = java.time.LocalDate.now().format(
        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    // Filtrar ventas del día actual
    List<Venta> ventasHoy = gestionVentas.getTodasLasVentas().stream()
        .filter(v -> v.getFechaFormateada().startsWith(
            java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
        .collect(Collectors.toList());

    double totalEfectivo = ventasHoy.stream()
        .filter(v -> v.getMetodoPago().equals("EFECTIVO"))
        .mapToDouble(Venta::getTotal).sum();

    double totalTarjeta = ventasHoy.stream()
        .filter(v -> v.getMetodoPago().equals("TARJETA"))
        .mapToDouble(Venta::getTotal).sum();

    double totalDia = totalEfectivo + totalTarjeta;
    int numVentas = ventasHoy.size();

    // Construir texto del corte
    StringBuilder sb = new StringBuilder();
    sb.append("---------------------------------------\n");
    sb.append("|                 Corte               |\n");
    sb.append("--------------------------------------\n");
    sb.append("Fecha          : ").append(hoy).append("\n");
    sb.append("Hora de corte  : ").append(
        java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")))
        .append("\n");
    sb.append("──────────────────────────────────────────\n");
    sb.append(String.format("Ventas del día : %d transacción(es)%n", numVentas));
    sb.append(String.format("Efectivo : $%,.2f%n", totalEfectivo));
    sb.append(String.format("Tarjeta: $%,.2f%n", totalTarjeta));
    sb.append("──────────────────────────────────────────\n");
    sb.append(String.format("TOTAL DEL DÍA  : $%,.2f%n", totalDia));
    sb.append("-------------------------------------\n");

    // Detalle por venta
    if (!ventasHoy.isEmpty()) {
        sb.append("\nDETALLE DE VENTAS:\n");
        for (Venta v : ventasHoy) {
            sb.append(String.format("  %s | %s | %s | $%,.2f%n",
                v.getFolio(),
                v.getNombreCliente(),
                v.getMetodoPago(),
                v.getTotal()));
        }
    }

    // Guardar archivo
    String nombreArchivo = "corte_" +
        java.time.LocalDate.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))
        + "_" + System.currentTimeMillis() + ".txt";

    try (java.io.PrintWriter pw =
            new java.io.PrintWriter(new java.io.FileWriter(nombreArchivo))) {
        pw.print(sb.toString());
    } catch (java.io.IOException ex) {
        JOptionPane.showMessageDialog(this,
            "Error al guardar el corte: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Mostrar en pantalla
    JDialog dialogo = new JDialog(this, "Corte de caja — " + hoy, true);
    dialogo.setSize(460, 440);
    dialogo.setLocationRelativeTo(this);
    dialogo.setResizable(false);

    JPanel root = new JPanel(new BorderLayout());
    root.setBackground(COLOR_FONDO);

    // Header
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(COLOR_SIDEBAR);
    header.setBorder(new EmptyBorder(14, 20, 14, 20));
    JLabel tit = new JLabel("🧾  Corte de caja — " + hoy);
    tit.setFont(new Font("Segoe UI", Font.BOLD, 16));
    tit.setForeground(Color.WHITE);
    header.add(tit, BorderLayout.WEST);

    // Resumen visual
    JPanel resumen = new JPanel();
    resumen.setLayout(new BoxLayout(resumen, BoxLayout.Y_AXIS));
    resumen.setBackground(COLOR_FONDO);
    resumen.setBorder(new EmptyBorder(20, 28, 12, 28));

    resumen.add(filaCorte("Ventas realizadas",
        numVentas + " transacción(es)", false));
    resumen.add(Box.createVerticalStrut(8));
    resumen.add(filaCorte("Efectivo (OXXO)",
        String.format("$%,.2f", totalEfectivo), false));
    resumen.add(Box.createVerticalStrut(8));
    resumen.add(filaCorte("Tarjeta",
        String.format("$%,.2f", totalTarjeta), false));
    resumen.add(Box.createVerticalStrut(12));

    // Separador
    JSeparator sep = new JSeparator();
    sep.setForeground(new Color(210, 200, 230));
    sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    resumen.add(sep);
    resumen.add(Box.createVerticalStrut(12));

    // Total grande
    resumen.add(filaCorte("TOTAL DEL DÍA",
        String.format("$%,.2f", totalDia), true));
    resumen.add(Box.createVerticalStrut(16));

    JLabel lblArchivo = new JLabel("Guardado como: " + nombreArchivo);
    lblArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    lblArchivo.setForeground(COLOR_VERDE);
    lblArchivo.setAlignmentX(Component.LEFT_ALIGNMENT);
    resumen.add(lblArchivo);

    // Botón cerrar
    JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
    footer.setBackground(COLOR_BLANCO);
    footer.setBorder(new MatteBorder(1,0,0,0, new Color(220,215,230)));
    JButton btnCerrar = crearBoton("Cerrar", true);
    btnCerrar.addActionListener(e -> dialogo.dispose());
    footer.add(btnCerrar);

    root.add(header,  BorderLayout.NORTH);
    root.add(resumen, BorderLayout.CENTER);
    root.add(footer,  BorderLayout.SOUTH);

    dialogo.setContentPane(root);
    dialogo.setVisible(true);
}

    // corte
    private JPanel filaCorte(String label, String valor, boolean destacado) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lLabel = new JLabel(label);
        lLabel.setFont(new Font("Segoe UI",
            destacado ? Font.BOLD : Font.PLAIN,
            destacado ? 16 : 13));
        lLabel.setForeground(destacado ? COLOR_MORADO_BASE : COLOR_TEXTO);

        JLabel lValor = new JLabel(valor);
        lValor.setFont(new Font("Segoe UI",
            destacado ? Font.BOLD : Font.PLAIN,
            destacado ? 18 : 13));
        lValor.setForeground(destacado ? COLOR_NARANJA : COLOR_GRIS);

        fila.add(lLabel, BorderLayout.WEST);
        fila.add(lValor, BorderLayout.EAST);
        return fila;
    }
}
package gui;

import java.awt.*;
import javax.swing.*;

public class PanelAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PanelAdmin.class.getName());

    public PanelAdmin() {
        initComponents();
    }

    private modelo.GestionTienda gestion;
    private modelo.GestionVentas gestionVentas;
    private javax.swing.table.DefaultTableModel modeloTabla;
    private javax.swing.table.DefaultTableModel modeloTablaVentas;

    public PanelAdmin(modelo.GestionTienda gestion, modelo.GestionVentas gestionVentas) {
        this.gestion = gestion;
        this.gestionVentas = gestionVentas;
        
        initComponents();
        setLocationRelativeTo(null); // Centrar en pantalla
        
        configurarMenu();
        configurarTabla();
        configurarEventos();
    }
    
    private void configurarMenu() {
        // Evento para cerrar el panel
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            
        });

        lblMenuProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                java.awt.CardLayout cl = (java.awt.CardLayout) panelCartas.getLayout();
                cl.show(panelCartas, "cartaProductos");
            }
        });

        lblMenuVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                java.awt.CardLayout cl = (java.awt.CardLayout) panelCartas.getLayout();
                cl.show(panelCartas, "cartaVentas");
            }
        });
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Descuento", "Precio final", "Stock"};
        
        modeloTabla = new javax.swing.table.DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { 
                //bloquear
                return c == 1 || c == 2 || c == 3 || c == 4 || c == 6; 
            }
        };
        tablaProductos.setModel(modeloTabla);
        
        tablaProductos.setRowHeight(30);
        tablaProductos.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        
        cargarTablaProductos(gestion.getListaProductos());
        
        // Configurar tabla ventas
        String[] colsVentas = {"Folio", "Cliente", "Fecha", "Método", "Total", "Estado"};
        modeloTablaVentas = new javax.swing.table.DefaultTableModel(colsVentas, 0);
        tablaVentas.setModel(modeloTablaVentas);
        cargarTablaVentas(gestionVentas.getTodasLasVentas());

        btnReporte.addActionListener(e -> exportarReporte());
    }

    private void cargarTablaProductos(java.util.List<modelo.Producto> lista) {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        for (modelo.Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                String.format("$%,.2f", p.getPrecio()),
                p.isTieneDescuento() ? "-" + (int)(p.getPorcentajeDescuento()*100) + "%" : "—",
                String.format("$%,.2f", p.getPrecioEfectivo()),
                p.getStock()
            });
        }
    }
    
    private void configurarEventos() {
        // Filtraar
        txtFiltroProducto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });
        cbCategoria.addActionListener(e -> filtrar());

        btnEditar.setText("Guardar Fila"); // Cambiamos el texto visualmente
        btnEditar.addActionListener(e -> {
            // Si el usuario sigue escribiendo en la celda, la cerramos para capturar el texto
            if (tablaProductos.isEditing()) {
                tablaProductos.getCellEditor().stopCellEditing();
            }

            int row = tablaProductos.getSelectedRow();
            if (row < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Selecciona la fila que editaste para guardar.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String id = (String) modeloTabla.getValueAt(row, 0);
                String nombre = (String) modeloTabla.getValueAt(row, 1);
                String categoria = (String) modeloTabla.getValueAt(row, 2);
                String precioStr = modeloTabla.getValueAt(row, 3).toString().replace("$", "").replace(",", "").trim();
                double precio = Double.parseDouble(precioStr);

                String descStr = modeloTabla.getValueAt(row, 4).toString().replace("-", "").replace("%", "").replace("—", "0").trim();
                double descuento = Double.parseDouble(descStr) / 100.0;

                String stockStr = modeloTabla.getValueAt(row, 6).toString().trim();
                int stock = Integer.parseInt(stockStr);

                // Buscar producto
                for (modelo.Producto p : gestion.getListaProductos()) {
                    if (p.getId().equals(id)) {
                        p.setNombre(nombre);
                        p.setCategoria(categoria);
                        p.setPrecio(precio);
                        p.setDescuento(descuento > 0);
                        p.setPorcentajeDescuento(descuento);
                        p.setStock(stock);
                        break;
                    }
                }
                
                gestion.guardarProductos();
                javax.swing.JOptionPane.showMessageDialog(this, "Producto guardado con éxito.", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                filtrar(); // Recargar la tabla

            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Verifica que el Precio, Descuento y Stock sean números válidos.", "Error de formato", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = tablaProductos.getSelectedRow();
            if (row < 0) return;
            String id = (String) modeloTabla.getValueAt(row, 0);
            int r = javax.swing.JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este producto?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
            
            if (r == javax.swing.JOptionPane.YES_OPTION) {
                gestion.getListaProductos().removeIf(p -> p.getId().equals(id));
                gestion.guardarProductos();
                filtrar(); 
            }
        });
        
        btnAgregar.addActionListener(e -> {
            String id = javax.swing.JOptionPane.showInputDialog(this, "Ingresa el ID del nuevo producto:");
            if (id == null || id.trim().isEmpty()) return;
            
            boolean existe = gestion.getListaProductos().stream().anyMatch(p -> p.getId().equals(id));
            if (existe) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ese ID ya existe.");
                return;
            }
            
            String nombre = javax.swing.JOptionPane.showInputDialog(this, "Ingresa el Nombre:");
            if (nombre == null || nombre.trim().isEmpty()) return;

            gestion.getListaProductos().add(new modelo.Producto(id, nombre, 0.0, 0, "Electrónica", false, 0.0));
            gestion.guardarProductos();
            filtrar(); 
        });
    }

    private void filtrar() {
        String query = txtFiltroProducto.getText().toLowerCase().trim();
        String cat = (String) cbCategoria.getSelectedItem();
        boolean buscando = !query.isEmpty() && !query.equals("Buscar producto...");

        java.util.List<modelo.Producto> filtrados = gestion.getListaProductos().stream()
            .filter(p -> (cat.equals("Todas") || p.getCategoria().equalsIgnoreCase(cat))
                && (!buscando || p.getNombre().toLowerCase().contains(query) || p.getId().toLowerCase().contains(query)))
            .collect(java.util.stream.Collectors.toList());

        cargarTablaProductos(filtrados);
    }
    
        private void abrirFormProducto(modelo.Producto prodEditar) {
            boolean esNuevo = (prodEditar == null);
            JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
            JTextField fId = new JTextField(esNuevo ? "" : prodEditar.getId());
            JTextField fNombre = new JTextField(esNuevo ? "" : prodEditar.getNombre());
            JTextField fPrecio = new JTextField(esNuevo ? "" : String.valueOf(esNuevo ? 0 : prodEditar.getPrecio()));
            JTextField fStock = new JTextField(esNuevo ? "" : String.valueOf(esNuevo ? 0 : prodEditar.getStock()));
            JComboBox<String> fCat = new JComboBox<>(new String[]{"Electrónica", "Ropa", "Hogar", "Deportes", "Libros"});
            if (!esNuevo) fCat.setSelectedItem(prodEditar.getCategoria());

            form.add(new JLabel("ID:")); form.add(fId);
            form.add(new JLabel("Nombre:")); form.add(fNombre);
            form.add(new JLabel("Precio:")); form.add(fPrecio);
            form.add(new JLabel("Stock:")); form.add(fStock);
            form.add(new JLabel("Categoría:")); form.add(fCat);

            int res = javax.swing.JOptionPane.showConfirmDialog(this, form, esNuevo ? "Nuevo Producto" : "Editar Producto", javax.swing.JOptionPane.OK_CANCEL_OPTION);

            if (res == javax.swing.JOptionPane.OK_OPTION) {
                try {
                    if (esNuevo) {
                        gestion.getListaProductos().add(new modelo.Producto(fId.getText(), fNombre.getText(), Double.parseDouble(fPrecio.getText()), Integer.parseInt(fStock.getText()), fCat.getSelectedItem().toString(), false, 0));
                    } else {
                        prodEditar.setNombre(fNombre.getText());
                        prodEditar.setPrecio(Double.parseDouble(fPrecio.getText()));
                        prodEditar.setStock(Integer.parseInt(fStock.getText()));
                        prodEditar.setCategoria(fCat.getSelectedItem().toString());
                    }
                    gestion.guardarProductos();
                    filtrar();
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error: Revisa los datos numéricos.");
                }
            }
    }
    
        //VENTAS
    private void configurarTablaVentas() {
        String[] cols = {"Folio", "Cliente", "Fecha", "Método", "Total", "Estado"};
        modeloTablaVentas = new javax.swing.table.DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaVentas.setModel(modeloTablaVentas);
        cargarTablaVentas(gestionVentas.getTodasLasVentas());
    }

    // 1. Método para cargar las ventas en la tabla
    private void cargarTablaVentas(java.util.List<modelo.Venta> lista) {
        modeloTablaVentas.setRowCount(0);
        double total = 0;
        for (modelo.Venta v : lista) {
            modeloTablaVentas.addRow(new Object[]{
                v.getFolio(), v.getNombreCliente(), v.getFechaFormateada(), 
                v.getMetodoPago(), String.format("$%,.2f", v.getTotal()), v.getEstado().getEtiqueta()
            });
            total += v.getTotal();
        }
        lblTotalIngresos.setText("$" + String.format("%,.2f", total));
    }

    // 2. Método para exportar a TXT
    private void exportarReporte() {
        String nombre = "Reporte_Ventas_" + System.currentTimeMillis() + ".txt";
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(nombre))) {
            pw.println("--- REPORTE DE VENTAS ---");
            for (int i = 0; i < modeloTablaVentas.getRowCount(); i++) {
                pw.println("Folio: " + modeloTablaVentas.getValueAt(i, 0) + " | Total: " + modeloTablaVentas.getValueAt(i, 4));
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Reporte guardado como: " + nombre);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al exportar.");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSidebar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMenuProductos = new javax.swing.JLabel();
        lblMenuVentas = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        panelCartas = new javax.swing.JPanel();
        panelProductos = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtFiltroProducto = new javax.swing.JTextField();
        cbCategoria = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelVentas = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalIngresos = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cbFiltroCategoria = new javax.swing.JComboBox<>();
        btnReporte = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1100, 700));
        setMinimumSize(new java.awt.Dimension(1100, 700));

        panelSidebar.setBackground(new java.awt.Color(30, 10, 45));
        panelSidebar.setPreferredSize(new java.awt.Dimension(200, 700));
        panelSidebar.setLayout(new javax.swing.BoxLayout(panelSidebar, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Panel de Admin");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 20, 40, 20));
        panelSidebar.add(jLabel1);

        lblMenuProductos.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMenuProductos.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuProductos.setText("Productos");
        lblMenuProductos.setAlignmentX(0.5F);
        lblMenuProductos.setAlignmentY(1.0F);
        lblMenuProductos.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 15, 20, 15));
        lblMenuProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSidebar.add(lblMenuProductos);

        lblMenuVentas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuVentas.setText("Ventas e Ingresos");
        lblMenuVentas.setAlignmentX(0.5F);
        lblMenuVentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 15, 20, 15));
        lblMenuVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSidebar.add(lblMenuVentas);

        lblSalir.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(255, 255, 255));
        lblSalir.setText("Salir");
        lblSalir.setAlignmentX(0.5F);
        lblSalir.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 15, 20, 15));
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSidebar.add(lblSalir);

        getContentPane().add(panelSidebar, java.awt.BorderLayout.WEST);

        panelCartas.setLayout(new java.awt.CardLayout());

        panelProductos.setBackground(new java.awt.Color(248, 246, 250));
        panelProductos.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 25, 15, 25));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(50, 20, 50));
        jLabel4.setText("Gestión Productos");
        jLabel4.setToolTipText("");
        jPanel1.add(jLabel4, java.awt.BorderLayout.WEST);

        jPanel2.setOpaque(false);

        txtFiltroProducto.setText("Buscar producto...");
        txtFiltroProducto.setPreferredSize(new java.awt.Dimension(200, 22));
        txtFiltroProducto.addActionListener(this::txtFiltroProductoActionPerformed);
        jPanel2.add(txtFiltroProducto);

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Electrónica", "Ropa", "Hogar", "Deportes", "Libros" }));
        jPanel2.add(cbCategoria);

        btnAgregar.setText("Agregar Producto");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);
        jPanel2.add(btnAgregar);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        panelProductos.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Categoría", "Precio", "Descuento", "Precio Final", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        panelProductos.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnEditar.setText("Editar");
        jPanel3.add(btnEditar);

        btnEliminar.setText("Eliminar");
        jPanel3.add(btnEliminar);

        panelProductos.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelCartas.add(panelProductos, "cartaProductos");

        panelVentas.setBackground(new java.awt.Color(248, 246, 250));
        panelVentas.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Ingresos Totales");
        jPanel4.add(jLabel2);

        lblTotalIngresos.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblTotalIngresos.setText("$0.00");
        jPanel4.add(lblTotalIngresos);

        jPanel5.setBackground(new java.awt.Color(248, 246, 250));

        cbFiltroCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Electrónica", "Ropa", "Hogar", "Deportes", "Libros" }));
        cbFiltroCategoria.addActionListener(this::cbFiltroCategoriaActionPerformed);

        btnReporte.setText("Hacer reporte");
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbFiltroCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(607, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReporte)
                    .addComponent(cbFiltroCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Folio", "Cliente", "Fecha", "Método", "Total", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaVentas);

        jPanel4.add(jScrollPane2);

        panelVentas.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        panelCartas.add(panelVentas, "cartaVentas");

        getContentPane().add(panelCartas, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltroProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroProductoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
       abrirFormProducto(null);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReporteActionPerformed

    private void cbFiltroCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltroCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFiltroCategoriaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PanelAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JComboBox<String> cbFiltroCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMenuProductos;
    private javax.swing.JLabel lblMenuVentas;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTotalIngresos;
    private javax.swing.JPanel panelCartas;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtFiltroProducto;
    // End of variables declaration//GEN-END:variables
}

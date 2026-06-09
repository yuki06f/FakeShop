package gui;

public class HistorialDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HistorialDialog.class.getName());

    public HistorialDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    private modelo.Cliente cliente;
    private modelo.GestionVentas gestionVentas;

    public HistorialDialog(java.awt.Frame parent, modelo.Cliente cliente, modelo.GestionVentas gestionVentas) {
        super(parent, true);
        this.cliente = cliente;
        this.gestionVentas = gestionVentas;
        
        initComponents();
        setLocationRelativeTo(parent);
        
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { dispose(); }
        });
        
        poblarHistorial();
    }

    private void poblarHistorial() {
        panelVentas.removeAll();
        java.util.ArrayList<modelo.Venta> ventas = gestionVentas.getVentasDeCliente(cliente.getId());

        System.out.println("Ventas encontradas para este cliente: " + ventas.size());
        if (ventas.isEmpty()) {
            javax.swing.JLabel msg = new javax.swing.JLabel("Aún no has realizado ningún pedido");
            msg.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            msg.setForeground(new java.awt.Color(120, 110, 130));
            msg.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panelVentas.add(javax.swing.Box.createVerticalStrut(60));
            panelVentas.add(msg);
        } else {
            for (int i = ventas.size() - 1; i >= 0; i--) {
                ItemVenta item = new ItemVenta();
                item.setDatos(ventas.get(i));
                
                javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
                wrapper.setOpaque(false);
                wrapper.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
                wrapper.setMaximumSize(new java.awt.Dimension(32767, 300));
                
                wrapper.add(item, java.awt.BorderLayout.CENTER);
                
                panelVentas.add(wrapper);
            }
        }
        panelVentas.revalidate();
        panelVentas.repaint();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelVentas = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(248, 246, 250));
        setMaximumSize(new java.awt.Dimension(600, 640));
        setMinimumSize(new java.awt.Dimension(600, 640));
        setPreferredSize(new java.awt.Dimension(600, 640));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(40, 15, 45));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 25, 15, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 60));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mis Pedidos");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        lblCerrar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCerrar.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrar.setText("X");
        lblCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblCerrar, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelVentas.setBackground(new java.awt.Color(248, 246, 250));
        panelVentas.setLayout(new javax.swing.BoxLayout(panelVentas, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelVentas);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HistorialDialog dialog = new HistorialDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JPanel panelVentas;
    // End of variables declaration//GEN-END:variables
}

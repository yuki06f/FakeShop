package gui;

import modelo.GestionTienda;
import modelo.GestionVentas;
import gui.VentanaPrincipal;
import modelo.Cliente;

import java.util.ArrayList;
import modelo.Producto;

public class CarritoDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CarritoDialog.class.getName());

    public CarritoDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    private modelo.Cliente cliente;
    private modelo.GestionTienda gestion;
    private modelo.GestionVentas gestionV;
    private VentanaPrincipal ventanaParent;
    
    public CarritoDialog(VentanaPrincipal parent, Cliente cliente, GestionTienda gestion, GestionVentas gestionV){
        super(parent, true); //para que modal sea true
        this.ventanaParent = parent;
        this.cliente = cliente;
        this.gestion = gestion;
        this.gestionV = gestionV;
        
        initComponents();
        
        setLocationRelativeTo(parent);
        
        vacio.setVisible(false);
        
        poblarItems();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnVaciar = new javax.swing.JButton();
        btnPagar = new gui.botonGradiente();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelItems = new javax.swing.JPanel();
        vacio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(248, 246, 250));
        setMaximumSize(new java.awt.Dimension(540, 620));
        setMinimumSize(new java.awt.Dimension(540, 620));
        setPreferredSize(new java.awt.Dimension(520, 620));
        setResizable(false);

        header.setBackground(new java.awt.Color(40, 15, 45));
        header.setPreferredSize(new java.awt.Dimension(520, 60));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mi Carrito");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(431, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(header, java.awt.BorderLayout.PAGE_START);

        footer.setBackground(new java.awt.Color(255, 255, 255));
        footer.setPreferredSize(new java.awt.Dimension(520, 80));

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(65, 20, 60));
        lblTotal.setText("Total: $0.00");
        lblTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 20));
        lblTotal.setMaximumSize(new java.awt.Dimension(146, 50));
        lblTotal.setMinimumSize(new java.awt.Dimension(146, 50));
        lblTotal.setPreferredSize(new java.awt.Dimension(136, 50));

        jPanel3.setMinimumSize(new java.awt.Dimension(343, 30));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(384, 30));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVaciar.setText("Vaciar Carrito");
        btnVaciar.setBorderPainted(false);
        btnVaciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVaciar.addActionListener(this::btnVaciarActionPerformed);
        jPanel3.add(btnVaciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        btnPagar.setText("Proceder al pago");
        btnPagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPagar.addActionListener(this::btnPagarActionPerformed);
        jPanel3.add(btnPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 116, 37));

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, Short.MAX_VALUE))
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(footer, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItems.setBackground(new java.awt.Color(248, 246, 250));
        panelItems.setLayout(new javax.swing.BoxLayout(panelItems, javax.swing.BoxLayout.Y_AXIS));

        vacio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        vacio.setForeground(new java.awt.Color(120, 110, 130));
        vacio.setText("Tu Carrito está vacío");
        vacio.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelItems.add(vacio);

        jScrollPane1.setViewportView(panelItems);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        int r = javax.swing.JOptionPane.showConfirmDialog(this,"¿seguro que quieres vaciar el carrito?", "confirmar", javax.swing.JOptionPane.YES_NO_OPTION );
       if (r == javax.swing.JOptionPane.YES_OPTION) {
           cliente.limpiarCarrito();
           ventanaParent.actualizarContadorCarrito();
           poblarItems();
       }
    }//GEN-LAST:event_btnVaciarActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
       PagoDialog pago =  new PagoDialog(ventanaParent, cliente, gestion, gestionV, ventanaParent);
       dispose();
       pago.setVisible(true);
       
       poblarItems();
    }//GEN-LAST:event_btnPagarActionPerformed

    
    public void poblarItems(){
        panelItems.removeAll();
        ArrayList<Producto> carrito =  cliente.getCarrito();
        
        if(carrito.isEmpty()){
            //mostrar etiqueta de vacio
            panelItems.add(javax.swing.Box.createVerticalStrut(40));
            panelItems.add(vacio);
            vacio.setVisible(true);
            
        }else{
            for (int i = 0; i < carrito.size(); i++){
                final int index = i;
                Producto p = carrito.get(i);
                
                //añadirle la informcaion al carrito
                ItemCarrito item = new ItemCarrito();
                item.setDatos(p);
                item.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                
                item.getBtnEliminar().addMouseListener(new java.awt.event.MouseAdapter(){
                    public void mouseClicked(java.awt.event.MouseEvent e){
                        cliente.getCarrito().remove(index);
                        ventanaParent.actualizarContadorCarrito();
                        poblarItems();
                    }
                });
                
                panelItems.add(item);
                panelItems.add(javax.swing.Box.createVerticalStrut(10)); //espacio
            }
        }
        
        actualizarTotal();
        panelItems.revalidate();
        panelItems.repaint();
    }
    
    private void actualizarTotal(){
        double total = cliente.getCarrito().stream().mapToDouble(Producto::getPrecioEfectivo).sum();
        lblTotal.setText("total: $" + String.format("%, .2f", total));
        btnPagar.setEnabled(!cliente.getCarrito().isEmpty());
    }
    
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CarritoDialog dialog = new CarritoDialog(new javax.swing.JFrame(), true);
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
    private gui.botonGradiente btnPagar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelItems;
    private javax.swing.JLabel vacio;
    // End of variables declaration//GEN-END:variables
}

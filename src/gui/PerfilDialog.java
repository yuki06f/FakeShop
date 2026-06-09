package gui;

public class PerfilDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PerfilDialog.class.getName());

    public PerfilDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    private modelo.Cliente cliente;
    private modelo.GestionTienda gestion;
    private gui.VentanaPrincipal ventanaParent;

    public PerfilDialog(gui.VentanaPrincipal parent, modelo.Cliente cliente, modelo.GestionTienda gestion) {
        super(parent, true); // true = modal 
        this.ventanaParent = parent;
        this.cliente = cliente;
        this.gestion = gestion;
        
        initComponents();
        
        setLocationRelativeTo(parent); // Centrar 
        
        txtNombre.setText(cliente.getNombre());
        // Si tu clase Cliente tiene getDomicilio(), ponlo aquí. Si no, déjalo en blanco.
        txtDomicilio.setText(""); 
        
        pass1.setText("");
        pass2.setText("");
        lblMensaje.setText(" "); // Limpiar etiqueta de error

        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); // Cierra esta ventanita
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pass1 = new javax.swing.JPasswordField();
        lblMensaje = new javax.swing.JLabel();
        pass2 = new javax.swing.JPasswordField();
        botonGradiente1 = new gui.botonGradiente();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(248, 246, 250));
        setMaximumSize(new java.awt.Dimension(400, 500));
        setMinimumSize(new java.awt.Dimension(400, 500));

        header.setBackground(new java.awt.Color(40, 15, 45));
        header.setPreferredSize(new java.awt.Dimension(399, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mi Cuenta");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("X");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(21, 21, 21))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(40, 40, 40))
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(header, java.awt.BorderLayout.PAGE_START);

        footer.setBackground(new java.awt.Color(255, 255, 255));
        footer.setLayout(new java.awt.BorderLayout());
        getContentPane().add(footer, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(248, 246, 250));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(65, 20, 60));
        jLabel3.setText("Datos Personales");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(120, 110, 130));
        jLabel4.setText("Nombre Completo");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(120, 110, 130));
        jLabel6.setText("Domicilio");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(120, 110, 130));
        jLabel7.setText("Contraseña Actual");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(120, 110, 130));
        jLabel8.setText("Nueva Contraseña");

        txtNombre.setBackground(new java.awt.Color(248, 246, 250));
        txtNombre.setFont(new java.awt.Font("Segoe UI", 2, 13)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(120, 110, 130));
        txtNombre.setText("Ingresa tu nuevo nombre");
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtNombre.setOpaque(true);

        txtDomicilio.setBackground(new java.awt.Color(248, 246, 250));
        txtDomicilio.setFont(new java.awt.Font("Segoe UI", 2, 13)); // NOI18N
        txtDomicilio.setForeground(new java.awt.Color(120, 110, 130));
        txtDomicilio.setText("Ingresa tu domicilio");
        txtDomicilio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtDomicilio.setOpaque(true);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(65, 20, 60));
        jLabel9.setText("Cambiar Contraseña");

        pass1.setBackground(new java.awt.Color(248, 246, 250));
        pass1.setFont(new java.awt.Font("Segoe UI", 2, 13)); // NOI18N
        pass1.setForeground(new java.awt.Color(120, 110, 130));
        pass1.setText("conrasena xd");
        pass1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        lblMensaje.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblMensaje.setText(".");

        pass2.setBackground(new java.awt.Color(248, 246, 250));
        pass2.setFont(new java.awt.Font("Segoe UI", 2, 13)); // NOI18N
        pass2.setForeground(new java.awt.Color(120, 110, 130));
        pass2.setText("contrasenaxd");
        pass2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pass2.addActionListener(this::pass2ActionPerformed);

        botonGradiente1.setText("Guardar");
        botonGradiente1.setAlignmentX(0.5F);
        botonGradiente1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonGradiente1.addActionListener(this::botonGradiente1ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(botonGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 77, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pass2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMensaje, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(txtDomicilio, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(botonGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(lblMensaje)
                .addGap(26, 26, 26))
        );

        jScrollPane1.setViewportView(jPanel3);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonGradiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGradiente1ActionPerformed
        String nuevoNombre = txtNombre.getText().trim();
        String passActual = new String(pass1.getPassword());
        String passNueva = new String(pass2.getPassword());

        if (nuevoNombre.isEmpty()) {
            lblMensaje.setText("El nombre no puede estar vacío.");
            lblMensaje.setForeground(new java.awt.Color(200, 50, 50)); // Rojo
            return;
        }

         if (!passActual.isEmpty() || !passNueva.isEmpty()) {
            if (!cliente.getContraseña().equals(passActual)) {
                lblMensaje.setText("La contraseña actual es incorrecta.");
                lblMensaje.setForeground(new java.awt.Color(200, 50, 50));
                return;
            }
            // Verificar que sí escribió una nueva
            if (passNueva.isEmpty()) {
                lblMensaje.setText("Ingresa una nueva contraseña.");
                lblMensaje.setForeground(new java.awt.Color(200, 50, 50));
                return;
            }
            
           cliente.setContraseña(passNueva);
        }

        cliente.setNombre(nuevoNombre);
        
        gestion.guardarUsuarios();

         lblMensaje.setText("Perfil actualizado con éxito");
        lblMensaje.setForeground(new java.awt.Color(40, 140, 70)); // Verde
        
        ventanaParent.setUsuario(cliente); // Actualiza
        
        pass1.setText("");
        pass2.setText("");
    }//GEN-LAST:event_botonGradiente1ActionPerformed

    private void pass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pass2ActionPerformed

    public static void main(String args[]) {
        
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PerfilDialog dialog = new PerfilDialog(new javax.swing.JFrame(), true);
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
    private gui.botonGradiente botonGradiente1;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JPasswordField pass1;
    private javax.swing.JPasswordField pass2;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

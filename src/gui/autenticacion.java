
package gui;

import java.awt.Color;

public class autenticacion extends javax.swing.JFrame {
    
    public void quitar(){
        cardLogin.setVisible(false);
        cardRegistro.setVisible(false);
        nombreUsuario.setText("Ingresa aquí el nombre de tu cuenta."); 
        contraseña1.setText("Ingresa tu contraseña aquí.");
        contraseña2.setText("Vuelve a ingresar la contraseña.");
        nombreOblig.setText("*");
        contraoblig.setText("*"); 
        coincidencia.setText("*");
        coincidenciausuario.setText("*");
        coincidenciacontra.setText("*");   
        nombreusuario.setText("Ingresa tu nombre de usuario aquí.");
        contraseña.setText("Ingresa tu contraseña aquí");
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(autenticacion.class.getName());
    
    private modelo.GestionTienda gestion;
    private VentanaPrincipal ventanaPrincipal;
    
    public autenticacion(modelo.GestionTienda gestion, VentanaPrincipal vp) {
        this.gestion = gestion;
        this.ventanaPrincipal = vp;
        initComponents();
        setLocationRelativeTo(null);
    }

    public autenticacion() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelBg = new javax.swing.JPanel();
        loginCard1 = new gui.loginCard();
        cardRegistro = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        contraseña1 = new javax.swing.JTextField();
        contraseña2 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        iniciarChange = new javax.swing.JLabel();
        domicilio = new javax.swing.JTextField();
        nombreOblig = new javax.swing.JLabel();
        contraoblig = new javax.swing.JLabel();
        coincidencia = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btnRegistro = new gui.botonGradiente();
        cardLogin = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nombreusuario = new javax.swing.JTextField();
        contraseña = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        linkRegistro = new javax.swing.JLabel();
        coincidenciausuario = new javax.swing.JLabel();
        coincidenciacontra = new javax.swing.JLabel();
        btnIniciar = new gui.botonGradiente();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBg.setOpaque(false);
        panelBg.setLayout(new java.awt.GridBagLayout());

        loginCard1.setPreferredSize(new java.awt.Dimension(100, 100));
        loginCard1.setLayout(new java.awt.CardLayout());

        cardRegistro.setOpaque(false);
        cardRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 203, 251));
        jLabel17.setText("REGISTRARSE");
        cardRegistro.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 203, 251));
        jLabel18.setText("NOMBRE DE USUARIO");
        cardRegistro.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        nombreUsuario.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        nombreUsuario.setForeground(new java.awt.Color(102, 102, 102));
        nombreUsuario.setText("Ingresa tu nombre de usuario aquí.");
        nombreUsuario.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        nombreUsuario.setPreferredSize(new java.awt.Dimension(126, 16));
        nombreUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nombreUsuarioMousePressed(evt);
            }
        });
        nombreUsuario.addActionListener(this::nombreUsuarioActionPerformed);
        cardRegistro.add(nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 380, -1));

        jLabel20.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 203, 251));
        jLabel20.setText("CONTRASEÑA");
        cardRegistro.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        contraseña1.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        contraseña1.setForeground(new java.awt.Color(102, 102, 102));
        contraseña1.setText("Ingresa tu contraseña aquí.");
        contraseña1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        contraseña1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                contraseña1MousePressed(evt);
            }
        });
        contraseña1.addActionListener(this::contraseña1ActionPerformed);
        cardRegistro.add(contraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 360, 20));

        contraseña2.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        contraseña2.setForeground(new java.awt.Color(102, 102, 102));
        contraseña2.setText("Vuelve a ingresar la contraseña.");
        contraseña2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        contraseña2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                contraseña2MousePressed(evt);
            }
        });
        contraseña2.addActionListener(this::contraseña2ActionPerformed);
        cardRegistro.add(contraseña2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 360, -1));

        jLabel25.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 203, 251));
        jLabel25.setText("DOMICILIO");
        cardRegistro.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel26.setText("¿Ya tienes cuenta? Inicia sesión ");
        cardRegistro.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, 10));

        iniciarChange.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        iniciarChange.setForeground(new java.awt.Color(0, 203, 251));
        iniciarChange.setText(" aquí");
        iniciarChange.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciarChange.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iniciarChangeMouseClicked(evt);
            }
        });
        cardRegistro.add(iniciarChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, -1, 30));

        domicilio.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        domicilio.setForeground(new java.awt.Color(102, 102, 102));
        domicilio.setText("Ingresa la dirección para tus pedidos a domiclio aquí.");
        domicilio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        domicilio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                domicilioMousePressed(evt);
            }
        });
        domicilio.addActionListener(this::domicilioActionPerformed);
        cardRegistro.add(domicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 360, 20));

        nombreOblig.setForeground(new java.awt.Color(153, 0, 0));
        nombreOblig.setText("*");
        cardRegistro.add(nombreOblig, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 40, 20));

        contraoblig.setForeground(new java.awt.Color(153, 0, 0));
        contraoblig.setText("*");
        cardRegistro.add(contraoblig, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, 30, 20));

        coincidencia.setForeground(new java.awt.Color(153, 0, 0));
        coincidencia.setText("*");
        cardRegistro.add(coincidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 130, 20));

        jLabel29.setText("(Opcional)");
        cardRegistro.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, -1, 20));

        btnRegistro.setText("Registrarse");
        btnRegistro.addActionListener(this::btnRegistroActionPerformed);
        cardRegistro.add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, -1, -1));

        loginCard1.add(cardRegistro, "card3");

        cardLogin.setOpaque(false);
        cardLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 203, 251));
        jLabel8.setText("INICIAR SESIÓN");
        cardLogin.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 203, 251));
        jLabel9.setText("NOMBRE DE USUARIO");
        cardLogin.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 203, 251));
        jLabel11.setText("CONTRASEÑA");
        cardLogin.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 130, -1));

        nombreusuario.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        nombreusuario.setForeground(new java.awt.Color(102, 102, 102));
        nombreusuario.setText("Ingresa tu nombre de usuario aquí.");
        nombreusuario.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        nombreusuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nombreusuarioMousePressed(evt);
            }
        });
        nombreusuario.addActionListener(this::nombreusuarioActionPerformed);
        cardLogin.add(nombreusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 370, 20));

        contraseña.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        contraseña.setForeground(new java.awt.Color(102, 102, 102));
        contraseña.setText("Ingresa tu contraseña aquí.");
        contraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        contraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                contraseñaMousePressed(evt);
            }
        });
        contraseña.addActionListener(this::contraseñaActionPerformed);
        cardLogin.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 370, 20));

        jLabel14.setText("¿No tienes cuenta? Regístrate ");
        cardLogin.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        linkRegistro.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        linkRegistro.setForeground(new java.awt.Color(0, 203, 251));
        linkRegistro.setText(" aquí");
        linkRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        linkRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linkRegistroMouseClicked(evt);
            }
        });
        cardLogin.add(linkRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 30, 30));

        coincidenciausuario.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        coincidenciausuario.setForeground(new java.awt.Color(153, 0, 0));
        coincidenciausuario.setText("*");
        cardLogin.add(coincidenciausuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 10, 20));

        coincidenciacontra.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        coincidenciacontra.setForeground(new java.awt.Color(153, 0, 0));
        coincidenciacontra.setText("*");
        cardLogin.add(coincidenciacontra, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 30, 20));

        btnIniciar.setText("Iniciar Sesión");
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);
        cardLogin.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 220, -1));

        loginCard1.add(cardLogin, "card2");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(40, 50, 40, 50);
        panelBg.add(loginCard1, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("accede a tu cuenta");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 90));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Para continuar");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, 90));

        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, -1, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(50, 50, 50, 20);
        panelBg.add(jPanel1, gridBagConstraints);

        getContentPane().add(panelBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 500));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgLoggear.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombreUsuarioMousePressed
        if(nombreUsuario.getText().equals("Ingresa tu nombre de usuario aquí.")){
            nombreUsuario.setText("");
        }
        nombreUsuario.setForeground(Color.black);
        if(contraseña1.getText().isEmpty()){
            contraseña1.setText("Ingresa tu contraseña aquí.");
            contraseña1.setForeground(Color.gray);
        }if(contraseña2.getText().isEmpty()){
            contraseña2.setText("Vuelve a ingresar la contraseña.");
            contraseña2.setForeground(Color.gray);
        }
        //primero
    }//GEN-LAST:event_nombreUsuarioMousePressed

    private void nombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreUsuarioActionPerformed

    }//GEN-LAST:event_nombreUsuarioActionPerformed

    private void contraseña1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contraseña1MousePressed
        if(contraseña1.getText().equals("Ingresa tu contraseña aquí.")){
            contraseña1.setText("");
            contraseña1.setForeground(Color.black);
        }
        if(nombreUsuario.getText().isEmpty()){
            nombreUsuario.setText("Ingresa tu nombre de usuario aquí.");
            nombreUsuario.setForeground(Color.gray);
        }if(contraseña2.getText().isEmpty()){
            contraseña2.setText("Vuelve a ingresar la contraseña.");
            contraseña2.setForeground(Color.gray);
        }if(domicilio.getText().isEmpty()){
            domicilio.setText("Ingresa la dirección para tus pedidos a domicilio aquí.");
            domicilio.setForeground(Color.gray);
        }
    }//GEN-LAST:event_contraseña1MousePressed

    private void contraseña1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseña1ActionPerformed

    }//GEN-LAST:event_contraseña1ActionPerformed

    private void contraseña2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contraseña2MousePressed
        if(contraseña2.getText().equals("Vuelve a ingresar la contraseña.")){
            contraseña2.setText("");
            contraseña2.setForeground(Color.black);
            if(contraseña1.getText().isEmpty()){
                contraseña1.setText("");
                contraseña1.setForeground(Color.black);
            }if(nombreUsuario.getText().isEmpty()){
                nombreUsuario.setText("Ingresa tu nombre de usuario aquí.");
                nombreUsuario.setForeground(Color.gray);
            }if(domicilio.getText().isEmpty()){
                domicilio.setText("Ingresa la dirección para tus pedidos a domicilio aquí.");
                domicilio.setForeground(Color.gray);
            }
        }//3er
    }//GEN-LAST:event_contraseña2MousePressed

    private void contraseña2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseña2ActionPerformed

    }//GEN-LAST:event_contraseña2ActionPerformed

    private void iniciarChangeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iniciarChangeMouseClicked
        quitar();
        java.awt.CardLayout cartas = (java.awt.CardLayout) loginCard1.getLayout();
        cartas.show(loginCard1, "card2");
    }//GEN-LAST:event_iniciarChangeMouseClicked

    private void domicilioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_domicilioMousePressed
        if(domicilio.getText().equals("Ingresa la dirección para tus pedidos a domicilio aquí.")){
            domicilio.setText("");
            domicilio.setForeground(Color.black);
        }
        if(nombreUsuario.getText().isEmpty()){
            nombreUsuario.setText("Ingresa tu nombre de usuario aquí.");
            nombreUsuario.setForeground(Color.gray);
            if(contraseña1.getText().isEmpty()){
                contraseña1.setText("Ingresa tu contraseña aquí.");
                contraseña1.setForeground(Color.gray);
            }if(contraseña2.getText().isEmpty()){
                contraseña2.setText("Vuelve a ingresar la contraseña.");
                contraseña2.setForeground(Color.gray);
            }
        }
    }//GEN-LAST:event_domicilioMousePressed

    private void domicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domicilioActionPerformed

    }//GEN-LAST:event_domicilioActionPerformed

    private void nombreusuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombreusuarioMousePressed
        if(nombreusuario.getText().equals("Ingresa tu nombre de usuario aquí.")){
            nombreusuario.setText("");
            nombreusuario.setForeground(Color.black);
        }if(contraseña.getText().isEmpty()){
            contraseña.setText("Ingresa tu contraseña aquí.");
            contraseña.setForeground(Color.gray);
        }
    }//GEN-LAST:event_nombreusuarioMousePressed

    private void nombreusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreusuarioActionPerformed
        if(nombreusuario.getText().isEmpty()||nombreusuario.getText().equals("Ingresa tu nombre de usuario aquí.")){
            coincidenciausuario.setText("*");
        }else{
            coincidenciausuario.setText("");
        }
    }//GEN-LAST:event_nombreusuarioActionPerformed

    private void contraseñaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contraseñaMousePressed
        contraseña.setText("");
        if(contraseña.getText().equals("Ingresa tu contraseña aquí.")){
            contraseña.setText("");
            contraseña.setForeground(Color.black);
        }if(nombreusuario.getText().isEmpty()){
            nombreusuario.setText("Ingresa tu nombre de usuario aquí.");
            nombreusuario.setForeground(Color.gray);
        }
    }//GEN-LAST:event_contraseñaMousePressed

    private void contraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseñaActionPerformed
        if(contraseña.getText().isEmpty()||contraseña.getText().equals("Ingresa tu contraseña aquí.")){
            coincidenciacontra.setText("*");
        }else{
            coincidenciacontra.setText("");
        }
    }//GEN-LAST:event_contraseñaActionPerformed

    private void linkRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linkRegistroMouseClicked
        quitar();
        java.awt.CardLayout cartas = (java.awt.CardLayout) loginCard1.getLayout();
        cartas.show(loginCard1, "card3");
    }//GEN-LAST:event_linkRegistroMouseClicked

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        String nombre   = nombreUsuario.getText().trim();
        String pass1    = contraseña1.getText().trim();
        String pass2    = contraseña2.getText().trim();
        String dom      = domicilio.getText().trim();

        // Limpiar errores previos
        nombreOblig.setText("");
        contraoblig.setText("");
        coincidencia.setText("");

        boolean valido = true;

        if (nombre.isEmpty() || nombre.equals("Ingresa tu nombre de usuario aquí.")) {
            nombreOblig.setText("* Campo obligatorio");
            valido = false;
        }
        if (pass1.isEmpty() || pass1.equals("Ingresa tu contraseña aquí.")) {
            contraoblig.setText("* Campo obligatorio");
            valido = false;
        }
        if (!pass1.equals(pass2)) {
            coincidencia.setText("* Las contraseñas no coinciden");
            valido = false;
        }
        if (dom.isEmpty() || dom.equals("Ingresa la dirección para tus pedidos a domiclio aquí.")) {
           valido = false;
        }

        if (!valido) return;

        String emailGenerado = nombre.toLowerCase()
            .replaceAll("\\s+", "") + "@gmail.com";
        String id = "U" + System.currentTimeMillis();

        boolean ok = gestion.registrarCliente(id, nombre, emailGenerado, pass1, dom);

        if (ok) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "¡Cuenta creada exitosamente!\nTu correo de acceso es: " + emailGenerado,
                "Registro exitoso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // Volver al login automáticamente
            quitar();
            java.awt.CardLayout cartas = (java.awt.CardLayout) loginCard1.getLayout();
            cartas.show(loginCard1, "card2");
        } else {
            nombreOblig.setText("* Ese nombre de usuario ya existe");
        }
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed

        String email = nombreusuario.getText().trim();
        String pass  = contraseña.getText().trim();

        // Validar campos vacíos
        if (email.isEmpty() || email.equals("Ingresa tu nombre de usuario aquí.")) {
            coincidenciausuario.setText("* Campo obligatorio");
            return;
        }
        if (pass.isEmpty() || pass.equals("Ingresa tu contraseña aquí")) {
            coincidenciacontra.setText("* Campo obligatorio");
            return;
        }

        modelo.Usuario usuario = gestion.login(email, pass);

        if (usuario != null) {
            // Login exitoso
            ventanaPrincipal.setUsuario(usuario);
            dispose(); // Cierra la ventana de autenticación
            ventanaPrincipal.toFront();
        } else {
            coincidenciausuario.setText("* Correo o contraseña incorrectos");
            coincidenciacontra.setText("*");
        }
   
    }//GEN-LAST:event_btnIniciarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new autenticacion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private gui.botonGradiente btnIniciar;
    private gui.botonGradiente btnRegistro;
    private javax.swing.JPanel cardLogin;
    private javax.swing.JPanel cardRegistro;
    private javax.swing.JLabel coincidencia;
    private javax.swing.JLabel coincidenciacontra;
    private javax.swing.JLabel coincidenciausuario;
    private javax.swing.JLabel contraoblig;
    private javax.swing.JTextField contraseña;
    private javax.swing.JTextField contraseña1;
    private javax.swing.JTextField contraseña2;
    private javax.swing.JTextField domicilio;
    private javax.swing.JLabel iniciarChange;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel linkRegistro;
    private gui.loginCard loginCard1;
    private javax.swing.JLabel nombreOblig;
    private javax.swing.JTextField nombreUsuario;
    private javax.swing.JTextField nombreusuario;
    private javax.swing.JPanel panelBg;
    // End of variables declaration//GEN-END:variables
}

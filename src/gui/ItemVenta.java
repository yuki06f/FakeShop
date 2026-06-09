package gui;

public class ItemVenta extends javax.swing.JPanel {

    public ItemVenta() {
        initComponents();
    }
    
    public void setDatos(modelo.Venta venta) {
        lblFolio.setText("Folio: " + venta.getFolio() + "  |  " + venta.getFechaFormateada());
        lblTotal.setText("$" + String.format("%,.2f", venta.getTotal()));
        
        String metodo = venta.getMetodoPago().equals("EFECTIVO") ? "Efectivo (OXXO) — Ref: " + venta.getReferenciaPago() : "Tarjeta terminación: " + venta.getReferenciaPago().substring(Math.max(0, venta.getReferenciaPago().length()-4));
        lblMetodo.setText(metodo);

        panelProductos.removeAll();
        for (modelo.Producto p : venta.getProductos()) {
            javax.swing.JPanel fila = new javax.swing.JPanel(new java.awt.BorderLayout());
            fila.setBackground(java.awt.Color.WHITE);
            fila.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 25));
            
            javax.swing.JLabel lblNom = new javax.swing.JLabel("• " + p.getNombre());
            lblNom.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            
            javax.swing.JLabel lblPrec = new javax.swing.JLabel("$" + String.format("%,.2f", p.getPrecioEfectivo()));
            lblPrec.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            lblPrec.setForeground(new java.awt.Color(120, 110, 130));
            
            fila.add(lblNom, java.awt.BorderLayout.WEST);
            fila.add(lblPrec, java.awt.BorderLayout.EAST);
            panelProductos.add(fila);
        }
        
        barraProgreso.setEstado(venta.getEstado().ordinal());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblFolio = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        panelProductos = new javax.swing.JPanel();
        lblMetodo = new javax.swing.JLabel();
        barraProgreso = new gui.BarraSeguimiento();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        jPanel1.setName(""); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        lblFolio.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFolio.setForeground(new java.awt.Color(65, 20, 60));
        lblFolio.setText("Folio #0000");
        jPanel1.add(lblFolio, java.awt.BorderLayout.WEST);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(50, 20, 50));
        lblTotal.setText("$0.00");
        lblTotal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(lblTotal, java.awt.BorderLayout.EAST);

        add(jPanel1);

        panelProductos.setLayout(new javax.swing.BoxLayout(panelProductos, javax.swing.BoxLayout.Y_AXIS));
        add(panelProductos);

        lblMetodo.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblMetodo.setForeground(new java.awt.Color(120, 110, 130));
        lblMetodo.setText("Método de Pago");
        lblMetodo.setAlignmentX(0.5F);
        lblMetodo.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(lblMetodo);
        add(barraProgreso);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.BarraSeguimiento barraProgreso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblMetodo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelProductos;
    // End of variables declaration//GEN-END:variables
}

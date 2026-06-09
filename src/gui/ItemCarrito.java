package gui;

public class ItemCarrito extends javax.swing.JPanel {

    public ItemCarrito() {
        initComponents();
    }
    
    public void setDatos(modelo.Producto p) {
    lblNombre.setText(p.getNombre());
    lblCategoria.setText(p.getCategoria());
    lblPrecio.setText("$" + String.format("%,.2f", p.getPrecioEfectivo()));
}
public javax.swing.JLabel getBtnEliminar() { return lblEliminar; }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblEliminar = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 255, 240), 1, true));
        setMaximumSize(new java.awt.Dimension(460, 80));
        setMinimumSize(new java.awt.Dimension(460, 80));
        setPreferredSize(new java.awt.Dimension(460, 80));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(50, 20, 50));
        lblNombre.setText("Nombre");
        add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 12, 350, -1));

        lblCategoria.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblCategoria.setForeground(new java.awt.Color(120, 110, 130));
        lblCategoria.setText("categoria");
        add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 32, 320, 20));

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(65, 20, 60));
        lblPrecio.setText("precio");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 50, 350, -1));

        lblEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEliminar.setText("🗑");
        lblEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(lblEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 25, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblEliminar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    // End of variables declaration//GEN-END:variables
}

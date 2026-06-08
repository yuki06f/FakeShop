package gui;

import modelo.Cliente;
import modelo.GestionTienda;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class PerfilDialog extends JDialog {

    // colores
    private final Color COLOR_MORADO_OSCURO = new Color(40, 15, 45);
    private final Color COLOR_MORADO_BASE   = new Color(65, 20, 60);
    private final Color COLOR_NARANJA       = new Color(225, 120, 80);
    private final Color COLOR_FONDO         = new Color(248, 246, 250);
    private final Color COLOR_BLANCO        = Color.WHITE;
    private final Color COLOR_TEXTO         = new Color(50, 20, 50);
    private final Color COLOR_GRIS          = new Color(120, 110, 130);
    private final Color COLOR_VERDE         = new Color(30, 140, 70);
    private final Color COLOR_ERROR         = new Color(200, 50, 50);

    private Cliente       cliente;
    private GestionTienda gestion;

    private JTextField     txtNombre;
    private JTextField     txtDomicilio;
    private JPasswordField txtPassActual;
    private JPasswordField txtPassNueva;
    private JPasswordField txtPassConfirm;
    private JLabel         lblMensaje;

    public PerfilDialog(Frame parent, Cliente cliente, GestionTienda gestion) {
        super(parent, "Mi perfil", true);
        this.cliente = cliente;
        this.gestion = gestion;

        setSize(460, 580);
        setResizable(false);
        setLocationRelativeTo(parent);
        construirUI();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_FONDO);
        root.add(crearHeader(),  BorderLayout.NORTH);
        root.add(crearCuerpo(),  BorderLayout.CENTER);
        root.add(crearFooter(),  BorderLayout.SOUTH);
        setContentPane(root);
    }

    private JPanel crearHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_MORADO_OSCURO);
        h.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel avatar = new JLabel(String.valueOf(
            cliente.getNombre().charAt(0)).toUpperCase()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(
                    0,0, COLOR_MORADO_BASE, getWidth(), 0, COLOR_NARANJA));
                g2.fillOval(0, 0, getWidth()-1, getHeight()-1);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(48, 48));
        avatar.setOpaque(false);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(0, 14, 0, 0));

        JLabel nombre = new JLabel(cliente.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nombre.setForeground(Color.WHITE);

        JLabel email = new JLabel(cliente.getEmail());
        email.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        email.setForeground(new Color(200, 185, 210));

        info.add(nombre);
        info.add(Box.createVerticalStrut(3));
        info.add(email);

        JLabel cerrar = new JLabel("✕");
        cerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cerrar.setForeground(new Color(200, 180, 210));
        cerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
            public void mouseEntered(MouseEvent e) { cerrar.setForeground(COLOR_NARANJA); }
            public void mouseExited(MouseEvent e)  { cerrar.setForeground(new Color(200,180,210)); }
        });

        JPanel izq = new JPanel(new BorderLayout());
        izq.setOpaque(false);
        izq.add(avatar, BorderLayout.WEST);
        izq.add(info,   BorderLayout.CENTER);

        h.add(izq,    BorderLayout.WEST);
        h.add(cerrar, BorderLayout.EAST);
        return h;
    }

    private JScrollPane crearCuerpo() {
        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(COLOR_FONDO);
        cuerpo.setBorder(new EmptyBorder(24, 32, 8, 32));

        // ── Sección datos personales ──
        cuerpo.add(crearSubtitulo("Datos personales"));
        cuerpo.add(Box.createVerticalStrut(12));

        txtNombre = crearCampo(cuerpo, "Nombre completo", cliente.getNombre(), false);
        txtDomicilio = crearCampo(cuerpo, "Domicilio de entrega",
            cliente.getDomicilio(), false);

        cuerpo.add(Box.createVerticalStrut(20));

        cuerpo.add(crearSubtitulo("🔒  Cambiar contraseña"));
        cuerpo.add(Box.createVerticalStrut(4));

        JLabel hint = new JLabel("Deja en blanco si no quieres cambiarla.");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(COLOR_GRIS);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        cuerpo.add(hint);
        cuerpo.add(Box.createVerticalStrut(12));

        txtPassActual  = crearCampoPass(cuerpo, "Contraseña actual");
        txtPassNueva   = crearCampoPass(cuerpo, "Nueva contraseña");
        txtPassConfirm = crearCampoPass(cuerpo, "Confirmar nueva contraseña");

        cuerpo.add(Box.createVerticalStrut(16));

        // Mensaje de estado
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensaje.setAlignmentX(Component.LEFT_ALIGNMENT);
        cuerpo.add(lblMensaje);

        JScrollPane scroll = new JScrollPane(cuerpo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        return scroll;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 14));
        footer.setBackground(COLOR_BLANCO);
        footer.setBorder(new MatteBorder(1,0,0,0, new Color(220,215,230)));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancelar.setForeground(COLOR_GRIS);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> dispose());

        JButton btnGuardar = crearBotonGradiente("Guardar cambios");
        btnGuardar.addActionListener(e -> guardarCambios());

        footer.add(btnCancelar);
        footer.add(btnGuardar);
        return footer;
    }

    private void guardarCambios() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoDom    = txtDomicilio.getText().trim();
        String passActual  = new String(txtPassActual.getPassword()).trim();
        String passNueva   = new String(txtPassNueva.getPassword()).trim();
        String passConfirm = new String(txtPassConfirm.getPassword()).trim();

        // Validar nombre
        if (nuevoNombre.isEmpty()) {
            mostrarError("El nombre no puede estar vacío.");
            return;
        }

        // Validar domicilio
        if (nuevoDom.isEmpty()) {
            mostrarError("El domicilio no puede estar vacío.");
            return;
        }

        // Si quiere cambiar contraseña
        if (!passNueva.isEmpty() || !passActual.isEmpty()) {
            if (!cliente.getPassword().equals(passActual)) {
                mostrarError("La contraseña actual es incorrecta.");
                return;
            }
            if (passNueva.length() < 4) {
                mostrarError("La nueva contraseña debe tener al menos 4 caracteres.");
                return;
            }
            if (!passNueva.equals(passConfirm)) {
                mostrarError("Las contraseñas nuevas no coinciden.");
                return;
            }
            // Aplicar nueva contraseña
            cliente.editarPerfil(nuevoNombre, passNueva, nuevoDom);
        } else {
            // Solo nombre y domicilio
            cliente.editarPerfil(nuevoNombre, cliente.getPassword(), nuevoDom);
        }

        gestion.guardarUsuarios();
        mostrarExito("¡Perfil actualizado correctamente!");

        // Limpiar campos de contraseña
        txtPassActual.setText("");
        txtPassNueva.setText("");
        txtPassConfirm.setText("");
    }

    private JLabel crearSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_MORADO_BASE);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField crearCampo(JPanel parent, String label,
                                   String valor, boolean soloLectura) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(COLOR_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lbl);
        parent.add(Box.createVerticalStrut(4));

        JTextField campo = new JTextField(valor);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setEditable(!soloLectura);
        campo.setBackground(soloLectura ? new Color(240, 237, 245) : COLOR_BLANCO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 200, 220), 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        parent.add(campo);
        parent.add(Box.createVerticalStrut(12));
        return campo;
    }

    private JPasswordField crearCampoPass(JPanel parent, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(COLOR_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lbl);
        parent.add(Box.createVerticalStrut(4));

        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 200, 220), 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        parent.add(campo);
        parent.add(Box.createVerticalStrut(12));
        return campo;
    }

    private JButton crearBotonGradiente(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(
                    0,0,COLOR_MORADO_BASE, getWidth(),0,COLOR_NARANJA));
                g2.fill(new RoundRectangle2D.Double(0,0,
                    getWidth(),getHeight(),12,12));
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void mostrarError(String msg) {
        lblMensaje.setText(msg);
        lblMensaje.setForeground(COLOR_ERROR);
    }

    private void mostrarExito(String msg) {
        lblMensaje.setText(msg);
        lblMensaje.setForeground(COLOR_VERDE);
    }
}
package modelo;

import java.io.*;
import java.net.Authenticator;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GestionVentas {

    private ArrayList<Venta> listaVentas;
    private final String ARCHIVO_VENTAS  = "ventas.dat";
    private final String CARPETA_TICKETS = "tickets/";

    // ─── Constructor ──────────────────────────────────────────────
    public GestionVentas() {
        listaVentas = new ArrayList<>();
        cargarVentas();
        new File(CARPETA_TICKETS).mkdirs(); // crea carpeta si no existe
    }

    // ─── Registrar una venta nueva ────────────────────────────────
    public Venta registrarVenta(Cliente cliente, String metodoPago,
                                String referenciaPago) {

        Venta venta = new Venta(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getEmail(),
            cliente.getCarrito(),
            metodoPago,
            referenciaPago
        );

        // Descontar stock
        for (Producto p : cliente.getCarrito()) {
            if (p.getStock() > 0) {
                p.setStock(p.getStock() - 1);
            }
        }

        listaVentas.add(venta);
        guardarVentas();
        generarTicketTXT(venta);

        // Enviar correo en hilo separado (no bloquea la UI)
        new Thread(() -> enviarCorreo(venta)).start();

        // Iniciar seguimiento automático en hilo separado
        iniciarSeguimiento(venta);

        return venta;
    }

    // ─── Persistencia binaria ─────────────────────────────────────
    public void guardarVentas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_VENTAS))) {
            oos.writeObject(listaVentas);
        } catch (IOException e) {
            System.out.println("Error al guardar ventas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarVentas() {
        File f = new File(ARCHIVO_VENTAS);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(f))) {
            listaVentas = (ArrayList<Venta>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error al cargar ventas: " + e.getMessage());
        }
    }

    // ─── Ticket TXT ───────────────────────────────────────────────
    public void generarTicketTXT(Venta v) {
        String ruta = CARPETA_TICKETS + v.getFolio() + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.println("╔══════════════════════════════════════════╗");
            pw.println("║           TIENDAMAX — TICKET             ║");
            pw.println("╚══════════════════════════════════════════╝");
            pw.println("Folio    : " + v.getFolio());
            pw.println("Fecha    : " + v.getFechaFormateada());
            pw.println("Cliente  : " + v.getNombreCliente());
            pw.println("Correo   : " + v.getEmailCliente());
            pw.println("──────────────────────────────────────────");
            pw.println("PRODUCTOS:");
            for (Producto p : v.getProductos()) {
                pw.printf("  %-28s  $%,.2f%n",
                    p.getNombre(), p.getPrecioEfectivo());
            }
            pw.println("──────────────────────────────────────────");
            pw.printf("  TOTAL:                           $%,.2f%n", v.getTotal());
            pw.println("──────────────────────────────────────────");
            pw.println("Método de pago : " + v.getMetodoPago());
            pw.println("Referencia     : " + v.getReferenciaPago());
            pw.println("Estado         : " + v.getEstado().getEtiqueta());
            pw.println("══════════════════════════════════════════");
            pw.println("  Gracias por tu compra en TiendaMax  ");
            pw.println("══════════════════════════════════════════");
        } catch (IOException e) {
            System.out.println("Error al generar ticket: " + e.getMessage());
        }
    }

    // ─── Correo electrónico (JavaMail) ────────────────────────────
    //  Requiere: javax.mail (mail.jar) en Libraries del proyecto
    //  Si no tienes el jar, el catch captura el error silenciosamente
    private void enviarCorreo(Venta v) {
        // ── Configura aquí tu cuenta Gmail de prueba ──────────────
        final String REMITENTE  = "tiendamax.notif@gmail.com"; // cambia esto
        final String PASSWORD    = "tu_contrasena_app";         // contraseña de aplicación
        // ─────────────────────────────────────────────────────────

        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(REMITENTE, PASSWORD);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(REMITENTE));
            msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(v.getEmailCliente()));
            msg.setSubject("Tu compra en TiendaMax — Folio " + v.getFolio());

            StringBuilder sb = new StringBuilder();
            sb.append("Hola ").append(v.getNombreCliente()).append(",\n\n");
            sb.append("Tu compra ha sido registrada exitosamente.\n\n");
            sb.append("Folio   : ").append(v.getFolio()).append("\n");
            sb.append("Fecha   : ").append(v.getFechaFormateada()).append("\n");
            sb.append("Total   : $").append(String.format("%,.2f", v.getTotal())).append("\n");
            sb.append("Método  : ").append(v.getMetodoPago()).append("\n");
            if (v.getMetodoPago().equals("EFECTIVO")) {
                sb.append("Referencia OXXO: ").append(v.getReferenciaPago()).append("\n");
                sb.append("Tienes 48 horas para realizar el pago.\n");
            }
            sb.append("\nGracias por comprar en TiendaMax.");
            msg.setText(sb.toString());

            Transport.send(msg);
            System.out.println("Correo enviado a: " + v.getEmailCliente());

        } catch (Exception e) {
            // Si no hay jar de JavaMail o falla la red, solo se loguea
            System.out.println("Correo no enviado (configura JavaMail): "
                + e.getMessage());
        }
    }

    // ─── Hilo de seguimiento automático ──────────────────────────
    //  Avanza el estado de la venta cada N minutos simulando el proceso
    private void iniciarSeguimiento(Venta venta) {
        Thread hilo = new Thread(() -> {
            // Intervalos en ms: 2 min, 5 min, 10 min, 15 min
            int[] intervalos = {120_000, 300_000, 600_000, 900_000};
            int i = 0;

            // Si fue EFECTIVO, espera confirmación manual antes de avanzar
            // (en una versión real se validaría con el banco)
            // Para demo, avanzamos igual después del primer intervalo
            while (i < intervalos.length) {
                try {
                    Thread.sleep(intervalos[i]);
                } catch (InterruptedException ex) {
                    break;
                }
                boolean sigue = venta.avanzarEstado();
                guardarVentas(); // persistir el nuevo estado
                System.out.println("Pedido " + venta.getFolio()
                    + " → " + venta.getEstado().getEtiqueta());
                if (!sigue) break;
                i++;
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    // ─── Consultas ────────────────────────────────────────────────
    public ArrayList<Venta> getVentasDeCliente(String idCliente) {
        ArrayList<Venta> resultado = new ArrayList<>();
        for (Venta v : listaVentas) {
            if (v.getIdCliente().equals(idCliente)) resultado.add(v);
        }
        return resultado;
    }

    public ArrayList<Venta> getTodasLasVentas() { return listaVentas; }

    public double getTotalIngresos() {
        return listaVentas.stream().mapToDouble(Venta::getTotal).sum();
    }
}
package demo;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import javax.mail.MessagingException;

import com.itextpdf.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.err.println("--- Inicio");
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        try {
            Factura factura = new Factura(
                "Juan Pérez",
                "123456789",
                500.00,
                1,
                LocalDate.now(),
                "cliente@example.com"
            );
            System.err.println("--- Carga datos");

            // Generar el PDF de la factura
            factura.generarPDF();
            System.err.println("--- Se genera el PDF");

            // Enviar la factura por correo electrónico
            factura.enviarCorreo();
            System.err.println("--- Se envia el correo");

            System.out.println("Factura generada y enviada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear la factura: " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }

    private static void println(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'println'");
    }
}


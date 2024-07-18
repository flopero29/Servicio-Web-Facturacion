package demo;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.layout.Document;
import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;

public class Factura{
    public String cliente;
    public String usuario;
    public double monto;
    public int numeroDeFactura;
    public LocalDate fecha;
    public String email;
    
    public FileDataSource fileDataSource = new FileDataSource("factura.pdf");
    private String smtpUser = "factofastfacturacion@gmail.com";
    private String smtpPassword = "nnblaltxpedmzpum";

    public Factura(String cliente, String id, double monto, int numeroDeFactura, LocalDate fecha, String email) {
        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo ni vacío");
        }
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo ni vacío");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser un valor positivo");
        }
        if (numeroDeFactura <= 0) {
            throw new IllegalArgumentException("El número de factura debe ser un entero positivo");
        }
    
        this.cliente = cliente;
        this.usuario = usuario;
        this.monto = monto;
        this.numeroDeFactura = numeroDeFactura;
        this.fecha = fecha;
        this.email = email;
    }
    //Getters
    public String getcliente (){
        return cliente;
    }

    public String getusuario(){
        return usuario;
    }
    
    public double getmonto(){
        return monto;
    }
    
    public int getnumeroDeFactura(){
        return numeroDeFactura;
    }
    
    public LocalDate getfecha(){
        return fecha;
    }
    
    public String getemail(){
        return email;
    }
    //Setters
    public void setcliente(String cliente) {
        this.cliente = cliente;
    }
    
    public void setid(String usuario){
        this.usuario = usuario;
    }

    public void setmonto(double monto){
        this.monto = monto;
    }

    public void setnumeroDeFactura(int numeroDeFactura){
        this.numeroDeFactura = numeroDeFactura;
    }

    public void setfecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public void setemail(String email){
        this.email = email;
    }

    //Métodos
    public void generarPDF()throws MessagingException, FileNotFoundException{
       String pdfPath = "factura_" + numeroDeFactura + ".pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Factura Electrónica"));
        document.add(new Paragraph("Número de factura: " + numeroDeFactura));
        document.add(new Paragraph("Cliente: " + cliente));
        document.add(new Paragraph("Fecha: " + fecha));
        document.add(new Paragraph("Monto: " + monto));

        document.close();
    }

    public void enviarCorreo() throws MessagingException, IOException {
        System.err.println("--- Entra a enviarCorreo");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.err.println("--- Entra a autenticacion");
                return new PasswordAuthentication(smtpUser, smtpPassword);
            }
        };
    
        Session session = Session.getInstance(props, auth);
    
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("facturacionfactofact@gmail.com"));
        //message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("joseab2907@gmail.com"));
        message.setSubject("Ha recibido una factura electrónica nueva");
    
        // Crear el cuerpo del mensaje
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Estimado cliente, Adjunto su factura electrónica emitida en tal fecha con un importe total de $" + this.monto + ".\nAtentamente el personal de facturación");
    
        System.err.println("--- Crea mensaje del cuerpo");

        // Adjuntar el archivo PDF
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(this.getPdfPath());
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName("factura_" + this.numeroDeFactura + ".pdf");
    
        System.err.println("--- Adjunto el archivo pdf");

        // Crear el mensaje multipart
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        System.err.println("--- Crea mensaje multipart");

        message.setContent(multipart);
    
        System.err.println("--- Multipart");

        try {
            Transport.send(message);
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.err.println("--- Entra a Enviar mensaje");
    }
public String getPdfPath() {
    return "factura_" + this.numeroDeFactura + ".pdf";
}

}

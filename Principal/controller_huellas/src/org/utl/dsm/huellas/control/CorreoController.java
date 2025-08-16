package org.utl.dsm.huellas.control;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

public class CorreoController {

public void enviarCorreo(String correoDestino, String asunto, String nombreAnimal, String nombreAdoptante, String plantilla)  {
    try {
        String remitente = "dayronlawl@gmail.com";
        String contra = "wtcfgsplvzhvsneb";

        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        Session session = Session.getInstance(p, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contra);
            }
        });
 InputStream inputStream = getClass().getClassLoader().getResourceAsStream(plantilla);
        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo HTML.");
        }

        String contenido = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));


        contenido = contenido
                .replace("${adoptante.nombre}", nombreAdoptante)
                .replace("${animal.nombre}", nombreAnimal);

      
        Message mensajeCorreo = new MimeMessage(session);
        mensajeCorreo.setFrom(new InternetAddress(remitente));
        mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
        mensajeCorreo.setSubject(asunto);
        mensajeCorreo.setContent(contenido, "text/html; charset=utf-8");

        Transport.send(mensajeCorreo);

        System.out.println("Correo enviado con éxito a " + correoDestino);
    } catch (MessagingException e) {
        System.out.println(" Error al enviar el correo:");
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println(" Error al procesar el contenido del correo:");
        e.printStackTrace();
    }
}
public void enviarCorreoPendiente(String correo, String nombreAnimal, String nombreAdoptante) {
    enviarCorreo(
        correo,
        "Solicitud en revisión - Huellitas Suaves",
        nombreAnimal,
        nombreAdoptante,
        "main/resources/correos/Pendiente.html"
    );
}

public void enviarCorreoAceptado(String correo, String nombreAnimal, String nombreAdoptante) {
    enviarCorreo(
        correo,
        "¡Tu solicitud fue ACEPTADA! - Huellitas Suaves",
        nombreAnimal,
        nombreAdoptante,
        "main/resources/correos/Aceptada.html"
    );
}

public void enviarCorreoRechazado(String correo, String nombreAnimal, String nombreAdoptante) {
    enviarCorreo(
        correo,
        "Tu solicitud fue rechazada - Huellitas Suaves",
        nombreAnimal,
        nombreAdoptante,
        "main/resources/correos/Rechazada.html"
    );
}


}

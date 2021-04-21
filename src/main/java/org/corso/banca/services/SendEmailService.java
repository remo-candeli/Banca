package org.corso.banca.services;

import org.corso.eccezioni.ErroreInvioEmailException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Invia una email ad un destinatario.
 * Il la password per autenticarsi ad un SMTP é prelevata (per questioni di sicurezza)
 * da una variabile di ambiente (emailPassword) definita nel sistema di esecuzione.
 * Non é dunque visibile neanche dal programmatore!
 *
 */
public class SendEmailService {


    /**
     * Se la password di autenticazione allo SMTP non é definita il sistema non tenta di inviare l'email.
     *
     * @param indirizzoEmailDestinatario
     * @throws ErroreInvioEmailException
     */
    public void sendEmail(String indirizzoEmailDestinatario, String indirizzoEmailMittente) throws ErroreInvioEmailException {
        if (indirizzoEmailDestinatario==null)
            throw new ErroreInvioEmailException("Indirizzo destinatario nullo");
        String username = indirizzoEmailMittente;
        String passowrd = System.getenv("emailPassword");
        if (passowrd==null)
            return;
        // Create all the needed properties
        Properties prop = new Properties();
        // SMTP host
        prop.put("mail.smtp.host", "smtp.gmail.com");
        // Is authentication enabled
        prop.put("mail.smtp.auth", "true");
        // Is TLS enabled
        prop.put("mail.smtp.starttls.enable", "true");
        // SSL Port
        prop.put("mail.smtp.socketFactory.port", "465");
        // SSL Socket Factory class
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // SMTP port, the same as SSL port :)
        prop.put("mail.smtp.port", "465");

        System.out.print("Creating the session...");

        // Create the session
        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {	// Define the authenticator
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,passowrd);
                    }
                });

        System.out.println("done!");

        // Create and send the message
        try {
            // Create the message
            Message message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress(username));
            // Set the recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(indirizzoEmailDestinatario));
            // Set message subject
            message.setSubject("Apertura conto corrente!");
            // Set message text
            message.setText("Benvenuto)");

            System.out.print("Sending message...");
            // Send the message
            Transport.send(message);

            System.out.println("done!");

        } catch (Exception e) {
            throw new ErroreInvioEmailException((e.getMessage()));
        }
    }
}
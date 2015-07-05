package com.artema;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author edieguez
 */
public class HTMLEmailSender {

    private ResourceBundle properties;
    private String user;
    private String password;
    private String host;
    private String port;

    private final Properties SMTPproperties;
    private final Session session;
    private final VelocityEngine engine;
    private final Template template;
    private final VelocityContext context;

    public HTMLEmailSender(String templateName) {
        this.engine = new VelocityEngine();
        this.engine.init();
        this.context = new VelocityContext();
        this.template = engine.getTemplate(templateName, "UTF-8");

        // Auth settings
        this.properties = ResourceBundle.getBundle("email");
        this.user = properties.getString("user");
        this.password = properties.getString("password");
        this.host = properties.getString("host");
        this.port = properties.getString("port");

        this.SMTPproperties = new Properties();
        this.SMTPproperties.put("mail.smtp.host", this.host);
        this.SMTPproperties.put("mail.smtp.starttls.enable", "true");
        this.SMTPproperties.put("mail.smtp.port", this.port);
        this.SMTPproperties.put("mail.smtp.mail.sender", this.user);
        this.SMTPproperties.put("mail.smtp.user", this.user);
        this.SMTPproperties.put("mail.smtp.password", this.password);
        this.SMTPproperties.put("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };

        this.session = Session.getDefaultInstance(SMTPproperties, auth);
    }

    public void setContextValue(String key, Object value) {
        this.context.put(key, value);
    }

    public String getHTML() {
        StringWriter writer = new StringWriter();
        this.template.merge(this.context, writer);

        return writer.toString();
    }

    public void sendMail(String to, String subject, Map<String, String> images, String... attachments) {
        try {
            Message MIMEmessage = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();

            MIMEmessage.setFrom(new InternetAddress(this.user));
            InternetAddress[] toAddresses = {new InternetAddress(to)};
            MIMEmessage.setRecipients(Message.RecipientType.TO, toAddresses);
            MIMEmessage.setSubject(subject);
            MIMEmessage.setSentDate(new Date());

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(this.getHTML(), "text/html");
            multipart.addBodyPart(bodyPart);

            // Images
            if (images != null) {
                for (Map.Entry<String, String> image : images.entrySet()) {
                    MimeBodyPart imageAttachment = new MimeBodyPart();

                    imageAttachment.setHeader("Content-ID", "<" + image.getKey() + ">");
                    imageAttachment.attachFile(image.getValue());
                    imageAttachment.setDisposition(MimeBodyPart.INLINE);

                    multipart.addBodyPart(imageAttachment);
                }
            }

            // Attachments
            for (String attachment : attachments) {
                MimeBodyPart fileAttachment = new MimeBodyPart();
                fileAttachment.attachFile(attachment);
                multipart.addBodyPart(fileAttachment);
            }

            MIMEmessage.setContent(multipart);
            Transport.send(MIMEmessage);
        } catch (MessagingException | IOException ex) {
            System.out.println("HTMLEmailSender.sendEmail()");
            System.out.println(ex);
        }
    }
}

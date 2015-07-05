package com.artema;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author edieguez
 */
public class Main {

    public static void main(String[] args) {
        String to = "mail@gmail.com";
        String subject = "A very important subject";

        // Creation of a HTMLEmailSender object. You must provide a templame name
        HTMLEmailSender emailSender = new HTMLEmailSender("template/index.html");

        // Add some values to the context. This variables can be used inside
        // the template
        emailSender.setContextValue("title", "HTMLEmailSender");
        emailSender.setContextValue("subtheme_1", "Requeriments");
        emailSender.setContextValue("subtheme_2", "How to use it?");

        // To add images in your template you need to put them in a map. If
        // your template has no images you can send a null value.
        // The key is the content-id and the value is the image path
        Map<String, String> images = new HashMap<>();
        images.put("java_logo", "template/images/java_logo.png");

        // You can also add attachments after the images argument
        emailSender.sendMail(to, subject, images, "LICENSE");
    }
}

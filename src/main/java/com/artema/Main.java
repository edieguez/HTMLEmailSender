package com.artema;

/**
 *
 * @author edieguez
 */
public class Main {
    public static void main(String[] args) {
        HTMLEmailSender emailSender = new HTMLEmailSender("template.html");
        emailSender.sendMail("aeelinn@gmail.com", "A very important subject", null, args);
    }
}

# HTMLEmailSender
A simple [Java](https://www.java.com/)
 class to send emails using a Velocity template and the JavaMail API. You can also include images attachments

## Requeriments
If you are using maven you can just add this dependencies to your pom.xml file or you can
download the jar's in the following links
* [javax.mail-api 1.5.4](http://mvnrepository.com/artifact/javax.mail/javax.mail-api/1.5.4)
* [mail 1.4.7](http://mvnrepository.com/artifact/javax.mail/mail/1.4.7)
* [velocity 1.7](http://mvnrepository.com/artifact/org.apache.velocity/velocity/1.7)

## How to use it?
With this code you can send a mail like this README file and an attachment file
```java
public static void main(String[] args) {
    String to = "mail@gmail.com";
    String subject = "A very important subject";

    // Creation of a HTMLEmailSender object. You must provide a templame name
    HTMLEmailSender emailSender = new HTMLEmailSender("template/index.html");

    // To add images in your template you need to put them in a map. If
    // your template has no images you can send a null value.
    // The key is the content-id and the value is the image path
    Map<String, String> images = new HashMap<>();
    images.put("java_logo", "template/images/java_logo.png");

    // You can also add attachments after the images argument
    emailSender.sendMail(to, subject, images, "LICENSE");
}
```

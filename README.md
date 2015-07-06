# HTMLEmailSender
![java logo](https://raw.githubusercontent.com/edieguez/HTMLEmailSender/master/template/images/java_logo.png)

A simple [Java](https://www.java.com/)
 class to send emails using a Velocity template and the JavaMail API. You can also include images and attachments
## Requeriments
If you are using maven you can just add this dependencies to your pom.xml file or you can
download the jar's in the following links
* [javax.mail-api 1.5.4](http://mvnrepository.com/artifact/javax.mail/javax.mail-api/1.5.4)
* [mail 1.4.7](http://mvnrepository.com/artifact/javax.mail/mail/1.4.7)
* [velocity 1.7](http://mvnrepository.com/artifact/org.apache.velocity/velocity/1.7)

## How to use it?
First, you need to edit the ***src/main/resources/email.properties*** file. This settings
are for a [Gmail](https://mail.google.com) account.
If you use a diferent email service the host will be diferent, and maybe the port too
```
user: your_email@gmail.com
password: your_password
host: smtp.gmail.com
port: 587
```
With this code you can send a mail like this README file and an attachment file
```java
public static void main(String[] args) {
    String to = "mail@gmail.com";
    String subject = "A very important subject";

    // Creation of a HTMLEmailSender object. You must provide a template name
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
```
You can replace variables inside the template using the following sintax.
More about Velocity templates [here](http://velocity.apache.org/engine/devel/vtl-reference-guide.html)
```html
<h1>$title</h1>
```
To use images inside your mail you must refer to them by their content-id
```html
<img src="cid:java_logo" alt="java logo">
```

package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.services.EmailServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServicesImplementation implements EmailServices {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;


    @Override
    public void sendEmail(String to, String subject, String body) {
        logger.info("Sending email to {}",to);
        String fullMessage = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>RideX</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                    margin: 0;
                    padding: 0;
                    background-color: #f7f7f7;
                }
                .email-container {
                    width: 100%%;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);
                    border: 1px solid #ddd;
                }
                .header {
                    background-color: #007BFF;
                    color: #fff;
                    text-align: center;
                    padding: 10px 0;
                    border-radius: 8px 8px 0 0;
                }
                .header h1 {
                    margin: 0;
                }
                .content {
                    padding: 20px;
                    line-height: 1.6;
                }
                .footer {
                    text-align: center;
                    padding: 10px;
                    font-size: 14px;
                    color: #888;
                }
                .footer a {
                    color: #007BFF;
                    text-decoration: none;
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <h1>RideX</h1>
                </div>
                <div class="content">
                    <p>Hello %s,</p>
                    <p>You are receiving this email from the RideX regarding an important update. Here's the message:</p>
                    <p><strong>%s</strong></p>
                    <p>If you have any questions or need assistance, feel free to reach out to us.</p>
                    <p>Thank you for being a part of RideX!</p>
                </div>
                <div class="footer">
                    <p>RideX &copy; 2024</p>
                    <p><a href="#">Privacy Policy</a> | <a href="#">Unsubscribe</a></p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(to, body);

        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            messageHelper.setFrom(domainName);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(fullMessage, true);
            mailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }

//    @Override
//    public void sendEmailWithHTML(String[] to, String subject, String body) {
//
//    }
}

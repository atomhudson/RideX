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
    private String from;

    @Value("${app.frontend.url}")
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
                               *{ margin:0; padding:0; box-sizing:border-box;}
                               body{ background:#0a0a0a; font-family:Arial,Helvetica,sans-serif; color:#f5f5f5;padding:40px 20px;}
                               .wrapper{ max-width:640px; margin:auto; }
                               .card{ background:#111111; border:1px solid rgba(255,255,255,.08); border-radius:20px; overflow:hidden;
                                   box-shadow:
                                       0 10px 40px rgba(0,0,0,.55),
                                       0 0 80px rgba(16,185,129,.08);
                               }
                               .header{ background:linear-gradient(135deg,#0f0f0f,#151515); text-align:center; padding:45px 30px; border-bottom:1px solid rgba(255,255,255,.08); }
                               .logo{ width:68px; height:68px; line-height:68px; margin:auto; background:rgba(16,185,129,.12); border:1px solid rgba(16,185,129,.35); border-radius:50%%; font-size:30px; }
                               .header h1{ margin-top:18px; font-size:34px; color:#ffffff; letter-spacing:1px; }
                               .header p{ margin-top:8px; color:#A3A3A3; font-size:15px; }
                               .content{ padding:40px; }
                               .greeting{ font-size:24px; font-weight:bold; margin-bottom:22px; }
                               .text{ color:#CFCFCF; line-height:1.8; font-size:16px; margin-bottom:24px; }
                               .message-box{ background:rgba(255,255,255,.04); border-left:5px solid #10B981; border-radius:12px; padding:24px; margin:30px 0;}
                               .message-box h3{ color:#10B981; margin-bottom:12px; font-size:17px; }
                               .message-box p{ color:#F5F5F5; font-size:16px; line-height:1.8; }
                               .button-container{ text-align:center; margin:38px 0; }
                               .button{ display:inline-block; background:#10B981; color:#0a0a0a !important; text-decoration:none; font-weight:bold; padding:16px 34px; border-radius:999px; font-size:16px; }
                               .note{ margin-top:25px; padding:18px; border-radius:12px; background:rgba(16,185,129,.08); border:1px solid rgba(16,185,129,.18); color:#A3A3A3; font-size:14px; line-height:1.7; }
                               .footer{ padding:28px; text-align:center; border-top:1px solid rgba(255,255,255,.08); color:#737373; font-size:13px; }
                               .footer a{ color:#10B981; text-decoration:none; }
                               .social{ margin:18px 0; }
                               .badge{ display:inline-block; padding:7px 16px; background:rgba(16,185,129,.12); border:1px solid rgba(16,185,129,.3); border-radius:999px; color:#10B981; font-size:12px; letter-spacing:1px; text-transform:uppercase; font-weight:bold; }
                               @media only screen and (max-width:600px){
                                   .content{ padding:28px; }
                                   .header{ padding:35px 20px; }
                                   .header h1{ font-size:28px; }
                                   .greeting{ font-size:21px; }
                                   .text{ font-size:15px; }
                                   .button{ width:100%%; padding:15px; }
                               }
                           </style>
                       </head>
                       <body>
                       <div class="wrapper">
                           <div class="card">
                               <div class="header">
                                   <h1 class="logo">RideX</h1>
                                   <p>Premium Ride Sharing Platform</p>
                                   <div style="margin-top:20px;">
                                       <span class="badge">Important Notification</span>
                                   </div>
                               </div>
                               <div class="content">
                                   <div class="greeting">
                                       Hello %s 👋
                                   </div>
                                   <div class="text">
                                       Thank you for being a valued member of <strong>RideX</strong>.
                                       We're reaching out regarding an important update for your account.
                                   </div>
                                   <div class="message-box">
                                       <h3>Your Message</h3>
                                       <p>%s</p>
                                   </div>
                                   <div class="button-container">
                                       <a href="%s" class="button">
                                           Open RideX
                                       </a>
                                   </div>
                                   <div class="note">
                                       <b>Security Tip</b><br><br>
                                       If you weren't expecting this email, you can safely ignore it.
                                       Never share verification codes or account credentials with anyone.
                                   </div>
                               </div>
                               <div class="footer">
                                   <div class="social">
                                       Ride Smart • Ride Safe • Ride Together
                                   </div>
                                   <p>
                                       Need help?
                                       <a href="mailto:support@ridex.com">support@ridex.com</a>
                                   </p>
                                   <br>
                                   <p>
                                       © 2026 RideX. All Rights Reserved.
                                   </p>
                               </div>
                           </div>
                       </div>
                       </body>
                       </html>
        """.formatted(to,body,domainName);

        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(fullMessage, true);
            mailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }
}

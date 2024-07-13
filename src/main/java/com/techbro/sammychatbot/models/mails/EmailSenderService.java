package com.techbro.sammychatbot.models.mails;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.from_name}")
    private String fromName;
    @Value("${mailer_sender.api.key}")
    private String apiKey;


    @Async
    private void sendEmail(String to, String subject, String content,boolean isHtml)  {
        Email email = new Email();
        email.setFrom(fromName, from);
        email.addRecipient(to, to);
        email.setSubject(subject);
        if(isHtml){
            email.setHtml(content);
        }else{
            email.setPlain(content);
        }

        MailerSend ms = new MailerSend();
        ms.setToken(apiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    public void sendOTPMail(String email, String otp)  {
        try {
            String otpTemplate = getOTPTemplate(otp);
            sendEmail(email,"Password Reset Otp",otpTemplate,true);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Async
    public void sendWelcomeEmail(String email)  {
        try {
            String welcomeEmail = getWelcomeEmail();
            sendEmail(email,"Welcome To Chatbot",welcomeEmail,true);
        }catch (Exception e){
            System.out.println(e);
        }
    }


    private String getOTPTemplate(String token) {

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Password Reset OTP</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            color: #333333;\n" +
                "            font-size: 24px;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .content p {\n" +
                "            color: #666666;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .otp {\n" +
                "            display: block;\n" +
                "            width: fit-content;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 10px 20px;\n" +
                "            text-align: center;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            font-size: 24px;\n" +
                "            letter-spacing: 4px;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "            color: #999999;\n" +
                "            font-size: 12px;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #4CAF50;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Password Reset</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Hi there,</p>\n" +
                "            <p>Your Request to reset password has been initiated. Please use the following OTP to complete your password reset process:</p>\n" +
                "            <div class=\"otp\">" + token + "</div>\n" +
                "            <p>If you did not request a password reset, please ignore this email or contact our support team at <a href=\"mailto:programer.projava@gmail.com \">programer.projava@gmail.com </a>.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2024 Sammy Chatbot. All rights reserved.</p>\n" +
                "            <p><a href=\"https://abey-dev.netlify.app\">Contact Us</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    private String getWelcomeEmail() {
            return "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Welcome to Sammy Chatbot!</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #f4f4f4;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "        .container {\n" +
            "            width: 100%;\n" +
            "            max-width: 600px;\n" +
            "            margin: 0 auto;\n" +
            "            background-color: #ffffff;\n" +
            "            padding: 20px;\n" +
            "            border-radius: 8px;\n" +
            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "        .header {\n" +
            "            text-align: center;\n" +
            "            padding: 10px 0;\n" +
            "        }\n" +
            "        .header h1 {\n" +
            "            color: #333333;\n" +
            "            font-size: 24px;\n" +
            "            margin: 0;\n" +
            "        }\n" +
            "        .content {\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        .content p {\n" +
            "            color: #666666;\n" +
            "            line-height: 1.6;\n" +
            "        }\n" +
            "        .button {\n" +
            "            display: block;\n" +
            "            width: 200px;\n" +
            "            margin: 20px auto;\n" +
            "            padding: 10px;\n" +
            "            text-align: center;\n" +
            "            background-color: #4CAF50;\n" +
            "            color: #ffffff;\n" +
            "            text-decoration: none;\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        .footer {\n" +
            "            text-align: center;\n" +
            "            margin-top: 20px;\n" +
            "            color: #999999;\n" +
            "            font-size: 12px;\n" +
            "        }\n" +
            "        .footer a {\n" +
            "            color: #4CAF50;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"header\">\n" +
            "            <h1>Welcome to Sammy Chatbot!</h1>\n" +
            "        </div>\n" +
            "        <div class=\"content\">\n" +
            "            <p>Hi there,</p>\n" +
            "            <p>Thank you for signing up to use Sammy Chatbot. It's exciting to have you on board and look forward to helping in side projects and contracts.</p>\n" +
            "            <p>If you have any questions or need assistance, feel free to reach out to me at <a href=\"mailto:programer.projava@gmail.com\">programer.projava@gmail.com </a>.</p>\n" +
            "            <p>You can also check out our <a href=\"#\" style=\"color: #4CAF50;\">documentation</a> for more information on how to use and test Sammy Chatbot.</p>\n" +
            "            <a href=\"#\" class=\"button\">Get Started</a>\n" +
            "        </div>\n" +
            "        <div class=\"footer\">\n" +
            "            <p>&copy; 2024 Sammy Chatbot. All rights reserved.</p>\n" +
            "            <p><a href=\"https://abey-dev.netlify.app/contact\">Contact Us</a></p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";
    }
}
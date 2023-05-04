package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.MailDto;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class SendGridMailService {
    private SendGrid sendGridClient;

    public void sendText(String to, MailDto mailDto) {
        Response response = sendEmail(to, mailDto.getSubject(), new Content("text/plain", mailDto.getContent()));
        log.info("Status Code: " + response.getStatusCode() + ", To: " + to);
    }

    public void sendHTML(String to, MailDto mailDto) {
        Response response = sendEmail(to, mailDto.getSubject(), new Content("text/html", mailDto.getContent()));
        log.info("Status Code: " + response.getStatusCode() + ", To: " + to);
    }

    private Response sendEmail(String to, String subject, Content content) {
        String from = "'Bò Nướng Vỉa Hè' <nhuthuynh.phu@gmail.com>";
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(from));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return response;
    }
}

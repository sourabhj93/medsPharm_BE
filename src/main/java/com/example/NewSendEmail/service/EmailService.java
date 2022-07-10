package com.example.NewSendEmail.service;

import com.example.NewSendEmail.DTO.CustomerInfoDTO;
import com.example.NewSendEmail.DTO.EmailDetails;
import com.example.NewSendEmail.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.nio.file.Path;

@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    EmailDetails emailDetails;
    public void sendEmailWithAttachment(CustomerInfoDTO customerInfo, Path path) {
        String message = "Hey Pharmcarer,\n\n you have 1 order to deliver!!\n\n For Prescriptions, Please find attached pdf.\n\nHere are the details of Customer :\n\nCustomer Name : " + customerInfo.getCustomerName() + "\nCustomer Contact Number : " + customerInfo.getMobileNumber() + "\nCustomer Address : " + customerInfo.getAddress();
        String subject = "Welcome To PharmCare - Details of Customer :" + customerInfo.getCustomerName();

        Session session = commonUtil.getSession();

        // STEP2: Compose the message for mails with attachments
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(emailDetails.getUsername()));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDetails.getReceipent()));
            mimeMessage.setSubject(subject);
            String path1 = String.valueOf(path.getFileName());
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart textMime = new MimeBodyPart();
            MimeBodyPart fileMime = new MimeBodyPart();
            textMime.setText(message);
            File file1 = new File(path1);
            fileMime.attachFile(file1);
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(fileMime);
            mimeMessage.setContent(mimeMultipart);

            //STEP3: send the message
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

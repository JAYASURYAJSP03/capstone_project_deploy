package com.project.capstone.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Generate PDF and send email
    public void sendEmailWithAttachment(String to, String subject, String body) throws Exception {
        // Create PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        // Add content to the PDF (example ticket content)
        document.add(new Paragraph(body));
        document.close();

        // Create email message with attachment
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("Please find your bus booking details attached.");
        helper.setFrom("jayasuryajsp01@gmail.com");

        // Attach the PDF
        helper.addAttachment("BusTicket.pdf", new ByteArrayDataSource(byteArrayOutputStream.toByteArray(), "application/pdf"));

        // Send the email
        mailSender.send(mimeMessage);
    }
}

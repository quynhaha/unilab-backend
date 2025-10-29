package com.unilab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingRejectionEmail(String recipientEmail, String title,
                                          java.time.LocalDateTime startTime, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Lab Booking Has Been Rejected");

        String formattedDate = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        message.setText(String.format(
                "Dear user,\n\n" +
                        "Your booking \"%s\" scheduled for %s has been rejected.\n\n" +
                        "Reason: %s\n\n" +
                        "Best regards,\n" +
                        "UniLab Team",
                title, formattedDate, reason
        ));

        mailSender.send(message);
        System.out.println("Rejection email sent to: " + recipientEmail);
    }

    public void sendBookingApprovalEmail(String recipientEmail, String title,
                                         java.time.LocalDateTime startTime, java.time.LocalDateTime endTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Lab Booking Has Been Approved");

        String formattedStart = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String formattedEnd = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        message.setText(String.format(
                "Dear user,\n\n" +
                        "Your booking \"%s\" from %s to %s has been approved.\n\n" +
                        "Please be present on time and follow all lab safety guidelines.\n\n" +
                        "Best regards,\n" +
                        "UniLab Team",
                title, formattedStart, formattedEnd
        ));

        mailSender.send(message);
        System.out.println("Approval email sent to: " + recipientEmail);
    }
}

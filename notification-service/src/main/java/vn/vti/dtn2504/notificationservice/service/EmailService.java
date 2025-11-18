package vn.vti.dtn2504.notificationservice.service;

import vn.vti.dtn2504.notificationservice.entity.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
}

package vn.vti.dtn2504.notificationservice.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.notificationservice.dto.SendNotificationRequest;
import vn.vti.dtn2504.notificationservice.entity.EmailDetails;
import vn.vti.dtn2504.notificationservice.service.EmailService;
import vn.vti.dtn2504.notificationservice.service.SendNotificationService;

@RequestMapping(value = "/api/v1/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final SendNotificationService sendNotificationService;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody SendNotificationRequest sendNotificationRequest) {
        sendNotificationService.sendNotification(sendNotificationRequest);
        return ResponseEntity.ok().build();
    }

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }
}

package vn.vti.dtn2504.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.notificationservice.entity.EmailDetails;
import vn.vti.dtn2504.notificationservice.service.EmailService;

@RequestMapping(value = "/api/v1/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private EmailService emailService;


    // Sending a simple Email
    @RabbitListener(
            queues = "${queue.notification.queue}"
    )
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }
}

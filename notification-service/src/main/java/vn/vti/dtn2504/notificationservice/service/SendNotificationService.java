package vn.vti.dtn2504.notificationservice.service;

import org.springframework.web.bind.annotation.RequestBody;
import vn.vti.dtn2504.notificationservice.dto.SendNotificationRequest;

public interface SendNotificationService {
    void sendNotification(SendNotificationRequest sendNotificationRequest);
}

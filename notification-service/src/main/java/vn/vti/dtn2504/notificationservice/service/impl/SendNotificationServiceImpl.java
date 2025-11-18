package vn.vti.dtn2504.notificationservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.vti.dtn2504.notificationservice.dto.SendNotificationRequest;
import vn.vti.dtn2504.notificationservice.service.SendNotificationService;

@Service
@Slf4j
public class SendNotificationServiceImpl implements SendNotificationService {

    @Override
    public void sendNotification(SendNotificationRequest sendNotificationRequest) {
        log.info("Send notification request : {}", sendNotificationRequest);
    }
}

package vn.vti.dtn2504.notificationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendNotificationRequest {
    private String sendTo;
    private String message;
    private String title;
}

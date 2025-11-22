package vn.vti.dtn2504.shipmentservice.payload.request;

import lombok.*;
import vn.vti.dtn2504.shipmentservice.model.DeliveryStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateShipmentRequest {
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String carrier;
    private String trackingCode;
    private BigDecimal shippingFee;
    private DeliveryStatus status;
    private LocalDateTime estimatedDeliveryDate;
}

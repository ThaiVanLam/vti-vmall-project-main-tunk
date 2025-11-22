package vn.vti.dtn2504.shipmentservice.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateShipmentRequest {
    private String shipmentId;
    private String shipmentName;
}

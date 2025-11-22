package vn.vti.dtn2504.mallservice.dto.request;

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

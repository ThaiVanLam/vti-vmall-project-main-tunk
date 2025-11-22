package vn.vti.dtn2504.mallservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.vti.dtn2504.mallservice.dto.request.CreateShipmentRequest;

@FeignClient(
        name = "shipment-service"
)
public interface ShipmentClient {
    @PostMapping
    public String createShipment(@RequestBody CreateShipmentRequest createShipmentRequest);
}

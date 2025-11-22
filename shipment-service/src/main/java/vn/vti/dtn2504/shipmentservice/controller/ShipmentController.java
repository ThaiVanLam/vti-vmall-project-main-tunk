package vn.vti.dtn2504.shipmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.shipmentservice.model.Shipment;
import vn.vti.dtn2504.shipmentservice.payload.request.CreateShipmentRequest;

@RequestMapping(value = "/api/v1/shipments")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {
    @PostMapping
    public String createShipment(@RequestBody CreateShipmentRequest createShipmentRequest) {
        log.info("createShipment with " + createShipmentRequest.toString());
        return createShipmentRequest.toString();
    }
}

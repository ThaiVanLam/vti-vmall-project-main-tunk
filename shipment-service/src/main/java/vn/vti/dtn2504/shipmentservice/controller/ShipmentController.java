package vn.vti.dtn2504.shipmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.shipmentservice.model.Shipment;
import vn.vti.dtn2504.shipmentservice.payload.request.CreateShipmentRequest;
import vn.vti.dtn2504.shipmentservice.service.ShipmentService;

@RequestMapping(value = "/api/v1/shipments")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {
    private final ShipmentService  shipmentService;

    @PostMapping
    public ResponseEntity<Void> createShipment(@RequestBody CreateShipmentRequest createShipmentRequest) {
        shipmentService.createShipment(createShipmentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

package vn.vti.dtn2504.shipmentservice.service;

import vn.vti.dtn2504.shipmentservice.payload.request.CreateShipmentRequest;

public interface ShipmentService {
    void createShipment(CreateShipmentRequest createShipmentRequest);
}

package vn.vti.dtn2504.mallservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.vti.dtn2504.mallservice.client.ShipmentClient;
import vn.vti.dtn2504.mallservice.dto.request.CreateShipmentRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentCircuitBreakerService {

    private static final String SHIPMENT_SERVICE = "shipmentService";
    private final ShipmentClient shipmentClient;

    @CircuitBreaker(name = SHIPMENT_SERVICE, fallbackMethod = "createShipmentFallback")
    public void createShipment(CreateShipmentRequest createShipmentRequest) {
        shipmentClient.createShipment(createShipmentRequest);
    }

    private void createShipmentFallback(CreateShipmentRequest createShipmentRequest, Throwable throwable) {
        log.warn("Shipment service unavailable for orderId {}. Falling back and continuing order processing. Cause: {}",
                createShipmentRequest.getOrderId(), throwable.toString());
    }
}

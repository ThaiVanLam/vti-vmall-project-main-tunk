package vn.vti.dtn2504.shipmentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.vti.dtn2504.shipmentservice.model.DeliveryStatus;
import vn.vti.dtn2504.shipmentservice.model.Shipment;
import vn.vti.dtn2504.shipmentservice.payload.request.CreateShipmentRequest;
import vn.vti.dtn2504.shipmentservice.repository.ShipmentRepository;
import vn.vti.dtn2504.shipmentservice.service.ShipmentService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ModelMapper modelMapper;
    private final ShipmentRepository shipmentRepository;

    @Override
    public void createShipment(CreateShipmentRequest createShipmentRequest) {
        Shipment shipment = Shipment.builder()
                .orderId(createShipmentRequest.getOrderId())
                .receiverName(createShipmentRequest.getReceiverName())
                .receiverPhone(createShipmentRequest.getReceiverPhone())
                .shippingAddress(createShipmentRequest.getShippingAddress())
                .carrier(createShipmentRequest.getCarrier())
                .trackingCode(createShipmentRequest.getTrackingCode())
                .shippingFee(createShipmentRequest.getShippingFee())
                .status(DeliveryStatus.PENDING)
                .estimatedDeliveryDate(createShipmentRequest.getEstimatedDeliveryDate())
                .build();

        shipmentRepository.save(shipment);
    }
}

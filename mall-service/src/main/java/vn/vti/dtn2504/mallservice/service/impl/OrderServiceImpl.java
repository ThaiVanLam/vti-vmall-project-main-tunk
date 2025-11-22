package vn.vti.dtn2504.mallservice.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.vti.dtn2504.mallservice.client.ShipmentClient;
import vn.vti.dtn2504.mallservice.dto.request.CreateOrderRequest;
import vn.vti.dtn2504.mallservice.dto.request.CreateShipmentRequest;
import vn.vti.dtn2504.mallservice.dto.request.DeliveryStatus;
import vn.vti.dtn2504.mallservice.dto.request.SendNotificationRequest;
import vn.vti.dtn2504.mallservice.dto.response.OrderResponse;
import vn.vti.dtn2504.mallservice.model.Order;
import vn.vti.dtn2504.mallservice.model.OrderStatus;
import vn.vti.dtn2504.mallservice.model.Product;
import vn.vti.dtn2504.mallservice.repository.OrderRepository;
import vn.vti.dtn2504.mallservice.repository.ProductRepository;
import vn.vti.dtn2504.mallservice.security.AuthenticatedUser;
import vn.vti.dtn2504.mallservice.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ShipmentClient shipmentClient;

    @Value("${queue.notification.routing-key}")
    private String routingKey;

    @Value("${queue.notification.queue}")
    private String queueName;

    @Value("${queue.notification.exchange}")
    private String exchangeName;

    @Override
    @Transactional
    public OrderResponse placeOrder(CreateOrderRequest request, JwtAuthenticationToken authentication) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        AuthenticatedUser authenticatedUser = AuthenticatedUser.from(authentication);
        BigDecimal totalAmount = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(request.getQuantity())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .userId(authenticatedUser.userId())
                .username(authenticatedUser.username())
                .build();

        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest();
        sendNotificationRequest.setRecipient(authenticatedUser.email());
        sendNotificationRequest.setSubject("Order notification");

        Order savedOrder = orderRepository.save(order);

        sendNotificationRequest.setMsgBody("You have ordered product: " + product.getProductName() + " at date: " + savedOrder.getCreatedAt());

        rabbitTemplate.convertAndSend(exchangeName, routingKey, sendNotificationRequest);

        //gọi shipment
        CreateShipmentRequest createShipmentRequest = new CreateShipmentRequest();
        createShipmentRequest.setOrderId(savedOrder.getId());
        createShipmentRequest.setReceiverName("Nguyen Van A");
        createShipmentRequest.setReceiverPhone("0912345678");
        createShipmentRequest.setShippingAddress("123 Duong Pho Hue, Quan Hoan Kiem, Ha Noi");
        createShipmentRequest.setCarrier("GiaoHangNhanh");
        createShipmentRequest.setTrackingCode("GHN-2025-000123");
        createShipmentRequest.setShippingFee(BigDecimal.valueOf(35000));
        createShipmentRequest.setStatus(DeliveryStatus.PENDING);

        LocalDateTime estimatedDeliveryDate = LocalDateTime.parse("2025-12-25T10:00:00");
        createShipmentRequest.setEstimatedDeliveryDate(estimatedDeliveryDate);
        shipmentClient.createShipment(createShipmentRequest);

        return toResponse(savedOrder);
    }

    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .username(order.getUsername())
                .productId(order.getProductId())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}

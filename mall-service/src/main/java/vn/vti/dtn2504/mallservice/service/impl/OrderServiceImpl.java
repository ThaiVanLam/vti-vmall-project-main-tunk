package vn.vti.dtn2504.mallservice.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.vti.dtn2504.mallservice.client.ShipmentClient;
import vn.vti.dtn2504.mallservice.dto.request.*;
import vn.vti.dtn2504.mallservice.dto.response.OrderItemResponse;
import vn.vti.dtn2504.mallservice.dto.response.OrderResponse;
import vn.vti.dtn2504.mallservice.model.Order;
import vn.vti.dtn2504.mallservice.model.OrderItem;
import vn.vti.dtn2504.mallservice.model.OrderStatus;
import vn.vti.dtn2504.mallservice.model.Product;
import vn.vti.dtn2504.mallservice.repository.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    @Value("${queue.notification.routing-key}")
    private String routingKey;

    @Value("${queue.notification.queue}")
    private String queueName;

    @Value("${queue.notification.exchange}")
    private String exchangeName;

    @Override
    @Transactional
    public OrderResponse placeOrder(CreateOrderRequest request, JwtAuthenticationToken authentication) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must contain at least one item");
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.from(authentication);

        Order order = Order.builder()
                .totalAmount(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .userId(authenticatedUser.userId())
                .username(authenticatedUser.username())
                .build();

        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest();
        sendNotificationRequest.setRecipient(authenticatedUser.email());
        sendNotificationRequest.setSubject("Order notification");

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            BigDecimal itemTotal = BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(BigDecimal.valueOf(product.getPrice()))
                    .build();

            orderItems.add(orderItem);
        }

        savedOrder.getOrderItems().addAll(orderItems);
        orderItemRepository.saveAll(orderItems);

        savedOrder.setTotalAmount(totalAmount);
        savedOrder = orderRepository.save(savedOrder);

        sendNotificationRequest.setMsgBody(
                "You have placed an order with " + orderItems.size() + " item(s) at date: " + savedOrder.getCreatedAt());

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
                .items(order.getOrderItems().stream()
                        .map(this::toItemResponse)
                        .toList())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private OrderItemResponse toItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .build();
    }
}

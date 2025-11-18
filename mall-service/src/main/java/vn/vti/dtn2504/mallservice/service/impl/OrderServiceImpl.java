package vn.vti.dtn2504.mallservice.service.impl;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.vti.dtn2504.mallservice.dto.request.CreateOrderRequest;
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

        Order savedOrder = orderRepository.save(order);
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

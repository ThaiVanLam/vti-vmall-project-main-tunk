package vn.vti.dtn2504.mallservice.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.vti.dtn2504.mallservice.model.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String username;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}

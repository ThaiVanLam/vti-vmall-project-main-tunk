package vn.vti.dtn2504.shipmentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID đơn hàng chính từ Order-Service
    @NotNull(message = "Order ID cannot be null")
    @Positive(message = "Order ID must be positive")
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    // Người nhận
    @NotBlank(message = "Receiver name cannot be empty")
    @Size(max = 100, message = "Receiver name must be ≤ 100 characters")
    @Column(name = "receiver_name", nullable = false, length = 100)
    private String receiverName;

    // SĐT người nhận
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(0|\\+?84)(\\d{9})$",
            message = "Invalid Vietnamese phone number format"
    )
    @Column(name = "receiver_phone", nullable = false, length = 20)
    private String receiverPhone;

    // Địa chỉ giao hàng
    @NotBlank(message = "Address cannot be empty")
    @Size(max = 255, message = "Address must be ≤ 255 characters")
    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    // Hãng vận chuyển
    @NotBlank(message = "Carrier is required")
    @Size(max = 50)
    @Column(name = "carrier", length = 50)
    private String carrier;

    // Mã tracking của đơn vị vận chuyển
    @NotBlank(message = "Tracking code is required")
    @Size(max = 100)
    @Column(name = "tracking_code", length = 100, unique = true)
    private String trackingCode;

    // Phí ship
    @NotNull(message = "Shipping fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Shipping fee must be greater than 0")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    // Trạng thái giao vận
    @NotNull(message = "Delivery status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private DeliveryStatus status;

    // Ngày dự kiến giao
    @FutureOrPresent(message = "Estimated delivery date must be today or later")
    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    // Ngày giao thực tế
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    // Tự động tạo timestamp
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

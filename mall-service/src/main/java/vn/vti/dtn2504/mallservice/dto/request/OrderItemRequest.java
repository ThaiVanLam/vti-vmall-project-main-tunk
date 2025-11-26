package vn.vti.dtn2504.mallservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "Product id is required")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
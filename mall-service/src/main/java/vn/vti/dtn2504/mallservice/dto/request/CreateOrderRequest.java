package vn.vti.dtn2504.mallservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderRequest {
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemRequest> items;
}

package vn.vti.dtn2504.mallservice.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
}
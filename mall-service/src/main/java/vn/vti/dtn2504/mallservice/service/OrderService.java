package vn.vti.dtn2504.mallservice.service;

import java.util.List;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import vn.vti.dtn2504.mallservice.dto.request.CreateOrderRequest;
import vn.vti.dtn2504.mallservice.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(CreateOrderRequest request, JwtAuthenticationToken authentication);
}

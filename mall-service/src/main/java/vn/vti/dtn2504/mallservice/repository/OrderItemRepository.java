package vn.vti.dtn2504.mallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.vti.dtn2504.mallservice.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
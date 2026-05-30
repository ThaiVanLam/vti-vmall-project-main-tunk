package vn.vti.dtn2504.mallservice.repository;

import java.util.List;
import vn.vti.dtn2504.mallservice.model.Order;

public interface OrderByUserIdQuery {

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}

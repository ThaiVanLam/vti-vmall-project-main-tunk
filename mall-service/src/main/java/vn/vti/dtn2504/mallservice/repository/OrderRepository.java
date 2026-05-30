package vn.vti.dtn2504.mallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vti.dtn2504.mallservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
        OrderWriteRepository,
        OrderByUserIdQuery,
        OrderByUsernameQuery {
}

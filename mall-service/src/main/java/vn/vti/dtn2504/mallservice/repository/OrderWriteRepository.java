package vn.vti.dtn2504.mallservice.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import vn.vti.dtn2504.mallservice.model.Order;

@NoRepositoryBean
public interface OrderWriteRepository extends Repository<Order, Long> {

    <S extends Order> S save(S entity);
}

package vn.vti.dtn2504.mallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vti.dtn2504.mallservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

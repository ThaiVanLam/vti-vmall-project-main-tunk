package vn.vti.dtn2504.usermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.vti.dtn2504.usermanager.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByUsername(String username);
}

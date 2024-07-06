package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projectuas.ukm_management.data.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByName(String name);
}

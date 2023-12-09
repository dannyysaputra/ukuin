package projectuas.streamingPlatform.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectuas.streamingPlatform.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}

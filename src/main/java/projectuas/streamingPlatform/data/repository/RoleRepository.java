package projectuas.streamingPlatform.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectuas.streamingPlatform.data.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

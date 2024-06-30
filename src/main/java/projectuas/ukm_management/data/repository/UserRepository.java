package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projectuas.ukm_management.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}

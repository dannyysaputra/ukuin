package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projectuas.ukm_management.data.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

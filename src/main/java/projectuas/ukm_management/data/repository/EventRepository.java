package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projectuas.ukm_management.data.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
}

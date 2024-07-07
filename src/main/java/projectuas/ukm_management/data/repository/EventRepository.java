package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.entity.User;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
    List<Event> findByUser(User user);
}

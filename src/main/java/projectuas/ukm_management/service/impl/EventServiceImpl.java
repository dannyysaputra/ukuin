package projectuas.ukm_management.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.data.repository.EventRepository;
import projectuas.ukm_management.service.EventService;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void deleteEventById(Long id) {
        entityManager
                .createNativeQuery("DELETE FROM events WHERE id = :eventId")
                .setParameter("eventId", id)
                .executeUpdate();
    }

    @Override
    public Event update(Event event, Long id) {
        return eventRepository.findById(id).map(e -> {
            e.setName(event.getName());
            e.setEnd_date(event.getEnd_date());
            e.setName(event.getName());
            e.setPrice(event.getPrice());
            e.setStart_date(event.getStart_date());
            e.setUser(event.getUser());

            return eventRepository.save(e);
        }).orElse(null);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getEventByUkm(User user) {
        return eventRepository.findByUser(user);
    }

    @Override
    public Event getEventByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}

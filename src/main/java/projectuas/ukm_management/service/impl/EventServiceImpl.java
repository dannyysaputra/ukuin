package projectuas.ukm_management.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.repository.EventRepository;
import projectuas.ukm_management.service.EventService;

@Service
public class EventServiceImpl implements EventService {
    
    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
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
    public Event getEventByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();   
    }
}

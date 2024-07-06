package projectuas.ukm_management.service;

import java.util.List;

import projectuas.ukm_management.data.entity.Event;

public interface EventService {
    void saveEvent(Event event);

    Event update(Event event, Long id);

    Event getEventById(Long id);

    Event getEventByName(String name);

    List<Event> getEvents();
}

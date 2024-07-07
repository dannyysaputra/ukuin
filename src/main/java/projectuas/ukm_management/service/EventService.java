package projectuas.ukm_management.service;

import java.util.List;

import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.entity.User;

public interface EventService {
    void saveEvent(Event event);

    Event update(Event event, Long id);

    void deleteEventById(Long id);

    Event getEventById(Long id);

    List<Event> getEventByUkm(User user);

    Event getEventByName(String name);

    List<Event> getEvents();
}

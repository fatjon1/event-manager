package com.fatjon.eventmanager.model.event;

import com.fatjon.eventmanager.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EventService {

    void saveEvent(Event event);
    void deleteEventById(Long id);
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long id);

    void updateEvent(Long id, Event event);

}

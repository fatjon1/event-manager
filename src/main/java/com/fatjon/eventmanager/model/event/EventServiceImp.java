package com.fatjon.eventmanager.model.event;

import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.model.user.User;
import com.fatjon.eventmanager.model.user.UserRepo;
import com.fatjon.eventmanager.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService{

    private final EventRepo eventRepo;
    private final UserService userService;

    private final UserRepo userRepo;
    @Override
    public void saveEvent(Event event) {
        eventRepo.save(event);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepo.deleteById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll(Pageable.ofSize(10)).stream().toList();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepo.findById(id);
    }


    @Override
    public void updateEvent(Long id, Event event) {
        Optional<Event> ev = eventRepo.findById(id);

        if (ev.isPresent()){
            ev.get().setCategory(event.getCategory());
            ev.get().setStartDate(event.getStartDate());
            ev.get().setEndDate(event.getEndDate());
            ev.get().setLocation(event.getLocation());
            ev.get().setTitle(event.getTitle());
            ev.get().setEventStatus(event.getEventStatus());
            ev.get().setDescription(event.getDescription());
            ev.get().setPjesmares(event.getPjesmares());
            eventRepo.save(ev.get());
        }
    }
}

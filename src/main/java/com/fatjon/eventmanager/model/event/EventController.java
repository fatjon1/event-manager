package com.fatjon.eventmanager.model.event;

import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.model.user.User;
import com.fatjon.eventmanager.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(){
        return ResponseEntity.ok().body(eventService.getAllEvents());
    }
    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id){
        return ResponseEntity.ok().body(eventService.getEventById(id));
    }

    @PostMapping("/events")
    public ResponseEntity<?> saveEvent(@RequestBody Event event){
        eventService.saveEvent(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @PutMapping("/events/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event event){
        Optional<Event> eventTepm = eventService.getEventById(id);
        eventTepm.get().setDescription(event.getDescription());
        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }

    @PutMapping("/events/{id}/participate")
    public ResponseEntity<?> participateToEvent(@PathVariable Long id, @RequestParam String username) throws UserNotFoundException {
        Optional<Event> event = eventService.getEventById(id);
        Optional<User> user = userService.findUserByUsername(username);
        event.get().getPjesmares().add(user.get());
        eventService.saveEvent(event.get());
        return ResponseEntity.ok().body(event);
    }

}

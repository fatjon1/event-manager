package com.fatjon.eventmanager.model.event;

import com.fatjon.eventmanager.model.user.User;
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

    public final EventService eventService;

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
    public ResponseEntity<?> participateToEvent(@PathVariable Long id, @RequestBody User user){
        Event event = eventService.getEventById(id).get();
        event.getPjesmares().add(user);
        eventService.updateEvent(id, event);
        return ResponseEntity.ok().body(event);
    }

}

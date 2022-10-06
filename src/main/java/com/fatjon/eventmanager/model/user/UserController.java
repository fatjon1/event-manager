package com.fatjon.eventmanager.model.user;

import com.fatjon.eventmanager.exception.FieldCantBeNullException;
import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.exception.UsernameExistsException;
import com.fatjon.eventmanager.model.event.Event;
import com.fatjon.eventmanager.model.event.EventService;
import com.fatjon.eventmanager.utils.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = userService.getUserById(id);
            return ResponseEntity.ok().body(user);
        }


    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserNotFoundException, UsernameExistsException, FieldCantBeNullException {
        userService.saveUser(user);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        userService.updateUser(id, user);
        return ResponseEntity.ok("Updated");
    }

    // controller for PagingAndSortingRepository
    //Return a custom response
    @GetMapping("/userscustom")
    public ResponseEntity<HttpResponse>getUsers(@RequestParam Optional<String> username,
                                                @RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size){
        return ResponseEntity.ok().body(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(Map.of("page", userService.getUsersPage(username.orElse(""), page.orElse(0), size.orElse(10))))
                .message("Users Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping("/users/{id}/event/new")
    public ResponseEntity<?> createEvent(@PathVariable Long id, @RequestBody Event event) throws UserNotFoundException {
        Optional<User> user = userService.getUserById(id);
        //event.setCreatedBy(user.get());
        eventService.saveEvent(event);
        return new ResponseEntity<>("Event created!", HttpStatus.CREATED);
    }
}
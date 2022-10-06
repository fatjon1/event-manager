package com.fatjon.eventmanager.model.user;

import com.fatjon.eventmanager.exception.FieldCantBeNullException;
import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.exception.UsernameExistsException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepo userRepo;
    @Override
    public void saveUser(User user) throws UsernameExistsException, FieldCantBeNullException {
        if (userRepo.findUserByUsername(user.getUsername()).isPresent()){
            throw new UsernameExistsException("Username Exists!");
        } else if (user.getUsername().isEmpty() || user.getFirstName().isEmpty()) {
            throw new FieldCantBeNullException("Field cant be empty");
        }
        userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) throws UserNotFoundException {
      return Optional.ofNullable(userRepo.findById(id).orElseThrow(
              () -> new UserNotFoundException("User with id "+id+" not found!")));
    }

    @Override
    public Optional<User> getUserByFirstName(String firstName) {
        Optional<User> user = userRepo.findUserByFirstName(firstName);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public void updateUser(Long id, User user) throws UserNotFoundException {
        Optional<User> user1 = userRepo.findById(id);
        if (user1.isPresent()){
                    user1.get().setUsername(user.getUsername());
                    user1.get().setFirstName(user.getFirstName());
                    user1.get().setLastName(user.getLastName());
                    user1.get().setEmail(user.getEmail());
                    user1.get().setPassword(user.getPassword());
                    user1.get().setRoles(user.getRoles());
                    user1.get().setEvente(user.getEvente());
                    userRepo.save(user1.get());
                }
        else {
            throw new UserNotFoundException("User not found!");
        }

    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll(Pageable.ofSize(10)).stream().toList();
        //not implemented correctly
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepo.findUserByUsername(username);
        user.orElseThrow(()->new UserNotFoundException("User not found"));
        return user;
    }

    // methods for PagingAndSortingRepository

    @Override
    public Page<User> getUsersPage(String username, int page, int size) {
        log.info("Fetching users from page {} of size {}", page, size);
        return userRepo.findByUsernameContaining(username, PageRequest.of(page, size));
    }
}

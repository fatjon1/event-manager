package com.fatjon.eventmanager.model.user;

import com.fatjon.eventmanager.exception.FieldCantBeNullException;
import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.exception.UsernameExistsException;
import com.fatjon.eventmanager.model.role.Role;
import com.fatjon.eventmanager.model.role.RoleRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void saveUser(User user) throws UsernameExistsException, FieldCantBeNullException {
        if (userRepo.findUserByUsername(user.getUsername()).isPresent()){
            throw new UsernameExistsException("Username Exists!");
        } else if (user.getUsername().isEmpty() || user.getFirstName().isEmpty()) {
            throw new FieldCantBeNullException("Field cant be empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
      return userRepo.findById(id).orElseThrow(
              () -> new UserNotFoundException("User with id "+id+" not found!"));
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

    @Override
    public void addRoleToUser(String username, String roleName) {
        Optional<User> user = userRepo.findUserByUsername(username);
        Role role = roleRepo.findRoleByName(roleName);
        user.get().getRoles().add(role);
        userRepo.save(user.get());
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }


    // methods for PagingAndSortingRepository

    @Override
    public Page<User> getUsersPage(String username, int page, int size) {
        log.info("Fetching users from page {} of size {}", page, size);
        return userRepo.findByUsernameContaining(username, PageRequest.of(page, size));
    }
}

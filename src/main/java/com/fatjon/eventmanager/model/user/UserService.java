package com.fatjon.eventmanager.model.user;

import com.fatjon.eventmanager.exception.FieldCantBeNullException;
import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.exception.UsernameExistsException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
   void saveUser(User user) throws UsernameExistsException, FieldCantBeNullException;
  Optional<User> getUserByFirstName(String firstName);
   void deleteUser(User user);
   void updateUser(Long id, User user) throws UserNotFoundException;

   List<User> getUsers();

   Optional<User> getUserById(Long id) throws UserNotFoundException;

    Optional<User> findUserByUsername(String username) throws UserNotFoundException;

    void addRoleToUser(String username, String roleName);


   Page<User> getUsersPage(String username, int page, int size);


}

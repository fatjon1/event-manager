package com.fatjon.eventmanager.model.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {

   Page<User> findByUsernameContaining(String username, Pageable pageable);

   Optional<User> findUserByUsername(String username);
   Optional<User> findUserByFirstName(String firstName);

}

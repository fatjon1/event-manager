package com.fatjon.eventmanager.model.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> tempUser = userRepo.findUserByUsername(username);
        return tempUser.map(MyUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found " + username));
    }
}

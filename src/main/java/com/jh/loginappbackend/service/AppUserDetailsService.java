package com.jh.loginappbackend.service;

import com.jh.loginappbackend.dao.UserRepository;
import com.jh.loginappbackend.model.AppUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return User.builder().username(username)
        .password(appUser.getPassword())
        .authorities("USER")
        .build();
  }
}

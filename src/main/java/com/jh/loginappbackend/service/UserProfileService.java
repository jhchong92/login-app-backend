package com.jh.loginappbackend.service;

import com.jh.loginappbackend.dao.UserRepository;
import com.jh.loginappbackend.model.AppUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
  private final UserRepository userRepository;

  public Optional<AppUser> getCurrentUserProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName());
  }

}

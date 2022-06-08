package com.jh.loginappbackend.service;

import com.jh.loginappbackend.dao.UserRepository;
import com.jh.loginappbackend.dto.RegisterUserDto;
import com.jh.loginappbackend.exception.DuplicateAppUserException;
import com.jh.loginappbackend.mapper.UserMapper;
import com.jh.loginappbackend.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
  private final UserMapper userMapper;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public AppUser registerUser(RegisterUserDto registerUserDto) {
    userRepository.findByEmail(registerUserDto.getEmail())
        .ifPresent((user) -> { throw new DuplicateAppUserException(); });

    AppUser appUser = userMapper.fromDto(registerUserDto);
    appUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
    return userRepository.save(appUser);
  }
}

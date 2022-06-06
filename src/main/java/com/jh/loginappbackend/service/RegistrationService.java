package com.jh.loginappbackend.service;

import com.jh.loginappbackend.dao.UserRepository;
import com.jh.loginappbackend.dto.RegisterUserDto;
import com.jh.loginappbackend.mapper.UserMapper;
import com.jh.loginappbackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
  private final UserMapper userMapper;

  private final UserRepository userRepository;

  public User registerUser(RegisterUserDto registerUserDto) {
    User user = userMapper.fromDto(registerUserDto);

    return userRepository.save(user);
  }
}

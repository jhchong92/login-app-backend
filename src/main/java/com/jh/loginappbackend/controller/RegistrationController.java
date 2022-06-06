package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.RegisterUserDto;
import com.jh.loginappbackend.dto.UserDto;
import com.jh.loginappbackend.mapper.UserMapper;
import com.jh.loginappbackend.model.User;
import com.jh.loginappbackend.service.RegistrationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = RegistrationController.REQUEST_MAPPING_PATH)
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
  public static final String REQUEST_MAPPING_PATH = "/registration";

  private final UserMapper userMapper;

  private final RegistrationService registrationService;

  @PostMapping
  public UserDto register(@Valid @RequestBody RegisterUserDto registerUserDto) {
    log.info("RegisterUserDto {}", registerUserDto);
    User user = registrationService.registerUser(registerUserDto);
    return userMapper.toDto(user);
  }
}

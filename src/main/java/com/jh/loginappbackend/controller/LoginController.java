package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.LoginUserDto;
import com.jh.loginappbackend.exception.BadCredentialsException;
import com.jh.loginappbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = LoginController.REQUEST_MAPPING_PATH)
@Slf4j
@RequiredArgsConstructor
public class LoginController {
  public static final String REQUEST_MAPPING_PATH = "/login";

  private final LoginService loginService;

  @PostMapping
  public String login(@Validated @RequestBody LoginUserDto loginUserDto) {
    try {
      loginService.login(loginUserDto);
    } catch (AuthenticationException exception) {
      throw new BadCredentialsException();
    }
    return "Hello";
  }

}

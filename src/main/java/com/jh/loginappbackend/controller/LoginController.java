package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.LoginUserDto;
import com.jh.loginappbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = LoginController.REQUEST_MAPPING_PATH)
@Slf4j
@RequiredArgsConstructor
public class LoginController {
  public static final String REQUEST_MAPPING_PATH = "/login";

  private final LoginService loginService;

  @PostMapping
  public String login(@Validated @RequestBody LoginUserDto loginUserDto) {
    loginService.login(loginUserDto);
    return "Login";
  }

}

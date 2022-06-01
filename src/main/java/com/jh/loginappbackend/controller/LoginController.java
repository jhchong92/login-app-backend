package com.jh.loginappbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = LoginController.REQUEST_MAPPING_PATH)
public class LoginController {
  public static final String REQUEST_MAPPING_PATH = "/login";

  @PostMapping
  public String login() {
    return "Login";
  }

}

package com.jh.loginappbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = RegistrationController.REQUEST_MAPPING_PATH)
public class RegistrationController {
  public static final String REQUEST_MAPPING_PATH = "/registration";

  @PostMapping
  public String register() {
    return "Register";
  }
}

package com.jh.loginappbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = HelloController.REQUEST_MAPPING_PATH)
public class HelloController {
  public static final String REQUEST_MAPPING_PATH = "/hello";

  @GetMapping
  public String hello() {
    return "Hello World";
  }
}

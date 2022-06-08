package com.jh.loginappbackend.errors;

import java.util.Date;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ErrorResponse {
  private final Date timestamp = new Date();

  private int status;

  private String error;

  private String message;

  private String path;
}

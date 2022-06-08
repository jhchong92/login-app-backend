package com.jh.loginappbackend.errors;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BindingErrorDto {
  private String field;
  private String message;
  private String rejectedValue;
}

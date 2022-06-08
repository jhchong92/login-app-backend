package com.jh.loginappbackend.errors;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class BindingErrorResponse extends ErrorResponse {
  private List<BindingErrorDto> errors;
}

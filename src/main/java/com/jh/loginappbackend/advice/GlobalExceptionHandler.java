package com.jh.loginappbackend.advice;

import com.jh.loginappbackend.errors.BindingErrorDto;
import com.jh.loginappbackend.errors.BindingErrorResponse;
import com.jh.loginappbackend.errors.ErrorResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {

    List<BindingErrorDto> validationErrorDetails = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> BindingErrorDto.builder()
            .field(error.getField())
            .message(error.getDefaultMessage())
            .rejectedValue(Optional.ofNullable(error.getRejectedValue()).orElse("").toString())
            .build())
        .collect(Collectors.toList());
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ErrorResponse errorResponse = BindingErrorResponse.builder()
        .status(badRequest.value())
        .error(badRequest.getReasonPhrase())
        .message("Failed validation")
        .path(((ServletWebRequest) request).getRequest().getRequestURI())
        .errors(validationErrorDetails)
        .build();
    return new ResponseEntity<>(errorResponse, status);
  }
}

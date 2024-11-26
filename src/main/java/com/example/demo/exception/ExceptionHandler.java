package com.example.demo.exception;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.demo.model.ErrorResponse;

/**
 * Handles all outgoing exceptions
 */
@ControllerAdvice
public class ExceptionHandler {

  private final Logger logger;

  public ExceptionHandler(Logger logger) {
    this.logger = logger;
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    logger.error("Error: " + e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
  }
}

package com.example.demo.model;

public class ErrorResponse {
  private String error;

  public ErrorResponse() {
    // Default no-arg constructor
  }

  public ErrorResponse(String msg) {
    error = msg;
  }

  public String getError() {
    return error;
  }

  public void setError(String message) {
    this.error = message;
  }

}

package com.example.streamsbackend.domain.dto;

public class LoggedInUserDto {
  private String jwtToken;
  private String username;

  public LoggedInUserDto() {
  }

  public LoggedInUserDto(String token, String username) {
    this.jwtToken = token;
    this.username = username;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "LoggedInUserDto{" +
        "jwtToken='" + jwtToken + '\'' +
        ", username='" + username + '\'' +
        '}';
  }
}

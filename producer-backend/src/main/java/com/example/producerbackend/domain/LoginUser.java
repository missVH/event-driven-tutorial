package com.example.producerbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "LoginUser")
public class LoginUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;

  public LoginUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public LoginUser() {
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

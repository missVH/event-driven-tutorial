package com.example.producerbackend.services;

import com.example.producerbackend.domain.LoginUser;
import com.example.producerbackend.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
  private final LoginRepository loginRepository;

  public LoginService(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  public String login(String userName, String password) {
    var user = loginRepository.findByUserName(userName);
    if (user.isEmpty()) {
      throw new IllegalArgumentException("Username not found");
    } else if (!Objects.equals(user.get().getPassword(), password)) {
      throw new IllegalArgumentException("Wrong password");
    }
    return userName;
  }

  public void register(String userName, String password) {
    var user = loginRepository.findByUserName(userName);
    if (user.isPresent()) {
      throw new IllegalArgumentException("Username already in use");
    }
    var newUser = new LoginUser(userName, password);
    loginRepository.save(newUser);
  }
}

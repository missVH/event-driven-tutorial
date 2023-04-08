package com.example.producerbackend.repositories;

import com.example.producerbackend.domain.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginUser, Integer> {
  @Query("SELECT l FROM LoginUser l WHERE l.username = :username")
  Optional<LoginUser> findByUserName(String username);
}

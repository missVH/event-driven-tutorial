package com.example.producerbackend.repositories;

import com.example.producerbackend.domain.LocationChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationChangeRepository extends JpaRepository<LocationChange, Long> {
  @Query("SELECT c FROM LocationChange c WHERE c.username = :username AND c.markerId = :location")
  Optional<LocationChange> findLocationChangeByUsernameAndLocation(String username, int location);
}

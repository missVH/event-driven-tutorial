package com.example.producerbackend.repositories;

import com.example.producerbackend.domain.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Integer> {
}

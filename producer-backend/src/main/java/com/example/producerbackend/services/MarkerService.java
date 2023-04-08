package com.example.producerbackend.services;

import com.example.producerbackend.repositories.MarkerRepository;
import org.springframework.stereotype.Service;

import com.example.producerbackend.domain.Marker;

import java.util.List;

@Service
public class MarkerService {
  private final MarkerRepository markerRepository;

  public MarkerService(MarkerRepository markerRepository) {
    this.markerRepository = markerRepository;
  }

  public List<Marker> getAllMarkers() {
    return markerRepository.findAll();
  }

  public Marker addMarker(Marker marker) {
    if (markerRepository.findAll().size() <= marker.getMarkerId()) {
      return markerRepository.save(marker);
    } else {
      return null;
    }
  }

}

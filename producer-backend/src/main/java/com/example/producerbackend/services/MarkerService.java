package com.example.producerbackend.services;

import com.example.producerbackend.KafkaProducer;
import com.example.producerbackend.repositories.MarkerRepository;
import org.springframework.stereotype.Service;

import com.example.producerbackend.domain.Marker;

import java.util.List;

@Service
public class MarkerService {
  private final MarkerRepository markerRepository;

  private final KafkaProducer kafkaProducer;

  public MarkerService(MarkerRepository markerRepository, KafkaProducer kafkaProducer) {
    this.markerRepository = markerRepository;
    this.kafkaProducer = kafkaProducer;
  }

  public List<Marker> getAllMarkers() {
    return markerRepository.findAll();
  }

  public Marker addMarker(Marker marker) {
    if (markerRepository.findAll().size() <= marker.getMarkerId()) {
      kafkaProducer.sendMarkerMessage(marker);
      return markerRepository.save(marker);
    } else {
      return null;
    }
  }
}

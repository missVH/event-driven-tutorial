package com.example.producerbackend.services;

import com.example.producerbackend.KafkaProducer;
import com.example.producerbackend.domain.LocationChange;
import com.example.producerbackend.domain.Visit;
import com.example.producerbackend.repositories.LocationChangeRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
  private final LocationChangeRepository locationChangeRepository;

  private final KafkaProducer kafkaProducer;

  public LocationService(LocationChangeRepository locationChangeRepository, KafkaProducer kafkaProducer) {
    this.locationChangeRepository = locationChangeRepository;
    this.kafkaProducer = kafkaProducer;
  }

  //TODO implement kafka producer
  public void addLocationChange(LocationChange locationChange) {
    var location = locationChangeRepository.findLocationChangeByUsernameAndLocation(locationChange.getUsername(), locationChange.getMarkerId());
    if (locationChange.getArrival()) {
      if (location.isEmpty()) {
        locationChange = locationChangeRepository.save(locationChange);
      } else {
        location.get().setTimeChange(locationChange.getTimeChange());
        locationChange = locationChangeRepository.save(location.get());
      }
      kafkaProducer.sendLocationMessage(locationChange, "location-arrival");
    } else {
      location.get().setTimeChange(locationChange.getTimeChange());
      locationChangeRepository.save(location.get());
      locationChange.setId(location.get().getId());
      kafkaProducer.sendLocationMessage(locationChange, "location-departure");
      kafkaProducer.sendVisitMessage(new Visit(locationChange.getUsername(), locationChange.getMarkerId(), location.get().getTimeChange(), locationChange.getTimeChange()));
    }
  }
}

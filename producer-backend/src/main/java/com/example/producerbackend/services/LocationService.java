package com.example.producerbackend.services;

import com.example.producerbackend.domain.LocationChange;
import com.example.producerbackend.repositories.LocationChangeRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
  private final LocationChangeRepository locationChangeRepository;

  public LocationService(LocationChangeRepository locationChangeRepository) {
    this.locationChangeRepository = locationChangeRepository;
  }

  //TODO implement kafka producer
  public void addLocationChange(LocationChange locationChange) {
    var location = locationChangeRepository.findLocationChangeByUsernameAndLocation(locationChange.getUsername(), locationChange.getMarkerId());
    if (locationChange.getArrival()) {
      if (location.isEmpty()) {
        locationChangeRepository.save(locationChange);
      } else {
        location.get().setTimeChange(locationChange.getTimeChange());
        locationChangeRepository.save(location.get());
      }
    } else {
      location.get().setTimeChange(locationChange.getTimeChange());
      locationChangeRepository.save(location.get());
    }
  }
}

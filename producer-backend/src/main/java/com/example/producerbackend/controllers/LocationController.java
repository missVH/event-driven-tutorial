package com.example.producerbackend.controllers;

import com.example.producerbackend.domain.LocationChange;
import com.example.producerbackend.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/location")
public class LocationController {
  private final LocationService locationService;

  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PutMapping("/registerLocationChange")
  public ResponseEntity<ResponseStatus> addNewLocationChange(@RequestBody LocationChange locationChange) {
    locationService.addLocationChange(locationChange);
    return ResponseEntity.ok().build();
  }
}

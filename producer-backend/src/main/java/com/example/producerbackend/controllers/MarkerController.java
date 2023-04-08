package com.example.producerbackend.controllers;

import com.example.producerbackend.domain.Marker;
import com.example.producerbackend.services.MarkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/marker")
public class MarkerController {
  private final MarkerService markerService;

  public MarkerController(MarkerService markerService) {
    this.markerService = markerService;
  }

  @PostMapping("/new")
  public ResponseEntity<Marker> addNewMarker(@RequestBody Marker marker) {
    var newMarker = markerService.addMarker(marker);
    return new ResponseEntity<>(newMarker, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Marker>> getMarkers() {
    var markers = markerService.getAllMarkers();
    return new ResponseEntity<>(markers, HttpStatus.OK);
  }
}

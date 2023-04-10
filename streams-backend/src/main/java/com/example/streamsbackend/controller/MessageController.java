package com.example.streamsbackend.controller;

import com.example.streamsbackend.domain.Pair;
import com.example.streamsbackend.service.KafkaStreamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("api/message")
public class MessageController {
  private static final Logger log = LoggerFactory.getLogger(MessageController.class);

  private final KafkaStreamsService kafkaStreamsService;

  public MessageController(KafkaStreamsService kafkaStreamsService) {
    this.kafkaStreamsService = kafkaStreamsService;
  }

  @GetMapping(path = "/averageTime/{username}")
  public ResponseEntity<Pair<List<String>, List<Double>>> getAverageTimes(@PathVariable String username) {
    var averageTimeSpentAtLocation = kafkaStreamsService.getAverageTimeSpentAtLocation(username);
    List<String> locations = new ArrayList<>();
    List<Double> amountOfTime = new ArrayList<>();
    for (var location : averageTimeSpentAtLocation) {
      locations.add(location.getKey());
      amountOfTime.add(location.getValue());
    }
    return new ResponseEntity<>(new Pair<>(locations, amountOfTime), HttpStatus.OK);
  }

  @GetMapping(path = "/popularTimes")
  public ResponseEntity<Pair<List<String>, List<Integer>>> getPopularTimes() {
    var popularTimes = kafkaStreamsService.getPopularTimes();
    List<String> times = new ArrayList<>();
    List<Integer> visits = new ArrayList<>();
    for (var location : popularTimes) {
      times.add(location.getKey());
      visits.add(location.getValue().intValue());
    }
    return new ResponseEntity<>(new Pair<>(times, visits), HttpStatus.OK);
  }

  @GetMapping(path = "/topVisited")
  public ResponseEntity<Pair<List<String>, List<Long>>> getTopVisited() {
    var popularLocations = kafkaStreamsService.getMostVisitedLocations();
    List<String> names = new ArrayList<>();
    List<Long> visits = new ArrayList<>();
    for (var location : popularLocations) {
      names.add(location.getKey());
      visits.add(location.getValue());
    }
    return new ResponseEntity<>(new Pair<>(names, visits), HttpStatus.OK);
  }
}

package com.example.streamsbackend.service;

import com.example.streamsbackend.domain.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaStreamsService {
  private static final Logger log = LoggerFactory.getLogger(KafkaStreamsService.class);

  public List<Pair<String, Double>> getAverageTimeSpentAtLocation(String username) {
    List<Pair<String, Double>> averageTime = new ArrayList<>();
    averageTime.add(new Pair<>("home", 15.5));
    averageTime.add(new Pair<>("work", 8.5));
    averageTime.add(new Pair<>("shops", 1.0));
    return averageTime;
  }

  public List<Pair<String, Long>> getPopularTimes() {
    List<Pair<String, Long>> popularTimes = new ArrayList<>();
    popularTimes.add(new Pair<>("8", 15L));
    popularTimes.add(new Pair<>("14", 8L));
    popularTimes.add(new Pair<>("18", 1L));
    return popularTimes;
  }

  public List<Pair<String, Long>> getMostVisitedLocations() {
    List<Pair<String, Long>> mostVisited = new ArrayList<>();
    mostVisited.add(new Pair<>("groenplaats", 15L));
    mostVisited.add(new Pair<>("bernarduscentrum", 1L));
    mostVisited.add(new Pair<>("pothoek", 8L));
    return mostVisited;
  }

}

package com.example.streamsbackend.streams;

import com.example.streamsbackend.domain.LocationChange;
import com.example.streamsbackend.service.KafkaStreamsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MostVisitedLocation {
  private static final Logger log = LoggerFactory.getLogger(MostVisitedLocation.class);
  @Autowired
  void buildMostVisitedPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> arrivalDataStream = streamsBuilder
        .stream("location-arrival");

    KStream<String, String> locationDataStream = arrivalDataStream.mapValues(jsonString -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        var locationChange = mapper.readValue(jsonString, LocationChange.class);
        return String.valueOf(locationChange.getMarkerId());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    KStream<String,String> switchedStream = locationDataStream.map(((key, value) -> KeyValue.pair(value,key)));

    KStream<String, String> markersStream = streamsBuilder.stream("markers-objects");

    KStream<String, String> joinedStream = switchedStream.join(markersStream,
        (arrival, marker) -> marker,
        JoinWindows.of(Duration.ofHours(5)),
        StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));

    KTable<String, Long> locationVisitsTable = joinedStream
        .groupBy((key, value) -> value)
        .count();

    locationVisitsTable.toStream()
        .groupByKey()
        .reduce(Long::sum, Materialized.as("most-visited-locations"));
  }

}

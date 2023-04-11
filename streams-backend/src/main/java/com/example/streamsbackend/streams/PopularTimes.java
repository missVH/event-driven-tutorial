package com.example.streamsbackend.streams;

import com.example.streamsbackend.domain.Visit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PopularTimes {
  @Autowired
  void buildPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> visitDataStream = streamsBuilder
        .stream("new-visits");

    KStream<String, String> timeDataStream = visitDataStream.mapValues(jsonString -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        var visit = mapper.readValue(jsonString, Visit.class);
        var arrivalTime = visit.getTimeStart();
        return String.valueOf(LocalDateTime.parse(arrivalTime, DateTimeFormatter.ISO_DATE_TIME).getHour());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    KTable<String, Long> timeVisitTable = timeDataStream
        .groupBy((key, value) -> value)
        .count();

    KTable<String, Long> timeVisitStream = timeVisitTable.toStream()
        .groupByKey()
        .reduce(Long::sum, Materialized.as("popular-times"));
  }
}

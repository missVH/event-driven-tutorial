package com.example.streamsbackend.streams;

import com.example.streamsbackend.domain.Marker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkerStream {
  @Autowired
  public void buildMarkerPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> markersStream = streamsBuilder
        .stream("markers");

    KStream<String, String> transformedStream = markersStream.mapValues(jsonString -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        var visit = mapper.readValue(jsonString, Marker.class);
        return visit.getLatitude() + " : " + visit.getLongitude();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    transformedStream.to("markers-objects", Produced.with(Serdes.String(), Serdes.String()));
  }
}

package com.example.producerbackend;

import com.example.producerbackend.domain.LocationChange;
import com.example.producerbackend.domain.Marker;
import com.example.producerbackend.domain.Visit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class KafkaProducer {
  private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

  private final KafkaTemplate<String,String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMarkerMessage(Marker marker) {
    try {
      var message = objectMapper.writeValueAsString(marker);
      log.info("Sending marker message: {}", message);
      kafkaTemplate.send("markers",String.valueOf(marker.getMarkerId()), message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendLocationMessage(LocationChange locationChange, String topicName) {
    try {
      var message = objectMapper.writeValueAsString(locationChange);
      log.info("Sending location message: {}", message);
      kafkaTemplate.send(topicName,String.valueOf(locationChange.getId()), message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendVisitMessage(Visit visit) {
    String message = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss");
    String key =  visit.getTimeStart().formatted(formatter) + "-" + visit.getUsername();
    try {
      message = objectMapper.writeValueAsString(visit);
      log.info("Sending visit message: {}", message);
      kafkaTemplate.send("new-visits",key, message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}

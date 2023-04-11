package com.example.streamsbackend.domain.serdes;


import com.example.streamsbackend.domain.Visit;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class VisitSerde implements Serde<Visit> {
  @Override
  public Serializer<Visit> serializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      byte[] usernameBytes = data.getUsername().getBytes(StandardCharsets.UTF_8);
      byte[] timeStartBytes = data.getTimeStart().getBytes(StandardCharsets.UTF_8);
      byte[] timeEndBytes = data.getTimeEnd().getBytes(StandardCharsets.UTF_8);
      ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + 100 + usernameBytes.length + timeStartBytes.length + timeEndBytes.length);
      buffer.putInt(data.getLocation());
      buffer.putInt(usernameBytes.length);
      buffer.put(usernameBytes);
      buffer.putInt(timeStartBytes.length);
      buffer.put(timeStartBytes);
      buffer.putInt(timeEndBytes.length);
      buffer.put(timeEndBytes);
      return buffer.array();
    };
  }

  @Override
  public Deserializer<Visit> deserializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      ByteBuffer buffer = ByteBuffer.wrap(data);
      int location = buffer.getInt();
      int usernameLength = buffer.getInt();
      byte[] usernameBytes = new byte[usernameLength];
      buffer.get(usernameBytes);
      String username = new String(usernameBytes, StandardCharsets.UTF_8);
      int timeStartLength = buffer.getInt();
      byte[] timeStartBytes = new byte[timeStartLength];
      buffer.get(timeStartBytes);
      String timeStart = new String(timeStartBytes, StandardCharsets.UTF_8);
      int timeEndLength = buffer.getInt();
      byte[] timeEndBytes = new byte[timeEndLength];
      buffer.get(timeEndBytes);
      String timeEnd = new String(timeEndBytes, StandardCharsets.UTF_8);
      return new Visit(username, location, timeStart, timeEnd);
    };
  }
}

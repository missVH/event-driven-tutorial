package com.example.streamsbackend.domain.serdes;

import com.example.streamsbackend.domain.Pair;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class StringPairSerdes implements Serde<Pair<String, String>> {

  @Override
  public Serializer<Pair<String, String>> serializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      byte[] keyBytes = data.getKey().getBytes(StandardCharsets.UTF_8);
      byte[] valueBytes = data.getValue().getBytes(StandardCharsets.UTF_8);
      ByteBuffer buffer = ByteBuffer.allocate(4 + keyBytes.length + valueBytes.length);
      buffer.putInt(keyBytes.length);
      buffer.put(keyBytes);
      buffer.put(valueBytes);
      return buffer.array();
    };
  }

  @Override
  public Deserializer<Pair<String, String>> deserializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      ByteBuffer buffer = ByteBuffer.wrap(data);
      int keyLength = buffer.getInt();
      byte[] keyBytes = new byte[keyLength];
      buffer.get(keyBytes);
      String key = new String(keyBytes, StandardCharsets.UTF_8);
      byte[] valueBytes = new byte[buffer.remaining()];
      buffer.get(valueBytes);
      String value = new String(valueBytes, StandardCharsets.UTF_8);
      return new Pair<>(key, value);
    };
  }
}

package com.example.streamsbackend.domain.serdes;

import com.example.streamsbackend.domain.Pair;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;

public class IntegerPairSerde implements Serde<Pair<Integer, Integer>> {

  @Override
  public Serializer<Pair<Integer, Integer>> serializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      int key = data.getKey();
      int value = data.getValue();
      ByteBuffer buffer = ByteBuffer.allocate(16);
      buffer.putInt(key);
      buffer.putInt(value);
      return buffer.array();
    };
  }

  @Override
  public Deserializer<Pair<Integer, Integer>> deserializer() {
    return (topic, data) -> {
      if (data == null) {
        return null;
      }
      ByteBuffer buffer = ByteBuffer.wrap(data);
      int key = buffer.getInt();
      int value = buffer.getInt();
      return new Pair<>(key, value);
    };
  }
}

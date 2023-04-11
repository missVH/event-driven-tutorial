package com.example.streamsbackend.domain.serdes;

import com.example.streamsbackend.domain.Pair;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class PairSerde<T1, T2> implements Serde<Pair<T1, T2>> {
  private final Serde<T1> serde1;
  private final Serde<T2> serde2;

  public PairSerde(Serde<T1> serde1, Serde<T2> serde2) {
    this.serde1 = serde1;
    this.serde2 = serde2;
  }

  @Override
  public Serializer<Pair<T1, T2>> serializer() {
    return new Serializer<Pair<T1, T2>>() {
      @Override
      public void configure(Map<String, ?> configs, boolean isKey) {
        serde1.serializer().configure(configs, isKey);
        serde2.serializer().configure(configs, isKey);
      }

      @Override
      public byte[] serialize(String topic, Pair<T1, T2> data) {
        byte[] bytes1 = serde1.serializer().serialize(topic, data.getKey());
        byte[] bytes2 = serde2.serializer().serialize(topic, data.getValue());
        ByteBuffer buffer = ByteBuffer.allocate(bytes1.length + bytes2.length);
        buffer.put(bytes1);
        buffer.put(bytes2);
        return buffer.array();
      }

      @Override
      public void close() {
        serde1.serializer().close();
        serde2.serializer().close();
      }
    };
  }

  @Override
  public Deserializer<Pair<T1, T2>> deserializer() {
    return new Deserializer<Pair<T1, T2>>() {
      @Override
      public void configure(Map<String, ?> configs, boolean isKey) {
        serde1.deserializer().configure(configs, isKey);
        serde2.deserializer().configure(configs, isKey);
      }

      @Override
      public Pair<T1, T2> deserialize(String topic, byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        byte[] bytes1 = new byte[buffer.remaining() / 2];
        buffer.get(bytes1);
        byte[] bytes2 = new byte[buffer.remaining()];
        buffer.get(bytes2);
        T1 key = serde1.deserializer().deserialize(topic, bytes1);
        T2 value = serde2.deserializer().deserialize(topic, bytes2);
        return new Pair<>(key, value);
      }

      @Override
      public void close() {
        serde1.deserializer().close();
        serde2.deserializer().close();
      }
    };
  }
}

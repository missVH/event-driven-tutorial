package com.example.streamsbackend.streams;

import com.example.streamsbackend.domain.Pair;
import com.example.streamsbackend.domain.Visit;
import com.example.streamsbackend.domain.serdes.StringPairSerdes;
import com.example.streamsbackend.domain.serdes.VisitSerde;
import com.example.streamsbackend.domain.dto.VisitDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class AverageTimes {

  private static class CountAndSum {
    private int sum;
    private int count;

    public CountAndSum(int sum, int count) {
      this.sum = sum;
      this.count = count;
    }

    public int getSum() {
      return sum;
    }

    public int getCount() {
      return count;
    }

    public void setSum(int sum) {
      this.sum = sum;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }

  static class CountAndSumSerde implements Serde<CountAndSum> {

    @Override
    public Serializer<CountAndSum> serializer() {
      return (topic, data) -> {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 2);
        buffer.putInt(data.getSum());
        buffer.putInt(data.getCount());
        return buffer.array();
      };
    }

    @Override
    public Deserializer<CountAndSum> deserializer() {
      return (topic, data) -> {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int sum = buffer.getInt();
        int count = buffer.getInt();
        return new CountAndSum(sum, count);
      };
    }
  }
  @Autowired
  void buildPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> visitsDataStream = streamsBuilder
        .stream("new-visits");

    KStream<String, Visit> visitsStream = visitsDataStream.mapValues(jsonString -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Visit.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    KStream<String, Visit> switchedStream = visitsStream.map(((key, value) -> KeyValue.pair(String.valueOf(value.getLocation()), value)));


    KStream<String, String> markersStream = streamsBuilder.stream("markers-objects");

    KStream<String, VisitDto> joinedStream = switchedStream
        .join(markersStream,
            (visit, marker) -> new VisitDto(visit.getUsername(), marker, visit.getTimeStart(), visit.getTimeEnd()),
            JoinWindows.of(Duration.ofHours(50)),
            StreamJoined.with(Serdes.String(), new VisitSerde(), Serdes.String()));

    KStream<Pair<String, String>, Long> simplerStream = joinedStream
        .map((key, value) -> KeyValue.pair(new Pair<>(value.getLocation(), value.getUsername()), Duration.between(LocalDateTime.parse(value.getTimeStart(), DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse(value.getTimeEnd(), DateTimeFormatter.ISO_DATE_TIME)).toSeconds()));


    KGroupedStream<Pair<String, String>, Long> groupedStream = simplerStream
        .groupByKey(Grouped.with(new StringPairSerdes(), Serdes.Long()));

    KTable<Pair<String, String>, CountAndSum> countAndSumKTable =
        groupedStream.aggregate(() -> new CountAndSum(0, 0),
            (key, value, aggregate) -> {
              aggregate.setCount(aggregate.getCount() + 1);
              aggregate.setSum(aggregate.getSum() + Integer.parseInt(String.valueOf(value)));
              return aggregate;
            },Materialized
            .<Pair<String, String>, CountAndSum, KeyValueStore<Bytes, byte[]>>as("state-store-name")
            .withKeySerde(new StringPairSerdes())
            .withValueSerde(new CountAndSumSerde()));

    countAndSumKTable.mapValues(value -> (double) value.getSum() / (double) value.getCount(),
        Materialized.<Pair<String, String>, Double, KeyValueStore<Bytes, byte[]>>as("average-times")
            .withKeySerde(new StringPairSerdes())
            .withValueSerde(Serdes.Double()));
  }
}

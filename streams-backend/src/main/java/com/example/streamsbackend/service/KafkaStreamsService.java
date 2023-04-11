package com.example.streamsbackend.service;

import com.example.streamsbackend.domain.Pair;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KafkaStreamsService {
  private static final Logger log = LoggerFactory.getLogger(KafkaStreamsService.class);

  private final StreamsBuilderFactoryBean factoryBean;

  public KafkaStreamsService(StreamsBuilderFactoryBean factoryBean) {
    this.factoryBean = factoryBean;
  }

  public List<Pair<String, Double>> getAverageTimeSpentAtLocation(String username) {
    KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
    ReadOnlyKeyValueStore<Pair<String, String>, Double> counts = kafkaStreams.store(
        StoreQueryParameters.fromNameAndType("average-times", QueryableStoreTypes.keyValueStore())
    );

    List<Pair<String, Double>> averageTime = new ArrayList<>();
    KeyValueIterator<Pair<String, String>, Double> averageIter = counts.all();
    while (averageIter.hasNext()) {
      KeyValue<Pair<String, String>, Double> entry = averageIter.next();
      if (Objects.equals(entry.key.getValue(), username)) {
        System.out.println("hello" + entry.key.getKey() + " " + entry.value);
        averageTime.add(new Pair<>(entry.key.getKey(), entry.value));
      }
    }

    averageIter.close();

    return averageTime;
  }

  public List<Pair<String, Long>> getPopularTimes() {
    KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
    ReadOnlyKeyValueStore<String, Long> popularTimesStore = kafkaStreams.store(
        StoreQueryParameters.fromNameAndType("popular-times", QueryableStoreTypes.keyValueStore())
    );

    List<Pair<String, Long>> popularTimes = new ArrayList<>();
    KeyValueIterator<String, Long> iterTimes = popularTimesStore.all();

    while (iterTimes.hasNext()) {
      KeyValue<String, Long> entry = iterTimes.next();
      Pair<String, Long> pair = new Pair<>(entry.key + ":00", entry.value);
      popularTimes.add(pair);
    }

    Comparator<Pair<String, Long>> keyComparator = (p1, p2) -> {
      int time1 = Integer.parseInt(p1.getKey().substring(0, p1.getKey().indexOf(":")));
      int time2 = Integer.parseInt(p2.getKey().substring(0, p2.getKey().indexOf(":")));
      return Integer.compare(time1, time2);
    };

    Collections.sort(popularTimes, keyComparator);

    iterTimes.close();

    return popularTimes;
  }

  public List<Pair<String, Long>> getMostVisitedLocations() {
    KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
    ReadOnlyKeyValueStore<String, Long> topVisited = kafkaStreams.store(
        StoreQueryParameters.fromNameAndType("most-visited-locations", QueryableStoreTypes.keyValueStore())
    );

    PriorityQueue<Pair<String, Long>> topValues = new PriorityQueue<>(3, Comparator.comparingLong(Pair::getValue));

    KeyValueIterator<String, Long> topVisitedIter = topVisited.all();
    while (topVisitedIter.hasNext()) {
      KeyValue<String, Long> currentLocation = topVisitedIter.next();
      Pair<String, Long> currentPair = new Pair<>(currentLocation.key, currentLocation.value);
      topValues.offer(currentPair);
      if (topValues.size() > 3) {
        topValues.poll();
      }
    }
    topVisitedIter.close();

    List<Pair<String, Long>> mostVisited = new ArrayList<>(topValues);
    Collections.reverse(mostVisited);
    return mostVisited;
  }

}

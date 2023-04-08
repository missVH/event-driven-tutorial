package com.example.producerbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "LocationChange")
public class LocationChange {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private int markerId;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private String timeChange;
  private Boolean arrival;

  public LocationChange() {
  }

  public LocationChange(String username, int markerId, String timeChange, Boolean arrival) {
    this.username = username;
    this.markerId = markerId;
    this.timeChange = timeChange;
    this.arrival = arrival;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getMarkerId() {
    return markerId;
  }

  public void setMarkerId(int markerId) {
    this.markerId = markerId;
  }

  public String getTimeChange() {
    return timeChange;
  }

  public void setTimeChange(String time) {
    this.timeChange = time;
  }

  public Boolean getArrival() {
    return arrival;
  }

  public void setArrival(Boolean arrival) {
    this.arrival = arrival;
  }
}

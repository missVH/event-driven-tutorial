package com.example.streamsbackend.domain;

public class LocationChange {
  private Long id;
  private String username;
  private int markerId;

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

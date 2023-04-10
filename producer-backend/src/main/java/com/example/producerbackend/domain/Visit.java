package com.example.producerbackend.domain;

public class Visit {
  private String username;

  private int location;

  private String timeStart;

  private String timeEnd;

  public Visit() {
  }

  public Visit(String username, int location, String startVisit, String endVisit) {
    this.username = username;
    this.location = location;
    this.timeStart = startVisit;
    this.timeEnd = endVisit;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getLocation() {
    return location;
  }

  public void setLocation(int location) {
    this.location = location;
  }

  public String getTimeStart() {
    return timeStart;
  }

  public void setTimeStart(String timeStart) {
    this.timeStart = timeStart;
  }

  public String getTimeEnd() {
    return timeEnd;
  }

  public void setTimeEnd(String timeEnd) {
    this.timeEnd = timeEnd;
  }
}

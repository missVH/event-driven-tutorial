package com.example.streamsbackend.domain.dto;

public class VisitDto {
  private String username;
  private String location;
  private String timeStart;
  private String timeEnd;

  public VisitDto() {
  }

  public VisitDto(String username, String location, String timeStart, String timeEnd) {
    this.username = username;
    this.location = location;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
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

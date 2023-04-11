package com.example.streamsbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Marker")
public class Marker {

  @Id
  int markerId;
  String longitude;
  String latitude;

  public Marker(int markerId, String longitude, String latitude) {
    this.markerId = markerId;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Marker() {
  }

  public int getMarkerId() {
    return markerId;
  }

  public String getLongitude() {
    return longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setMarkerId(int markerId) {
    this.markerId = markerId;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  @Override
  public String toString() {
    return "Marker{" +
        "markerId='" + markerId + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        '}';
  }
}

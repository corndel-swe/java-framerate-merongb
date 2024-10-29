package com.corndel.framerate.models;

import java.time.Instant;

public class Review {
  private int id;
  private int movieId;
  private long createdAt;
  private String content;
  private int rating;

  public Review() {}

  public Review(int id, int movieId, String content, int rating, long createdAt) {
    this.id = id;
    this.movieId = movieId;
    this.content = content;
    this.rating = rating;
    this.createdAt = createdAt;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}

package com.corndel.framerate.repositories;

import com.corndel.framerate.DB;
import com.corndel.framerate.models.Review;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
  public static List<Review> findByMovie(int movieId) throws SQLException {
    var query = "SELECT * FROM REVIEWS WHERE movieId = ?";

    try (var con = DB.getConnection();
        var stmt = con.prepareStatement(query)) {
      stmt.setInt(1, movieId);
      try (var rs = stmt.executeQuery()) {
        var reviews = new ArrayList<Review>();
        while (rs.next()) {
          var review = new Review();
          review.setId(rs.getInt("id"));
          review.setMovieId(rs.getInt("movieId"));
          review.setCreatedAt(rs.getLong("createdAt"));
          review.setContent(rs.getString("content"));
          review.setRating(rs.getInt("rating"));
          reviews.add(review);
        }
        return reviews;
      }
    }
  }

  public static Review postReview(int movieId, String content, int rating) throws SQLException {
    var query = "INSERT INTO reviews (movieId, content, rating) VALUES (?, ?, ?) Returning id, createdAt";

    int id = 0;
    long createdAt = 0;

    try(var connection = DB.getConnection();
    var statement = connection.prepareStatement(query)) {
      statement.setInt(1, movieId);
      statement.setString(2, content);
      statement.setInt(3, rating);

      try (var rs = statement.executeQuery()) {
        if (rs.next()) {
          id = rs.getInt("id");
          createdAt = rs.getLong("createdAt");
        }
      }
    }catch (SQLException e) {
      throw new SQLException("Error inserting review: " + e.getMessage(), e);
    }
    return new Review(id, movieId, content, rating, createdAt);
  }
}

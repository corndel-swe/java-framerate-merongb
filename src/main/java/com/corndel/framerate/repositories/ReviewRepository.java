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
}

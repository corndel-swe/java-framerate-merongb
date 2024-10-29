package com.corndel.framerate;

import com.corndel.framerate.models.Movie;
import com.corndel.framerate.models.Review;
import com.corndel.framerate.repositories.MovieRepository;
import com.corndel.framerate.repositories.ReviewRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;
import java.util.Map;

import static com.corndel.framerate.repositories.ReviewRepository.postReview;

public class App {
  public static void main(String[] args) {
    var javalin = createApp();
    javalin.start(8081);
  }

  public static Javalin createApp() {
    var app = Javalin.create(
        config -> {
          config.staticFiles.add("/public", Location.CLASSPATH);

          var resolver = new ClassLoaderTemplateResolver();
          resolver.setPrefix("exercises/templates/");
          resolver.setSuffix(".html");
          resolver.setTemplateMode("HTML");

          var engine = new TemplateEngine();
          engine.setTemplateResolver(resolver);

          config.fileRenderer(new JavalinThymeleaf(engine));
        });

      app.get("/", ctx -> {
          List<Movie> movies = MovieRepository.findAll();
          ctx.render("movies.html", Map.of("movies", movies));
      });

    app.get("/movie/{id}", ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int movieId = id;
        List<Review> reviews = ReviewRepository.findByMovie(id);
        Movie movie = MovieRepository.findById(movieId);
        ctx.render("singleMovie.html", Map.of("movie", movie, "reviews", reviews));
    });

    app.get("/review/{movieId}", ctx -> {
        int movieId = Integer.parseInt(ctx.pathParam("movieId"));
        ctx.render("reviewForm.html", Map.of("movieId", movieId));
    });

      app.post("/submit", ctx -> {

              String movieIdParam = ctx.formParam("movieId");
              int movieId = Integer.parseInt(movieIdParam);

              String content = ctx.formParam("content");
              int rating = Integer.parseInt(ctx.formParam("rating"));

              Review review = postReview(movieId, content, rating);
            ctx.redirect(String.format("/movie/%s", movieId));
      });


    return app;
  }
}

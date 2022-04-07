package io.github.samuelsonev.watchnext;

public class MovieModel {

    // variables for our course name,
    // description and duration.
    private String movieOriginalTitle;
    private String movieReleaseDate;
    private String movieImageUrl;
    private String movieOverview;
    private String originalLanguage;
    private String[] genreArray;

    // constructor class.
    public MovieModel(String movieOriginalTitle, String originalLanguage, String movieReleaseDate,
                      String movieImageUrl, String movieOverview, String[] genreArray) {
        this.movieOriginalTitle = movieOriginalTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieImageUrl = movieImageUrl;
        this.movieOverview = movieOverview;
        this.originalLanguage = originalLanguage;
        this.genreArray = genreArray;
    }

    // getter and setter methods.
    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieOverview() { return movieOverview;}

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String[] getGenreArray(){return this.genreArray;}
}
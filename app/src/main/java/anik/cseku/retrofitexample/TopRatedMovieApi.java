package anik.cseku.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopRatedMovieApi {

    @GET("movie/top_rated")
    Call<TopRatedMovies> getMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("pages") int pageIndex
    );
}

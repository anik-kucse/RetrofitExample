package anik.cseku.retrofitexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TopRatedMovieApi topRatedMovieApi = retrofit.create(TopRatedMovieApi.class);

        final Call<TopRatedMovies> topRatedMovie = topRatedMovieApi.getMovie(
                "705668a7ad98acfa9ff4c202baa895fe",
                "en_US",
                1);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<TopRatedMovies> execute = topRatedMovie.execute();
                    TopRatedMovies topRatedMovies = execute.body();
                    List<Result> results = topRatedMovies.getResults();;

                    recyclerViewAdapter.addAll(results);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}

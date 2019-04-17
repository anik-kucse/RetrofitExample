package anik.cseku.retrofitexample;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
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
    LinearLayoutManager manager;
    ProgressBar progressBar;
    Retrofit retrofit;
    TopRatedMovieApi topRatedMovieApi;
    int currentPage = 1;
    int totalPage = 357;
    int currentItem, totalItem, scrolledOutItem;
    boolean isScrolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(manager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fetchData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = manager.getChildCount();
                totalItem = manager.getItemCount();
                scrolledOutItem = manager.findFirstVisibleItemPosition();

                if(isScrolled && (currentItem + scrolledOutItem == totalItem)){
                    isScrolled = false;
                    progressBar.setVisibility(View.VISIBLE);
                    fetchData();
                }

            }
        });

    }

    private void fetchData() {
        if(totalPage >= currentPage){
            topRatedMovieApi = retrofit.create(TopRatedMovieApi.class);


            final Call<TopRatedMovies> topRatedMovie = topRatedMovieApi.getMovie(
                    "705668a7ad98acfa9ff4c202baa895fe",
                    "en_US",
                    currentPage);
            currentPage = currentPage + 1;
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
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            //TODO: set warning for end of list
        }
    }
}

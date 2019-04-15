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

//    TextView textView;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = findViewById(R.id.error_msg);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceHolderAPI placeHolderAPI = retrofit.create(PlaceHolderAPI.class);
        final Call<List<Post>> posts = placeHolderAPI.getAllPosts();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<Post>> execute = posts.execute();
                    recyclerViewAdapter.addPost(execute.body());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        posts.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if(!response.isSuccessful()){
//                    textView.setText("Error code:" + response.code());
//                    return;
//                }
//
//                recyclerViewAdapter.addPost(response.body());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerViewAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });
    }
}

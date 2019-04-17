package anik.cseku.retrofitexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Result> movieResult;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.movieResult = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_content, viewGroup, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Result result = movieResult.get(i);


        myViewHolder.rating.setText("Ratings: " + result.getVoteAverage() );
        myViewHolder.releaseDate.setText("Release Date: " + result.getReleaseDate());
        myViewHolder.name.setText(result.getTitle());
        myViewHolder.overview.setText(result.getOverview());
    }

    @Override
    public int getItemCount() {
        return movieResult.size();
    }

    public void addAll(List<Result> moveResults) {
        movieResult.addAll(moveResults);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rating, releaseDate, name, overview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.movie_ratings);
            releaseDate = itemView.findViewById(R.id.movie_release_date);
            name = itemView.findViewById(R.id.movie_name);
            overview = itemView.findViewById(R.id.movie_overview);
        }
    }
}

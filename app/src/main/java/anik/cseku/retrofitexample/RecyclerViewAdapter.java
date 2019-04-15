package anik.cseku.retrofitexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Post> post;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.post = new ArrayList<>();
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
        myViewHolder.userId.setText("User ID: " + post.get(i).getUserId());
        myViewHolder.id.setText("Post ID: " + post.get(i).getId());
        myViewHolder.name.setText(post.get(i).getName());
        myViewHolder.bodyText.setText(post.get(i).getBodyText());
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public void addPost(List<Post> post){
        this.post.addAll(post);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userId, id, name, bodyText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id);
            id = itemView.findViewById(R.id.post_id);
            name = itemView.findViewById(R.id.name);
            bodyText = itemView.findViewById(R.id.body_text);
        }
    }
}

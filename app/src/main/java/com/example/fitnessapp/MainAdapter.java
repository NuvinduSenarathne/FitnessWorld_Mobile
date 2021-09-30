package com.example.fitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<NewsfeedModel, MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<NewsfeedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull NewsfeedModel model) {
        holder.username.setText(model.getUsername());
        holder.description.setText(model.getDescription());

        Glide.with(holder.userImage.getContext())
                .load(model.getUserImage())
                .placeholder(R.drawable.user)
                .circleCrop()
                .error(R.drawable.user)
                .into(holder.userImage);

        Glide.with(holder.postImage.getContext())
                .load(model.getPostImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.postImage);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView username, description;
        ImageView postImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = (CircleImageView)itemView.findViewById(R.id.userImage);
            username = (TextView)itemView.findViewById(R.id.username);
            description = (TextView)itemView.findViewById(R.id.description);
            postImage = (ImageView)itemView.findViewById(R.id.postImage);

        }
    }
}

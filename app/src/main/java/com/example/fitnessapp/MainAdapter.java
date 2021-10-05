package com.example.fitnessapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

    class MainAdapter extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView username, description, likeTxt;
        ImageView postImage;
        ImageView btnLike;
        Button btnComment;
        DatabaseReference likeReference;

        public MainAdapter(@NonNull View itemView) {
            super(itemView);

            userImage = (CircleImageView)itemView.findViewById(R.id.userImage);
            username = (TextView)itemView.findViewById(R.id.username);
            description = (TextView)itemView.findViewById(R.id.description);
            postImage = (ImageView)itemView.findViewById(R.id.postImage);
            btnLike = (ImageView)itemView.findViewById(R.id.btnLike);
            likeTxt = (TextView)itemView.findViewById(R.id.likeTxt);
            btnComment = (Button)itemView.findViewById(R.id.btnComment);

        }

        public void getLikeButtonStatus(String postkey, String userID) {

            likeReference = FirebaseDatabase.getInstance().getReference("likes");
            likeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(postkey).hasChild(userID)) {
                        int likeCount = (int)snapshot.child(postkey).getChildrenCount();
                        likeTxt.setText(likeCount + " likes");
                        btnLike.setImageResource(R.drawable.ic_favoritefill);
                    } else  {
                        int likeCount = (int)snapshot.child(postkey).getChildrenCount();
                        likeTxt.setText(likeCount + " likes");
                        btnLike.setImageResource(R.drawable.ic_favourite);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

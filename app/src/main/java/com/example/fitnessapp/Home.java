package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    NavigationBarView bottomNavigationView;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    CircleImageView userImage;
    TextView username, description, likeTxt;
    ImageView postImage;
    ImageView btnLike;
    Button btnComment;
    DatabaseReference likeReference;
    DatabaseReference ref;
    FirebaseRecyclerOptions<NewsfeedModel>Newsfeedoptions;
    FirebaseRecyclerAdapter<NewsfeedModel, MainAdapter> Newsfeedadapter;
    Boolean testClick = false;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String userID;
    RecyclerView recyclerView2;
    CommentAdapter commentadapter;
    private FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ref = FirebaseDatabase.getInstance().getReference().child("newsfeed");
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView_Newsfeed);
        userImage = (CircleImageView)findViewById(R.id.userImage);
        username = (TextView)findViewById(R.id.username);
        description = (TextView)findViewById(R.id.description);
        postImage = (ImageView)findViewById(R.id.postImage);
        btnLike = (ImageView)findViewById(R.id.btnLike);
        likeTxt = (TextView)findViewById(R.id.likeTxt);
        btnComment = (Button)findViewById(R.id.btnComment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("comment");

        LoadData();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_nav:
                        return true;
                    case R.id.workout_nav:
                        startActivity(new Intent(getApplicationContext(), Workout.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.food_nav:
                        startActivity(new Intent(getApplicationContext(), Food.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.me_nav:
                        startActivity(new Intent(getApplicationContext(), Me.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }

    private void LoadData() {
        Query query = FirebaseDatabase.getInstance().getReference().child("newsfeed");
        Newsfeedoptions = new FirebaseRecyclerOptions.Builder<NewsfeedModel>().setQuery(query,NewsfeedModel.class).build();

        Newsfeedadapter = new FirebaseRecyclerAdapter<NewsfeedModel, MainAdapter>(Newsfeedoptions) {

            @Override
            protected void onBindViewHolder(@NonNull MainAdapter holder, @SuppressLint({ "RecyclerView"}) int position, @NonNull NewsfeedModel model) {
                holder.username.setText(model.getUsername());
                holder.description.setText(model.getDescription());
                userID = model.getUserID();

                mAuth = FirebaseAuth.getInstance();
                storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference profileRef = storageReference.child("users/"+ userID +"/profile.jpg");

                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).placeholder(R.drawable.user).error(R.drawable.user).into(holder.userImage);
                    }
                });


                Glide.with(holder.postImage.getContext())
                        .load(model.getPostImage())
                        .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                        .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(holder.postImage);

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = firebaseUser.getUid();
                String postkey = getRef(position).getKey();
                holder.getLikeButtonStatus(postkey, userID);

                likeReference = FirebaseDatabase.getInstance().getReference("likes");

                holder.btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testClick = true;
                        likeReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testClick==true) {
                                    if(snapshot.child(postkey).hasChild(userID)) {
                                        likeReference.child(postkey).removeValue();
                                    } else  {
                                        likeReference.child(postkey).child(userID).setValue(true);
                                    }
                                    testClick = false;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                holder.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.postImage.getContext())
                                .setContentHolder(new ViewHolder(R.layout.comment_popup))
                                .setExpanded(true, 1300)
                                .create();

                        View view = dialogPlus.getHolderView();
                        dialogPlus.show();

                        recyclerView2 = (RecyclerView)findViewById(R.id.recyclerView_CMNT);
                        Button btnSend = view.findViewById(R.id.send_CMNT);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(Home.this));

                        FirebaseRecyclerOptions<CommentModel> options =
                                new FirebaseRecyclerOptions.Builder<CommentModel>()
                                        .setQuery(FirebaseDatabase.getInstance().getReference().child("comment").orderByChild("postID").startAt(postkey).endAt(postkey+"~"), CommentModel.class)
                                        .build();

                        commentadapter = new CommentAdapter(options);
                        commentadapter.startListening();
                        recyclerView2.setAdapter(commentadapter);

                        btnSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String key = databaseReference.push().getKey();

                                EditText commentTxt = (EditText)findViewById(R.id.addcomment_CMNT);
                                String comment = commentTxt.getText().toString();

                                HashMap hashMap= new HashMap();
                                hashMap.put("postID", postkey);
                                hashMap.put("txtComment", comment);
                                hashMap.put("userID", user.getUid());
                                databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Home.this, "Comment Successfully Added", Toast.LENGTH_SHORT).show();
                                        commentTxt.setText("");
                                    }

                                });
                            }
                        });
                    }
                });
            }


            @NonNull
            public MainAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
                return new MainAdapter(v);
            }

        };

        Newsfeedadapter.startListening();
        recyclerView.setAdapter(Newsfeedadapter);
    }

    //Nav Drawer

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome (View view){ recreate(); }

    public void ClickWorkout (View view){
        redirectActivity(this, Workout.class);
    }

    public void ClickFood (View view){
        redirectActivity(this, Food.class);
    }

    public void ClickMyProfile (View view){
        redirectActivity(this, Profile.class);
    }

    public void ClickMyPosts (View view){
        redirectActivity(this, MyPosts.class);
    }

    public void ClickBMI (View view){
        redirectActivity(this, BMI.class);
    }

    public void ClickHealthCal (View view){
        redirectActivity(this, HealthCalculator.class);
    }

    public void ClickAboutUs (View view){
        redirectActivity(this, AboutUs.class);
    }

    public void ClickLogOut(View view){
        NavDrawer.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawer.closeDrawer(drawerLayout);
    }

    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

}
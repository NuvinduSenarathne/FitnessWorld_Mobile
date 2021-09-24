package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private Button delUser,upUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailAddressTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView ageTextView = (TextView) findViewById(R.id.age);
        final TextView genderTextView = (TextView) findViewById(R.id.gender);
        final TextView heightTextView = (TextView)  findViewById(R.id.height);
        final TextView weightTextView = (TextView) findViewById(R.id.weight);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    String gender = userProfile.gender;
                    String height = userProfile.height;
                    String weight = userProfile.weight;

                    fullNameTextView.setText(fullName);
                    emailAddressTextView.setText(email);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);
                    heightTextView.setText(height);
                    weightTextView.setText(weight);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something Wrong Happend!" ,Toast.LENGTH_LONG).show();
            }
        });

        delUser = (Button) findViewById(R.id.delUser);
        upUser = (Button) findViewById(R.id.upUser);

        upUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,UpdateUser.class));

            }
        });

        delUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.delete();
                startActivity(new Intent(Profile.this,MainActivity.class));
            }
        });

    }

    public void ClickMenu(View view){
        NavDrawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        NavDrawer.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        NavDrawer.redirectActivity(this,NavDrawer.class);
    }

    public void ClickProfile (View view){
        NavDrawer.redirectActivity(this,Profile.class);
    }

    public void ClickAboutUs (View view){
        NavDrawer.redirectActivity(this,AboutUs.class);
    }

    public void ClickLogOut(View view){
        NavDrawer.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawer.closeDrawer(drawerLayout);
    }
}
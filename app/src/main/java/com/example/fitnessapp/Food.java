package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

public class Food extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.food_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.food_nav:
                        return true;
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.workout_nav:
                        startActivity(new Intent(getApplicationContext(), Workout.class));
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

    public void ClickHome (View view){ redirectActivity(this, Home.class); }

    public void ClickWorkout (View view){
        redirectActivity(this, Workout.class);
    }

    public void ClickFood (View view){
        recreate();
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
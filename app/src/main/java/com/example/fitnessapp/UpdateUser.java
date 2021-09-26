package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateUser extends AppCompatActivity implements View.OnClickListener{

    private Button upUser;
    private TextView banner;
    private EditText editTextFullName,editTextHeight,editTextWeight,editTextAge;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    String _FULLNAME,_AGE,_HEIGHT,_WEIGHT;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

//        upUser = (Button) findViewById(R.id.updateUser);
//        upUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextHeight = (EditText) findViewById(R.id.height);
        editTextWeight = (EditText) findViewById(R.id.weight);
        editTextAge = (EditText) findViewById(R.id.age);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    _FULLNAME = userProfile.fullName;
                    _AGE = userProfile.age;
                    _HEIGHT = userProfile.height;
                    _WEIGHT = userProfile.weight;

                    editTextFullName.setText(_FULLNAME);
                    editTextAge.setText(_AGE);
                    editTextHeight.setText(_HEIGHT);
                    editTextWeight.setText(_WEIGHT);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUser.this, "Something Wrong Happend!" ,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this,Profile.class));
                break;
//            case R.id.updateUser:
//                updateUser();
//                break;
        }
    }



    public void updateUser(View view) {
       if(isFullnameChanged()||isAgeChanged()||isWeightChanged()||isHeightChanged()){
           Toast.makeText(UpdateUser.this, "Data has been updated. Press Again if you update multiple fields" ,Toast.LENGTH_LONG).show();
       }else{
           Toast.makeText(UpdateUser.this, "Error with Updating Data" ,Toast.LENGTH_LONG).show();
       }

    }

    private boolean isFullnameChanged(){
        if(!_FULLNAME.equals(editTextFullName.getText().toString())){
            reference.child(userID).child("fullName").setValue(editTextFullName.getText().toString());
            _FULLNAME = editTextFullName.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isAgeChanged(){
        if(!_AGE.equals(editTextAge.getText().toString())){
            reference.child(userID).child("age").setValue(editTextAge.getText().toString());
            _AGE = editTextAge.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isHeightChanged(){
        if(!_HEIGHT.equals(editTextHeight.getText().toString())){
            reference.child(userID).child("height").setValue(editTextHeight.getText().toString());
            _HEIGHT = editTextHeight.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isWeightChanged(){
        if(!_WEIGHT.equals(editTextWeight.getText().toString())){
            reference.child(userID).child("weight").setValue(editTextWeight.getText().toString());
            _WEIGHT = editTextWeight.getText().toString();
            return true;
        }else{
            return false;
        }
    }

}
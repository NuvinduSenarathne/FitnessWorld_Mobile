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
    private RadioGroup genderRadio;
    private RadioButton genderMaleButton;
    private RadioButton genderFemaleButton;
    private RadioButton genderButton;

    String editTextGender;

    private FirebaseUser user;
    private DatabaseReference reference;
    private DocumentReference documentReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String userID;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        upUser = (Button) findViewById(R.id.updateUser);
        upUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextHeight = (EditText) findViewById(R.id.height);
        editTextWeight = (EditText) findViewById(R.id.weight);
        editTextAge = (EditText) findViewById(R.id.age);

        genderMaleButton = (RadioButton) findViewById(R.id.male);
        genderFemaleButton = (RadioButton) findViewById(R.id.female);


        genderRadio = (RadioGroup) findViewById(R.id.radioGroup);


        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                genderButton = findViewById(genderRadio.getCheckedRadioButtonId());
                editTextGender = genderButton.getText().toString().trim();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String age = userProfile.age;
                    String height = userProfile.height;
                    String weight = userProfile.weight;
                    String gender = userProfile.gender;

                    editTextFullName.setText(fullName);
                    editTextAge.setText(age);
                    editTextHeight.setText(height);
                    editTextWeight.setText(weight);

                    if(gender == "Female"){
                        genderFemaleButton.setChecked(true);
                    }else{
                        genderMaleButton.setChecked(true);
                    }


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
            case R.id.upUser:
                updateUser();
                break;
        }
    }


    private void updateUser() {
        String fullname = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String gender = editTextGender;

        final DocumentReference sDoc = db.collection("Users").document(userID);

    }
}
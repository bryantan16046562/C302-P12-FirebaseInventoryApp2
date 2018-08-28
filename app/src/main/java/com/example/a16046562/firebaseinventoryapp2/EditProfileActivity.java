package com.example.a16046562.firebaseinventoryapp2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private TextView tvEmail;
    private EditText etName, etContact, etHobbies;
    private Button btnUpdate;
    private UserProfile userprofile;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userProfileRef;
    private static final String TAG = "EditProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = (EditText) findViewById(R.id.editTextName);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        etContact = (EditText) findViewById(R.id.editTextContactNo);
        etHobbies = (EditText) findViewById(R.id.editTextHobbies);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        tvEmail.setText(firebaseUser.getEmail());


        firebaseDatabase = FirebaseDatabase.getInstance();
        userProfileRef = firebaseDatabase.getReference("profiles/" + firebaseUser.getUid());
        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "userProfileRef.addValueEventListener -- onDataChange()");
                UserProfile profile = dataSnapshot.getValue(UserProfile.class);
                if (profile != null) {
                    Log.i(TAG, "profile: " + profile.toString());
                    etName.setText(profile.getName());
                    etContact.setText(profile.getContactNo());
                    etHobbies.setText(profile.getHobbies());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error occurred", databaseError.toException());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String hobbies = etHobbies.getText().toString();
                String number = etContact.getText().toString();

                userprofile = new UserProfile(name,hobbies,number);

                //String id = firebaseUser.getUid();
                userProfileRef.setValue(userprofile);

                Toast.makeText(getApplicationContext(),"Profile updated successfully",Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}

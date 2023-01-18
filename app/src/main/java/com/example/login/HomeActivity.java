package com.example.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextInputEditText   userFirstNameEdt, userLastNameEdt,passwordEdt,confirmPwdEdt,email,phone,id;
    private TextView loginTV;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private DatabaseReference mDatabase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         //= FirebaseDatabase.getInstance().getReference();


        userFirstNameEdt = findViewById(R.id.FirstName);
        userLastNameEdt = findViewById(R.id.LastName);
        passwordEdt = findViewById(R.id.Password);
        confirmPwdEdt = findViewById(R.id.ConfirmPassword);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        id = findViewById(R.id.UserId);
        registerBtn = findViewById(R.id.button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFirstName = userFirstNameEdt.getText().toString();
                String userLastName = userLastNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String confirmPassword = confirmPwdEdt.getText().toString();
                String userEmail = email.getText().toString();
                String userPhone = phone.getText().toString();
                String userId = id.getText().toString();
                Boolean full = true;
                if( TextUtils.isEmpty(userFirstNameEdt.getText())){
                    userFirstNameEdt.setError( "יש להכניס שם פרטי" );
                    full = false;}
                if( TextUtils.isEmpty(userLastNameEdt.getText())){
                    userLastNameEdt.setError( "יש להכניס שם משפחה" );
                    full = false;}
                if( TextUtils.isEmpty(passwordEdt.getText())){
                    passwordEdt.setError( "יש להכניס סיסמה" );
                    full = false;}
                if( TextUtils.isEmpty(email.getText())){
                    email.setError( "יש להכניס כתובת אימייל" );
                    full = false;}
                if( TextUtils.isEmpty(confirmPwdEdt.getText())){
                    confirmPwdEdt.setError( "יש לאמת סיסמה" );
                    full = false;}
                    else{
                        if(!password.equals(confirmPassword)){
                            confirmPwdEdt.setError( "סיסמה לא זהה" );
                            full = false;}
                    }
                if( TextUtils.isEmpty(phone.getText())){
                    phone.setError( "יש להכניס טלפון" );
                    full = false;}
                if( TextUtils.isEmpty(id.getText())){
                    id.setError( "יש להכניס תעודת זהות" );
                    full = false;}
                //if(full ==true) {
                    //Intent i = new Intent(getApplicationContext(), MainActivity_backup.class);
                    //startActivity(i);
                    writeNewUser(userFirstName,userLastName,password,
                                        userEmail,userPhone,userId);
               // }
            }
        });
    }

    public void writeNewUser(String userFirstName,String userLastName,String password,
                             String userEmail,String userPhone,String userId) {

        Map<String, Object> user = new HashMap<>();
        user.put("first", userFirstName);
        user.put("last", userLastName);
        user.put("pw", password);
        user.put("phone", userPhone);
        user.put("mail", userEmail);

        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });
    }

    @IgnoreExtraProperties
    public static class User {

        public String userFirstName;
        public String userLastName;
        public String password;
        public String userEmail;
        public String userPhone;
        public String userId;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(com.example.login.HomeActivity.User.class)
        }

        public User(String userFirstName,String userLastName,String password,
                    String userEmail,String userPhone,String userId) {
            this.userFirstName = userFirstName;
            this.userLastName = userLastName;
            this.password = password;
            this.userEmail = userEmail;
            this.userPhone = userPhone;
            this.userId = userId;
        }

    }
}
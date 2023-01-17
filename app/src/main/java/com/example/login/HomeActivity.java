package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class HomeActivity extends AppCompatActivity {

    private TextInputEditText   userFirstNameEdt, userLastNameEdt,passwordEdt,confirmPwdEdt,email,phone,id;
    private TextView loginTV;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                if(full==true) {
                    //Intent i = new Intent(getApplicationContext(), MainActivity_backup.class);
                    //startActivity(i);
                    User user = new User(userFirstName,userLastName,password,
                                        userEmail,userPhone,userId);
                    mDatabase.child("users").child(userId).setValue(user);
                }
            }
        });
    }

    public void writeNewUser(String userFirstName,String userLastName,String password,
                             String userEmail,String userPhone,String userId) {
        User user = new User(userFirstName, userLastName,password,userEmail,userPhone,userId);

        mDatabase.child("users").child(userId).setValue(user);
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
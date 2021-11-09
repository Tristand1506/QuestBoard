package com.sleeplessstudios.mappapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class LandingPage extends AppCompatActivity {
   private ImageButton login;
   private ImageButton signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        //button listener
        login = findViewById(R.id.lp_login_btn);
        login.setOnClickListener(v -> openLoginScreen());

        signUp = findViewById(R.id.lp_signup_btn);
        signUp.setOnClickListener(v -> openSignUpScreen());
    }

    //method to go to login screen
    public void openLoginScreen()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openSignUpScreen()
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}

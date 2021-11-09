package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
   private ImageButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //button listener
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(v -> openMainScreen());
    }

    public void openMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
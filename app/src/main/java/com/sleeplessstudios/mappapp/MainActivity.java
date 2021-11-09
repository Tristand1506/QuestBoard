package com.sleeplessstudios.mappapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
private ImageButton burgerBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button listener
        burgerBar = findViewById(R.id.main_burger_btn);
        burgerBar.setOnClickListener(v -> openNavBar());
    }

    //method to open activity using intent
    public void openNavBar()
    {

    }
}
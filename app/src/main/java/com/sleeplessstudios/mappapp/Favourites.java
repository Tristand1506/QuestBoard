package com.sleeplessstudios.mappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class Favourites extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageButton burgerBar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        drawer = findViewById(R.id.sidebar_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        burgerBar = (ImageButton) findViewById(R.id.favourites_burger_btn);
        burgerBar.setOnClickListener(v -> openNavBar());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sidebar_home:
                openMain();
                break;
            case R.id.sidebar_fav:
                openFavs();
                break;
            case R.id.sidebar_reviews:
                openReviews();
                break;
            case R.id.sidebar_settings:
                openSettings();
                break;
            case R.id.sidebar_logout:
                Logout();
                break;
        }
        return true;
    }

    public void openNavBar()
    {
        drawer.openDrawer(Gravity.LEFT);
    }

    public void openMain()
    {
        drawer.closeDrawer(Gravity.LEFT);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openFavs()
    {
        drawer.closeDrawer(Gravity.LEFT);
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    public void openReviews()
    {
        drawer.closeDrawer(Gravity.LEFT);
        //Intent intent = new Intent(this, Reviews.class);
        //startActivity(intent);
    }

    public void openSettings()
    {
        drawer.closeDrawer(Gravity.LEFT);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void Logout()
    {
        Intent intent = new Intent(this, LandingPage.class);
        //LoginManager.getInstance().LogOut();
        startActivity(intent);

    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(Gravity.LEFT))
        {
            drawer.closeDrawer(Gravity.LEFT);
        }
        else {
            super.onBackPressed();
        }
    }
}
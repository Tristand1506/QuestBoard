package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    private ImageButton burgerBar;
    private Spinner spinner;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        drawer = findViewById(R.id.sidebar_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        //button listener
        burgerBar = findViewById(R.id.settings_burger_btn);
        burgerBar.setOnClickListener(v -> openNavBar());

        //spinner for FILTERING LANDMARKS
        spinner = findViewById(R.id.settings_spinner);
        List<String> landmarkTypes = new ArrayList<>();
        landmarkTypes.add(0, "Select a Landmark type");
        landmarkTypes.add("Restaurants");
        landmarkTypes.add("Hotels");
        landmarkTypes.add("Historical Places");
        landmarkTypes.add("Parks");
        landmarkTypes.add("Hospitals");
        landmarkTypes.add("Gas Stations");
        landmarkTypes.add("Entertainment");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.colour_spinner_layout, landmarkTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        //drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, MainActivity.class);
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

    //USE THIS FOR WHATEVER THE SPINNER IS SUPPOSED TO DO -FILTER LANDMARKS
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //https://youtu.be/on_OrrX7Nw4?t=262
        if (parent.getItemAtPosition(position).equals("Select a Landmark type"))
        {
            //do nothing
        }
        else
        {
            //do the thing
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
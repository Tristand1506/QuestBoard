package com.sleeplessstudios.mappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private ImageButton burgerBar;
    private DrawerLayout drawer;
    private BottomSheetBehavior bottomSheetBehavior;

    boolean isPermissionGranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        checkMyPermission();

        if (isPermissionGranted) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        drawer = findViewById(R.id.sidebar_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        //button listener
        burgerBar = findViewById(R.id.map_burger_btn);
        burgerBar.setOnClickListener(v -> openNavBar());

        //PLACES PULL UP TAB
        LinearLayout linearLayout = findViewById(R.id.design_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        //EXPANDED = SHOW, HIDDEN = HIDE
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        LatLng capeTown = new LatLng(-33.9249, 18.4241);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capeTown, 15f));

    }


    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                isPermissionGranted = false;
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("Package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
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
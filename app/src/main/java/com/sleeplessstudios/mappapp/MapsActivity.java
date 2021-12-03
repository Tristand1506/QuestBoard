package com.sleeplessstudios.mappapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import ObjectLib.UserAccount;
import UtilLib.AccountManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback, NavigationView.OnNavigationItemSelectedListener  {

    private GoogleMap mMap;
    private FusedLocationProviderClient userLocation;
    private LatLng originLocation;
    private Polyline currentPolyline;

    private ImageButton burgerBar;
    private DrawerLayout drawer;
    private BottomSheetBehavior bottomSheetBehavior;

    private CheckBox fav_check;

    private TextView placeName;
    private TextView address;
    private TextView placeType;
    private TextView routingInfo;
    private TextView websiteURL;

    private LatLng userPos;
    boolean isPermissionGranted;

    private Settings settingsClass = new Settings();

    private TextView sidebarUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the MapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkMyPermission();

        if (isPermissionGranted) {

            userLocation = LocationServices.getFusedLocationProviderClient(this);

            getCurrentLocation();
        }

        drawer = findViewById(R.id.sidebar_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        //button listener
        burgerBar = findViewById(R.id.map_burger_btn);
        burgerBar.setOnClickListener(v -> openNavBar());
        fav_check  = findViewById(R.id.bottom_fav_btn);
        //change opennavbar to whatever method it needs to be
        //fav_check.setOnClickListener(v -> openNavBar());

        //landmark info textviews
        fav_check = findViewById(R.id.bottom_fav_btn);
        fav_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    UserAccount accIn = AccountManager.getActiveAccountData();
                    accIn.getFavorites().add(placeName.getText().toString());
                    AccountManager.UpdateAccountData(accIn);
                }
                else if (!isChecked){
                    UserAccount accIn = AccountManager.getActiveAccountData();
                    accIn.getFavorites().remove(placeName.getText().toString());
                    AccountManager.UpdateAccountData(accIn);
                }
            }
        });
        placeName = findViewById(R.id.bottom_placename_txt);
        address = findViewById(R.id.bottom_address_txt);
        placeType = findViewById(R.id.bottom_type_txt);
        routingInfo = findViewById(R.id.bottom_routing_txt);
        websiteURL = findViewById(R.id.bottom_website_txt);

        //PLACES PULL UP TAB
        LinearLayout linearLayout = findViewById(R.id.design_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        //EXPANDED = SHOW, HIDDEN = HIDE
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        Places.initialize(getApplicationContext(),  getString(R.string.google_maps_key));

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                String poiId = poi.placeId;
                String poiName = poi.name;

                LatLng poiLatLng = poi.latLng;

                LatLng originLatLng = originLocation;

                placeName.setText(poiName);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                new FetchURL(MapsActivity.this).execute(getUrl(originLatLng, poiLatLng, "driving"), "driving");

                SearchPoi(poi);

                switch (AccountManager.getActiveAccountData().getPrefUnits()) {
                    case METRIC:
                        float routeMetDistance = distBetween(originLatLng.latitude, originLatLng.longitude, poiLatLng.latitude, poiLatLng.longitude);
                        int routeMetDuration = (Math.round((routeMetDistance/80) * 60));

                        routingInfo.setText("Distance: " + routeMetDistance + " km,      Duration: " + routeMetDuration + " min");

                        break;
                    case IMPERIAL:
                        float routeImpDistance = distBetween(originLatLng.latitude, originLatLng.longitude, poiLatLng.latitude, poiLatLng.longitude);

                        routeImpDistance *= 0.6f;

                        routeImpDistance = (float) (Math.round(routeImpDistance * 100.0) / 100.0);

                        int routeImpDuration = (Math.round((routeImpDistance/50) * 60));

                        routingInfo.setText("Distance: " + routeImpDistance + " miles,      Duration: " + routeImpDuration + " min");

                        break;
                    default:
                        break;
                }

                fav_check.setChecked(AccountManager.getActiveAccountData().isFavorite(placeName.getText().toString()));

            }
        });
    }

    private void SearchPoi(PointOfInterest poi) {

        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.TYPES, Place.Field.WEBSITE_URI);

        final FetchPlaceRequest req = FetchPlaceRequest.newInstance(poi.placeId, placeFields);
        PlacesClient placesClient = Places.createClient(this);
        placesClient.fetchPlace(req).addOnSuccessListener(fetchPlaceResponse -> {

            Place place = fetchPlaceResponse.getPlace();
            Log.d("Result", place.getName());
            if (place.getName() != null)
            {
                placeName.setText(place.getName());


            } else placeName.setText("Unnamed");

            if (place.getAddress() != null)
            {
                address.setText(place.getAddress());
            }
            else address.setText("");

            if (place.getTypes().size() >= 1)
            {
                String tag = place.getTypes().get(0).name().toLowerCase();
                tag = tag.replaceAll("_", " ");
                placeType.setText(tag);
            }
            else placeType.setText("");

            if (place.getWebsiteUri() != null)
            {
                websiteURL.setVisibility(View.VISIBLE);
                websiteURL.setText(place.getWebsiteUri().toString());
            }
            else websiteURL.setVisibility(View.GONE);
        });
    }

    public static float distBetween(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        float roundedDist = (float) (Math.round(dist * 100.0) / 100.0);

        return roundedDist;
    }

    private String getUrl(LatLng origin, LatLng destination, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);

        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);


    }

    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = userLocation.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    userPos = new LatLng(location.getLatitude(), location.getLongitude());


                    MarkerOptions options = new MarkerOptions().position(userPos).title("You are here.");

                    originLocation = options.getPosition();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15f));
                    mMap.addMarker(options);
                }
            }
        });
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
        sidebarUsername = findViewById(R.id.sidebar_username_txt);
        sidebarUsername.setText(AccountManager.getActiveAccountData().getUsername());
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

    public void openSettings()
    {
        drawer.closeDrawer(Gravity.LEFT);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void Logout()
    {
        Intent intent = new Intent(this, LandingPage.class);
        AccountManager.I().LogOut();
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
package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.midtermproject.Models.Shop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.example.midtermproject.databinding.ActivityMapStoresBinding;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class mapStoresActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapStoresBinding binding;
    Bitmap markerImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapStoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        ArrayList<Shop> shopList = ShopArrayList.shopList;
        LatLng lastLatLng = null;
        for (int i = 0; i < shopList.size(); i++){
            Picasso.get().load(shopList.get(i).getImg()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    markerImg = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            LatLng latLng =  requestCoordinate(shopList.get(i).getLocation());
            if (i == shopList.size() - 1){
                lastLatLng = latLng;
            }
            mMap.addMarker(new MarkerOptions().position(latLng).title(shopList.get(i).getName()).icon(BitmapDescriptorFactory.fromBitmap(markerImg)));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markerName = marker.getTitle();
                Toast.makeText(mapStoresActivity.this, markerName, Toast.LENGTH_SHORT).show();
                String template = "geo:0,0?q=%s,%s(%s)";
                String uri = String.format(template, marker.getPosition().latitude, marker.getPosition().longitude,Uri.encode(markerName));
                Log.d("json", uri);
                Uri gmmIntentUri=Uri.parse(uri);
                Intent intent= new Intent(Intent.ACTION_VIEW,gmmIntentUri );
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                return false;
            }
        });
    }

    public LatLng requestCoordinate(String address){
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(mapStoresActivity.this);
        try {
            List<Address> addresses =  geocoder.getFromLocationName(address, 1);
            if(addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                latLng = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(mapStoresActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
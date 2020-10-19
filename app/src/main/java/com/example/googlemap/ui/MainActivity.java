package com.example.googlemap.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlemap.App;
import com.example.googlemap.R;
import com.example.googlemap.data.local.Database;
import com.example.googlemap.data.models.Figure;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.LOCATION;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private List<LatLng> places = new ArrayList<>();
    private List<Figure> figure;
    private static final int LOCATION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocation();

        changeMap();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }
    private void checkLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION);
    }
    private void changeMap(){
        Button changeMapTypeBtn = findViewById(R.id.change_btn);
        changeMapTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            }
        });
        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** ломание линиибфигур*/
                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.strokeWidth(15f);
                polygonOptions.strokeColor(Color.DKGRAY);
                figure = new ArrayList<>();
                for (LatLng latLng:places){
                    polygonOptions.add(latLng);
                    Figure figure1 = new Figure(latLng.latitude,latLng.longitude);
                    figure.add(figure1);
                }
                App.database.daoDs().putFigure(figure);
                mMap.addPolygon(polygonOptions);
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        CameraPosition cameraPosition = CameraPosition
                .builder()
                .target(new LatLng(42.8779812,74.585436 ))
                .zoom(13f)
                .build();
        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition));

            figure = App.database.daoDs().getFigure();

        if (figure.size()>0){
            for (int i = 0; i< figure.size(); i ++){
                LatLng lng = new LatLng(figure.get(i).getLatitude(),figure.get(i).getLatLng());
                places.add(lng);
            }
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeWidth(15f);
            polygonOptions.strokeColor(Color.DKGRAY);
            for (LatLng latLng:places){
                polygonOptions.add(latLng);
            }
            mMap.addPolygon(polygonOptions);
        }

    }



    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Title");
        markerOptions.position(latLng);
        markerOptions.snippet("Shipped");
        markerOptions.anchor(0.4f,0.4f);
        markerOptions.draggable(true); //перетаскивать
        markerOptions.alpha(0.6f); //празрачность
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(markerOptions);
        places.add(latLng);

    }
    public void location(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }else {
            Toast.makeText(this,"not",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        mMap.setMyLocationEnabled(true);

                    }else {
                        Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show();

                    }
                }
        }



    }
}
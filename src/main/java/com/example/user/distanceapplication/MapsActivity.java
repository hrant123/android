package com.example.user.distanceapplication;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> pntList;
    private LatLng lat_lng_1, lat_lng_2;
    Button button;
    Polyline line;
    float[] distance = new float[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pntList = new ArrayList<>();
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (pntList.size() == 2){
                    removeObjects();
                }
                pntList.add(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                MarkerOptions distanceOptions = new MarkerOptions();

                markerOptions.position(latLng);
                distanceOptions.title("Distance");
                if (pntList.size() == 1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    lat_lng_1 = latLng;
                }else if (pntList.size() == 2){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    lat_lng_2 = latLng;
                    Location.distanceBetween(lat_lng_1.latitude,lat_lng_1.longitude,lat_lng_2.latitude,lat_lng_2.longitude,distance);
                    distanceOptions.position(latLng);
                    distanceOptions.snippet(""+distance[0]);
                    mMap.addMarker(distanceOptions);
                    drawLine();
                }
                mMap.addMarker(markerOptions);
            }
        });
    }

    private void drawLine() {
        PolylineOptions options = new PolylineOptions();
        options.add(lat_lng_1);
        options.add(lat_lng_2);
        options.color(Color.RED);

        line = mMap.addPolyline(options);

    }
    private void removeObjects(){
            pntList.clear();
            mMap.clear();
            line.remove();
    }

}

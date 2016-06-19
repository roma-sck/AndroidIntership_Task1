package com.example.sck.androidintership_task1.activity;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.models.GeoAddress;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int ZOOM_VALUE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
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

        Realm realm = Realm.getDefaultInstance();
        RealmResults<IssueDataModel> results = realm.where(IssueDataModel.class)
                .findAll();
        realm.close();

        for(int i = 0; i < results.size(); i++) {
            IssueDataModel model = results.get(i);
            if(model.getGeoAddress() != null && model.getGeoAddress().getLatitude().length() > 0) {
                double latitude = Double.parseDouble(model.getGeoAddress().getLatitude());
                double longitude = Double.parseDouble(model.getGeoAddress().getLongitude());
                String title = results.get(i).getTitle() + "";
                String address = results.get(i).getGeoAddress().getAddress();

                LatLng location = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_VALUE));
                mMap.addMarker(new MarkerOptions()
                        .title(title)
                        .snippet(address)
                        .position(location));
            }
        }
    }
}

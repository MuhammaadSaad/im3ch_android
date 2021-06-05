package com.adnan.tech.im3ch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.adnan.tech.im3ch.Model.ModelAddress;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.ConstVar;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.GSON_Module;
import com.adnan.tech.im3ch.Util.MyPrefs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements
        GoogleMap.OnMapClickListener {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private GoogleMap mMap;
    LatLng latLng;
    TextView tv_address;
    Button btn_ok;
    MyPrefs prefs;
    Geocoder geocoder;
    String address, lat_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        tv_address = findViewById(R.id.tv_address);
        btn_ok = findViewById(R.id.btn_done);
        prefs = new MyPrefs(this);

        address = getIntent().getStringExtra("address");
        lat_long = getIntent().getStringExtra("lat_long");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.this, "Location Updated", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    private void getCurrentLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            mMap = googleMap;
                            if (lat_long != null) {
                                String[] ltlng = prefs.get_Val(ConstVar.pref_LatLng).split(":");
                                latLng = new LatLng(Double.parseDouble(ltlng[0]), Double.parseDouble(ltlng[1]));
                            } else {
                                if (location != null) {
                                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                } else {
                                    latLng = new LatLng(25.072527, 67.2675832);
                                }
                            }

                            MarkerOptions options = new MarkerOptions().position(latLng).title("My Location");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(options);
                            mMap.setOnMapClickListener(MapActivity.this);

                            getAddress();
                            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                buildAlertMessageNoGps();
                            } else {
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                            }
                            googleMap.setMyLocationEnabled(true);
                        }
                    });
                }
            });
        } catch (
                Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        handleMapClick(latLng);
    }

    private void handleMapClick(LatLng latLng) {
        try {
            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            mMap.addMarker(markerOptions);
            getAddress();

        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void getAddress() {
        try {
            geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        ArrayList<ModelAddress> lst_address = new ArrayList<>();
        lst_address.add(new ModelAddress("" + tv_address.getText().toString().trim(),
                "" + latLng.latitude + ":" + latLng.longitude));
        GSON_Module gson = new GSON_Module();
        String val = gson._put_address(lst_address);
        Intent intent = new Intent();
        intent.putExtra("val", val);
        setResult(RESULT_OK, intent);
        new Anim().Back(this);
        finish();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }*/
}
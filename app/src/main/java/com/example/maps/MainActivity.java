package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView B1,B2,B3,B4, B5, B6;
        B1= findViewById(R.id.address);
        B2= findViewById(R.id.sublocal);
        B3= findViewById(R.id.speed);
        B4 = findViewById(R.id.altitude);
        B5= findViewById(R.id.latitude);
        B6=findViewById(R.id.longitude);


        Button m = findViewById(R.id.maps);


        int p = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(p!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
            {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
                Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lm == null)
                {
                    Toast.makeText(getApplicationContext(), "Location Denied", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        try
                        {
                            Geocoder geo;
                            geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = geo.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                            String locality = addresses.get(0).getLocality();
                            B1.setText(locality);
                            String subLocality=addresses.get(0).getSubLocality();
                            B2.setText((subLocality));
    //                            getSubLocality
                                //t2.setText();
                            B5.setText(""+addresses.get(0).getLatitude());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1
                            , new LocationListener()
                            {
                                @Override
                                public void onLocationChanged(@NonNull Location location) {
                                  //  Box1.setText(""+);
                                    B3.setText(""+location.getSpeed());
                                    B4.setText(""+location.getAltitude());
                                    B5.setText(""+location.getLatitude());
                                    B6.setText(""+location.getLongitude());

                                }
                            });
                }
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri u = Uri.parse("geo:" + B5.getText() + "," + B6.getText());
                Intent i = new Intent(Intent.ACTION_VIEW, u);
                startActivity(i);
            }
        });



    }

}
package com.example.ludwi.geotracker;

import android.Manifest;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener
{

    Button startStop;
    Button show;
    WaypointDatabase database;
    TextView anzeige;
    Location last;
    LocationManager lm;
    int PERMISSION_REQUEST_CODE;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.database = Room.databaseBuilder(this, WaypointDatabase.class, "WaypointDB")
                .allowMainThreadQueries()
                .build();

        startStop = findViewById(R.id.trackingstarten);

        startStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                start();
            }
        });

        show = findViewById(R.id.trackinganzeigen);

        show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);
            }
        });

        show.setEnabled(false);

        anzeige = findViewById(R.id.geoanzeige);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);



    }

    public void start()
    {
        startStop.setText("Tracking stoppen");
        tracking();

        startStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                stop();
            }
        });

    }

    public void stop()
    {
        startStop.setText("Tracking starten");
        anzeige.setText(" ");
        show.setEnabled(true);
        lm.removeUpdates(this);

        startStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                start();
                database.waypointDao().nukeTable();
            }
        });

    }



    public void tracking()
    {
            try {
                this.lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                this.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
                //this.last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (SecurityException e) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
    }



    @Override
    public void onLocationChanged(Location location)
    {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        anzeige.setText("Letzte Position:"+location.getLatitude()+" / "+location.getLongitude()+" ");

        Wegpunkt wegpunkt = new Wegpunkt();

        wegpunkt.setLatitude(lat);
        wegpunkt.setLongitude(lng);
        wegpunkt.setTimestamp(System.currentTimeMillis());

        database.waypointDao().insertWegpunkt(wegpunkt);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        switch (status)
        {
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
            case LocationProvider.OUT_OF_SERVICE:
            case LocationProvider.AVAILABLE:
        }
    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }
}


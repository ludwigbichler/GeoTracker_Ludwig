package com.example.ludwi.geotracker;

import android.arch.persistence.room.Room;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

public class MyLocationListener implements LocationListener
{

    @Override
    public void onLocationChanged(Location location)
    {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Wegpunkt wegpunkt = new Wegpunkt();

        wegpunkt.setLatitude(lat);
        wegpunkt.setLongitude(lng);
        wegpunkt.setTimestamp(System.currentTimeMillis());



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

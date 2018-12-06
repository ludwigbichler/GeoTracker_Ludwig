package com.example.ludwi.geotracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity
{

    MapView map = null;
    MapController mc;

    protected void onCreate(Bundle savedInstanceState)
    {
        WaypointDatabase database = Room.databaseBuilder(this, WaypointDatabase.class, "WaypointDB")
                .allowMainThreadQueries()
                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));




        map =  findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(19f);

        List<GeoPoint> geoPoints = new ArrayList<>();


        




//add your points here
        Polyline line = new Polyline();   //see note below!
        line.setPoints(geoPoints);
        line.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                Toast.makeText(mapView.getContext(), "polyline with " + polyline.getPoints().size() + "pts was tapped", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        map.getOverlayManager().add(line);
    }



    public boolean onCreateOptionsMenu(Menu backmenu)
    {
        MenuInflater inflaterback = getMenuInflater();
        inflaterback.inflate(R.menu.backmenu, backmenu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem createitem)
    {
        int id = createitem.getItemId();

        if (createitem.getItemId() == R.id.back)
        {
            startActivity(new Intent(this, MainActivity.class));

        }
        return false;
    }


}

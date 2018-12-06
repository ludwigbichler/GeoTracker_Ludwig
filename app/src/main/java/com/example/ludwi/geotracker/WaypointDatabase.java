package com.example.ludwi.geotracker;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

@Database(entities = {Wegpunkt.class}, version = 1, exportSchema = false)
public abstract class WaypointDatabase extends RoomDatabase
{
    public abstract WaypointDao waypointDao();
}
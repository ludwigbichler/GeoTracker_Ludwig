package com.example.ludwi.geotracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;


@Dao
public interface WaypointDao
{
    @Insert
    public void insertWegpunkt(Wegpunkt wegpunkt);

    @Query("SELECT * FROM wegpunkt ORDER BY timestamp")
    public List<Wegpunkt> getAllWaypoints();

    @Query("DELETE FROM wegpunkt")
    public void nukeTable();


}

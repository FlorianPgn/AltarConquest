package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag {
    private String name;
    private LatLng coordonees;

    public Flag(String name, LatLng coordonees){
        this.coordonees = coordonees;
    }
}

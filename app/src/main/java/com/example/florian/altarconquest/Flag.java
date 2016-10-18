package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag extends TeamComponent{
    private boolean capturable;
    public Flag(String name, LatLng coordonnees){
        super(name, coordonnees);
        capturable = false;
    }
}

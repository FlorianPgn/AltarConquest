package com.example.florian.altarconquest.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public class Altar {
    private LatLng coordonees;

    public Altar(LatLng coordonees){
        this.coordonees = coordonees;
    }

    public LatLng getCoordonees() {
        return coordonees;
    }
}

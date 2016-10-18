package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag extends TeamComponent{
    private boolean capturable;

    public Flag(String name, LatLng coordonnees, TeamColor teamColor){
        super(name, coordonnees, teamColor);
        capturable = false;
    }

    //Getters
    public boolean isCapturable() {
        return capturable;
    }

    //Setters
    public void setCapturable(boolean capturable) {
        this.capturable = capturable;
    }
}

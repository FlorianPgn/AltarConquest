package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag {
    private String name;
    private LatLng coordonnees;
    private boolean capturable = false;
    private TeamColor teamColor;

    public Flag(String name, LatLng coordonnees, TeamColor teamColor){
        this.name = name;
        this.coordonnees = coordonnees;
        this.teamColor = teamColor;
    }

    //Methods


    //Getters
    public String getName(){
        return name;
    }

    public LatLng getCoordonnees(){
        return coordonnees;
    }

    public boolean isCapturable(){
        return capturable;
    }

    public TeamColor getTeamColor(){
        return teamColor;
    }
}

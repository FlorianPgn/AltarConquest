package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag {
    private String name;
    private LatLng coordonees;
    private boolean capturable = false;
    private TeamColor teamColor;

    public Flag(String name, LatLng coordonees, TeamColor teamColor){
        this.name = name;
        this.coordonees = coordonees;
        this.teamColor = teamColor;
    }

    //Methods


    //Getters
    public String getName(){
        return name;
    }

    public LatLng getCoordonees(){
        return coordonees;
    }

    public boolean isCapturable(){
        return capturable;
    }

    public TeamColor getTeamColor(){
        return teamColor;
    }
}

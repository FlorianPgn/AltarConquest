package com.example.florian.altarconquest.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 13/10/2016.
 */

public class Flag {
    private String name;
    private LatLng coordonnees;
    private boolean capturable = false;
    private TeamColor teamColor;

    public Flag(String name, double latitude, double longitude, TeamColor teamColor){
        this.name = name;
        this.coordonnees = new LatLng(latitude, longitude);
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

    public String toString(){
        return name+" "+coordonnees.toString()+" "+teamColor;
    }

    public void setCoordonnees(LatLng coordonnees) {
        this.coordonnees = coordonnees;
    }

    public void setCapturable(boolean capturable) {
        this.capturable = capturable;
    }
}

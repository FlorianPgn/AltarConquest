package com.example.florian.altarconquest.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public class Base  {
    private String name;
    private LatLng coordonnees;
    private TeamColor teamColor;

    public Base(String name, LatLng coordonnees, TeamColor teamColor){
        this.name = name;
        this.coordonnees = coordonnees;
        this.teamColor = teamColor;
    }

    public void refillToken(Player player){
        player.setAttackTokenAvailable(true);
        player.setDefenseTokenAvailable(true);
    }

    public String getName(){
        return name;
    }
    public LatLng getCoordonnees() {
        return coordonnees;
    }

    public TeamColor getTeamColor(){
        return teamColor;
    }

}

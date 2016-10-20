package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public class Base  {
    private String name;
    private LatLng coordonnees;
    private TeamColor teamColor;

    public Base(String name, LatLng coordonees, TeamColor teamColor){
        this.name = name;
        this.coordonnees = coordonees;
        this.teamColor = teamColor;
    }

    public void refillToken(Joueur joueur){
        joueur.setAttackTokenAvailable(true);
        joueur.setDefenseTokenAvailable(true);
    }
}

package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public class Base extends TeamComponent {

    public Base(String name, LatLng coordonees, TeamColor teamColor){
        super(name, coordonees, teamColor);
    }

    public void refillToken(Joueur joueur){
        joueur.setAttackTockenAvailable(true);
        joueur.setDefenseTockenAvailable(true);
    }
}

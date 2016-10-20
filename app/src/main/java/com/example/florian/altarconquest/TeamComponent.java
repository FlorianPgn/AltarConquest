package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public abstract class TeamComponent {
    private String name;
    private LatLng coordonees;
    private TeamColor teamColor;

    public TeamComponent(String name, LatLng coordonees, TeamColor teamColor){
        this.name = name;
        this.coordonees = coordonees;
        this.teamColor = teamColor;
    }
}

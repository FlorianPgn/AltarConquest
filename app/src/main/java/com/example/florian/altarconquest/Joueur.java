package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 18/10/2016.
 */

public class Joueur {
    private String pseudo;
    private int score = 0;
    private LatLng coordonnees;
    private boolean attackTockenAvailable = false;
    private boolean defenseTockenAvailable = false;
    private TeamColor teamColor;
    private boolean havingFlag = false;
    private Flag stolenFlag;

    public Joueur(String pseudo, LatLng coordonnees, TeamColor teamColor){
        this.pseudo = pseudo;
        this.coordonnees = coordonnees;
        this.teamColor = teamColor;
    }

    public void setAttackTockenAvailable(boolean attackTockenAvailable) {
        this.attackTockenAvailable = attackTockenAvailable;
    }

    public void setDefenseTockenAvailable(boolean defenseTockenAvailable) {
        this.defenseTockenAvailable = defenseTockenAvailable;
    }
}

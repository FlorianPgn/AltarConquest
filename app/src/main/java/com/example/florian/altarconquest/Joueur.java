package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 14/10/2016.
 */

public class Joueur {
    private String pseudo;
    private int score;
    private LatLng coordonnees;
    private boolean attackTokenAvaiable = true;
    private boolean defenceTokenAvaiable = true;
    private TeamColor teamColor;
    private boolean holdingAFlag = false;
    private Flag stolenFlag;

    public Joueur(String pseudo, TeamColor teamColor){
        this.pseudo = pseudo;
        this.teamColor = teamColor;
    }


    //Methods


    //Getters
    public String getPseudo() {
        return pseudo;
    }

    public LatLng getCoordonnees() {
        return coordonnees;
    }

    public boolean isAttackTokenAvaiable() {
        return attackTokenAvaiable;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public boolean isDefenceTokenAvaiable() {
        return defenceTokenAvaiable;
    }

    public boolean isHoldingAFlag() {
        return holdingAFlag;
    }

    public Flag getStolenFlag() {
        return stolenFlag;
    }

    public int getScore() {
        return score;
    }

    //Setters
    public void setScore(int score) {
        this.score = score;
    }

}

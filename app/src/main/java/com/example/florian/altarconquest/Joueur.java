package com.example.florian.altarconquest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 14/10/2016.
 */

public class Joueur {
    private String pseudo;
    private int score;
    private LatLng coordonnees;
<<<<<<< HEAD
    private boolean attackTokenAvailable = true;
    private boolean defenseTokenAvailable = true;
=======
    private boolean attackTokenAvaiable = true;
    private boolean defenceTokenAvaiable = true;
>>>>>>> refs/remotes/origin/master
    private TeamColor teamColor;
    private boolean holdingAFlag = false;
    private Flag stolenFlag;

<<<<<<< HEAD
    public Joueur(String pseudo, LatLng coordonnees, TeamColor teamColor){
        this.pseudo = pseudo;
        this.coordonnees = coordonnees;
=======
    public Joueur(String pseudo, TeamColor teamColor){
        this.pseudo = pseudo;
>>>>>>> refs/remotes/origin/master
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

<<<<<<< HEAD
    public boolean isAttackTokenAvailable() {
        return attackTokenAvailable;
=======
    public boolean isAttackTokenAvaiable() {
        return attackTokenAvaiable;
>>>>>>> refs/remotes/origin/master
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

<<<<<<< HEAD
    public boolean isDefenceTokenAvailable() {
        return defenseTokenAvailable;
=======
    public boolean isDefenceTokenAvaiable() {
        return defenceTokenAvaiable;
>>>>>>> refs/remotes/origin/master
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

<<<<<<< HEAD
    public void setAttackTokenAvailable(boolean attackTokenAvailable) {
        this.attackTokenAvailable = attackTokenAvailable;
    }

    public void setDefenseTokenAvailable(boolean defenceTokenAvailable) {
        this.defenseTokenAvailable = defenceTokenAvailable;
    }
}
=======
}
>>>>>>> refs/remotes/origin/master

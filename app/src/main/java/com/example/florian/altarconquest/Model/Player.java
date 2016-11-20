package com.example.florian.altarconquest.Model;

import com.example.florian.altarconquest.View.EcranJeu;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Florian on 14/10/2016.
 */

public class Player {
    private String pseudo;
    private int score;
    private LatLng coordonnees;

    private boolean attackTokenAvailable = true;
    private boolean defenseTokenAvailable = true;
    private boolean holdingAFlag = false;
    private Flag stolenFlag;


    public Player(String pseudo, LatLng coordonnees){
        this.pseudo = pseudo;
        this.coordonnees = coordonnees;
    }


    //Methods

    //Getters
    public String getPseudo() {
        return pseudo;
    }

    public LatLng getCoordonnees() {
        return coordonnees;
    }


    public boolean isAttackTokenAvailable() {
        return attackTokenAvailable;
    }

    public boolean isDefenceTokenAvailable() {
        return defenseTokenAvailable;
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

    public void setAttackTokenAvailable(boolean attackTokenAvailable) {
        this.attackTokenAvailable = attackTokenAvailable;
        EcranJeu.setAttackToken(attackTokenAvailable); // Pour l'affichage des jetons
    }

    public void setDefenseTokenAvailable(boolean defenceTokenAvailable) {
        this.defenseTokenAvailable = defenceTokenAvailable;
        EcranJeu.setDefencetoken(defenceTokenAvailable); // Pour l'affichage des jetons
    }

}


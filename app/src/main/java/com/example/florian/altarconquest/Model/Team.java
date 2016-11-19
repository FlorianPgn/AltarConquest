package com.example.florian.altarconquest.Model;

import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Florian on 02/11/2016.
 */

public class Team {
    private TeamColor teamColor;
    private LinkedList<Player> listOfPlayers;
    private Base base;
    private LinkedList<Flag> listofFlags;

    public Team(TeamColor teamColor){
        this.teamColor = teamColor;
        listOfPlayers = new LinkedList<>();
        listofFlags = new LinkedList<>();
    }


    //Methods
    public void ajouterJoueur(Player player){
        listOfPlayers.add(player);
    }

    public void ajouterDrapeau(Flag flag){
        listofFlags.add(flag);
    }


    //Getters and Setters
    public TeamColor getTeamColor() {
        return teamColor;
    }

    public LinkedList<Player> getListeDesPlayers() {
        return listOfPlayers;
    }

    public LinkedList<Flag> getListofFlags() {
        return listofFlags;
    }

    public Base getBase() {
        return base;
    }
}

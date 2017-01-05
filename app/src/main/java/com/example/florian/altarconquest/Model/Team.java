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
    private int nbMaxJoueurs;

    public Team(TeamColor teamColor, int nbMaxJoueurs){
        this.teamColor = teamColor;
        this.nbMaxJoueurs = nbMaxJoueurs;
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

    public Player getJoueur(String pseudo) {
        Log.i("LOP :" + listOfPlayers, "#SWAG");
        for (Player player : listOfPlayers) {
            if (player.getPseudo().equals(pseudo))
                return player;
        }
        return null;
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

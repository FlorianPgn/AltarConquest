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
        for (Player player : listOfPlayers) {
            if (player.getPseudo().equals(pseudo))
                return player;
        }
        return null;
    }

    public Flag getFlag(String name) {
        for (Flag flag: getListofFlags()) {
            if (flag.getName().equals(name)) {
                return flag;
            }
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

    public void setBase(Base base) {
        this.base = base;
    }

    public int getScore() {
        int score = 0;
        for (Player player:listOfPlayers) {
            score+=player.getScore();
        }
        return score;
    }
}

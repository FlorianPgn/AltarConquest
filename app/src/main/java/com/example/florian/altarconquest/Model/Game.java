package com.example.florian.altarconquest.Model;

import android.util.Log;

import com.example.florian.altarconquest.ServerInteractions.ServeurReceptionFlags;
import com.example.florian.altarconquest.View.EcranJeu;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Florian on 02/11/2016.
 */

public class Game {
    private String name;
    private String password;
    private Team blueTeam;
    private Team redTeam;
    private int nbJoueurs;


    public Game(String name, int nbJoueurs){
        this.name = name;
        this.nbJoueurs = nbJoueurs;

        if (nbJoueurs%2 == 0) {
            blueTeam = new Team(TeamColor.BLUE, nbJoueurs / 2);
            redTeam = new Team(TeamColor.RED, nbJoueurs / 2);
        } else {
            blueTeam = new Team(TeamColor.BLUE, nbJoueurs / 2 + 1);
            redTeam = new Team(TeamColor.RED, nbJoueurs / 2);
        }

    }

    public Game(String name, int nbJoueurs, String password){
        this(name, nbJoueurs);
        this.password = password;

    }

    public void ajouterDrapeau(Flag flag){
        if(flag.getTeamColor() == TeamColor.BLUE)
            blueTeam.ajouterDrapeau(flag);
        if(flag.getTeamColor() == TeamColor.RED)
            redTeam.ajouterDrapeau(flag);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public String getPassword() {
        return password;
    }

    public Team getBlueTeam() {
        return blueTeam;
    }

    public Team getRedTeam() {
        return redTeam;
    }
}

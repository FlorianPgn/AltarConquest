package com.example.florian.altarconquest.Model;

import android.util.Log;

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

    private EcranJeu activity;

    public Game(String name, EcranJeu activity){
        this.name = name;
        this.activity = activity;
        blueTeam = new Team(TeamColor.BLUE);
        redTeam = new Team(TeamColor.RED);
    }

    public Game(String name, String password, EcranJeu activity){
        this(name, activity);
        this.password = password;
    }

    public void ajouterDrapeau(Flag flag){
        if(flag.getTeamColor() == TeamColor.BLUE)
            blueTeam.ajouterDrapeau(flag);
        if(flag.getTeamColor() == TeamColor.RED)
            redTeam.ajouterDrapeau(flag);
    }

    public void launchServerRequest(){
        Log.i("Début", "requete serveur");
        ServeurReceptionFlags srf = new ServeurReceptionFlags(this);
        srf.execute();
        Log.i("Fin", "requete serveur");
    }

    public void initialisationObjetsLocalises(){
        Log.i("Init", "Initialisation flags");


        for (Flag flag : blueTeam.getListofFlags()) {
            activity.mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }

        for (Flag flag : redTeam.getListofFlags()) {
            activity.mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }

        Log.i("Liste des drapeaux", ""+blueTeam.getListofFlags());
    }
}

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
    private int nbJoueurs;

    private EcranJeu ecranJeu;

    public Game(String name, int nbJoueurs, EcranJeu ecranJeu){
        this.name = name;
        this.nbJoueurs = nbJoueurs;
        this.ecranJeu = ecranJeu;

        if (nbJoueurs%2 == 0) {
            blueTeam = new Team(TeamColor.BLUE, nbJoueurs / 2);
            redTeam = new Team(TeamColor.RED, nbJoueurs / 2);
        } else {
            blueTeam = new Team(TeamColor.BLUE, nbJoueurs / 2 + 1);
            redTeam = new Team(TeamColor.RED, nbJoueurs / 2);
        }

    }

    public Game(String name, int nbJoueurs, String password, EcranJeu ecranJeu){
        this(name, nbJoueurs, ecranJeu);
        this.password = password;
        ServerSendGameProperties ssgp = new ServerSendGameProperties(ecranJeu);
        ssgp.execute(addQuote(name), addQuote(password), addQuote(String.valueOf(nbJoueurs)));

    }

    public String addQuote(String chaine){
        return "'"+chaine+"'";
    }

    public void ajouterDrapeau(Flag flag){
        if(flag.getTeamColor() == TeamColor.BLUE)
            blueTeam.ajouterDrapeau(flag);
        if(flag.getTeamColor() == TeamColor.RED)
            redTeam.ajouterDrapeau(flag);
    }

    public void launchServerRequest(){
        Log.i("Début", "requete serveur flags");
        ServeurReceptionFlags srf = new ServeurReceptionFlags(this);
        srf.execute();
    }

    public void initialisationObjetsLocalises(){
        Log.i("Init", "Initialisation flags");

        for (Flag flag : blueTeam.getListofFlags()) {
            ecranJeu.mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }

        for (Flag flag : redTeam.getListofFlags()) {
            ecranJeu.mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }

        Log.i("Liste des drapeaux b", ""+blueTeam.getListofFlags());
        Log.i("Liste des drapeaux r", ""+redTeam.getListofFlags());
    }
}

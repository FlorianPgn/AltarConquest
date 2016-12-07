package com.example.florian.altarconquest.Model;

/**
 * Created by Florian on 02/11/2016.
 */

public class Game {
    private int id;
    private String name;
    private String password;
    private Team blueTeam;
    private Team redTeam;
    private int nbJoueurs;
    private int nbJoueursMax;

    public Game(int id, String name, int nbJoueursMax){
        this.id = id;
        this.name = name;
        this.nbJoueursMax = nbJoueursMax;

        if (nbJoueursMax %2 == 0) {
            blueTeam = new Team(TeamColor.BLUE, nbJoueursMax / 2);
            redTeam = new Team(TeamColor.RED, nbJoueursMax / 2);
        } else {
            blueTeam = new Team(TeamColor.BLUE, nbJoueursMax / 2 + 1);
            redTeam = new Team(TeamColor.RED, nbJoueursMax / 2);
        }
    }

    public Game(int id, String name, int nbJoueursMax, String password){
        this(id, name, nbJoueursMax);
        this.password = password;

    }

    public void ajouterDrapeau(Flag flag){
        if(flag.getTeamColor() == TeamColor.BLUE)
            blueTeam.ajouterDrapeau(flag);
        if(flag.getTeamColor() == TeamColor.RED)
            redTeam.ajouterDrapeau(flag);
    }

    public int getId() { return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbJoueursMax() {
        return nbJoueursMax;
    }

    public String getPassword() {
        return password;
    }

    public int getNbJoueurs() { return nbJoueurs; }

    public Team getBlueTeam() {
        return blueTeam;
    }

    public Team getRedTeam() {
        return redTeam;
    }

    public void setNbJoueurs(int nbJoueurs) { this.nbJoueurs = nbJoueurs; }
}

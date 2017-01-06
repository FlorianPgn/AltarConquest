package com.example.florian.altarconquest.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

/**
 * Created by Florian on 02/11/2016.
 */

public class Game implements Parcelable {
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

    public Game(Parcel in) {
        id = in.readInt();
        name = in.readString();
        password = in.readString();
        nbJoueursMax = in.readInt();

        if (nbJoueursMax %2 == 0) {
            blueTeam = new Team(TeamColor.BLUE, nbJoueursMax / 2);
            redTeam = new Team(TeamColor.RED, nbJoueursMax / 2);
        } else {
            blueTeam = new Team(TeamColor.BLUE, nbJoueursMax / 2 + 1);
            redTeam = new Team(TeamColor.RED, nbJoueursMax / 2);
        }
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

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

    public Team getTeam(TeamColor teamColor) {
        if (teamColor.equals(TeamColor.BLUE)) {
            return blueTeam;
        } else {
            return redTeam;
        }
    }

    public void setNbJoueurs(int nbJoueurs) { this.nbJoueurs = nbJoueurs; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeInt(nbJoueursMax);
    }

    public void updatePlayersCoordinates(List<Player> listPlayer){
        if(getBlueTeam().getListeDesPlayers().size() == 0) {
            for (Player player : listPlayer) {
                if (player.getColor() == TeamColor.BLUE) {
                    getBlueTeam().ajouterJoueur(player);
                } else {
                    getRedTeam().ajouterJoueur(player);
                }
            }
        }

        for (Player player:listPlayer) {
            if (player.getColor() == TeamColor.BLUE) {
                for (Player inGamePlayer:getBlueTeam().getListeDesPlayers()) {
                    if(inGamePlayer.getPseudo().equals(player.getPseudo())) {
                        inGamePlayer.setCoordonnees(player.getCoordonnees());
                    }
                }
            } else {
                for (Player inGamePlayer:getRedTeam().getListeDesPlayers()) {
                    if(inGamePlayer.getPseudo().equals(player.getPseudo())) {
                        inGamePlayer.setCoordonnees(player.getCoordonnees());
                    }
                }
            }
        }
    }

    public void ajouterBase(Base base) {
        if(base.getTeamColor().equals(TeamColor.BLUE)){
            getBlueTeam().setBase(base);
        } else {
            getRedTeam().setBase(base);
        }
    }
}

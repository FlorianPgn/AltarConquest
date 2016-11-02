package com.example.florian.altarconquest;

/**
 * Created by Florian on 02/11/2016.
 */

public class Game {
    private String name;
    private String password;
    private Team blueTeam;
    private Team redTeam;

    public Game(String name){
        this.name = name;
    }

    public Game(String name, String password){
        this(name);
        this.password = password;
    }
}

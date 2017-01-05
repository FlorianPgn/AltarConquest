package com.example.florian.altarconquest.ServerInteractions;

import com.example.florian.altarconquest.Model.Game;

import java.net.HttpURLConnection;

/**
 * Created by Florian on 05/01/2017.
 */

public class ServerReceptionBasesPositions extends ServerReceptionData {

    private Game game;

    public ServerReceptionBasesPositions(Game game) {
        this.game = game;
    }

    @Override
    public String getScriptUrl() {
        return "altarconquest.hol.es/scripts/get_bases.php";
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {

    }

    @Override
    public void doWhenTaskIsDone(String s) {

    }
}

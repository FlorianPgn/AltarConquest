package com.example.florian.altarconquest.ServerInteractions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 07/12/2016.
 */

public class ServerSendPlayerColor extends ServerSendData {

    public ServerSendPlayerColor(){
        super();
    }


    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/send_player_color.php";
    }

    @Override
    public String encodeData() {
        // création des données POST qui doivent être passées en paramètre
        String pseudo = (String) params[0];
        String teamColor = (String) params[1];

        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
            data += "&"+URLEncoder.encode("teamColor", "UTF-8")+"="+URLEncoder.encode(teamColor,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
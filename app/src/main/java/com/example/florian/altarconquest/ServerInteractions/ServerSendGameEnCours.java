package com.example.florian.altarconquest.ServerInteractions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 10/01/2017.
 */

public class ServerSendGameEnCours extends ServerSendData {

    @Override
    public String getScriptName() {
        return "set_game_en_cours.php";
    }

    @Override
    public String encodeData() {
        // création des données POST qui doivent être passées en paramètre
        String gameId = (String) params[0];

        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}

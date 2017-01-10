package com.example.florian.altarconquest.ServerInteractions;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 10/01/2017.
 */

public class ServerSendPlayerScore extends ServerSendData {

    @Override
    public String getScriptName() {
        return "send_player_score.php";
    }

    @Override
    public String encodeData() {
        // création des données POST qui doivent être passées en paramètre
        String pseudo = (String) params[0];
        String score = (String) params[1];
        Log.i("score", "post execute"+score);

        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
            data += "&"+URLEncoder.encode("score", "UTF-8")+"="+URLEncoder.encode(score,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}

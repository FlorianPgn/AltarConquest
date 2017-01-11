package com.example.florian.altarconquest.ServerInteractions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 10/01/2017.
 */

public class ServerSendPlayerHoldAFlag extends ServerSendData {
    @Override
    public String getScriptName() {
        return "send_hold_a_flag.php";
    }

    @Override
    public String encodeData() {
        // création des données POST qui doivent être passées en paramètre
        String pseudo = (String) params[0];
        String gameId = (String) params[1];
        String holdAFLag = (String) params[2];
        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
            data += "&"+URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
            data += "&"+URLEncoder.encode("holdAFlag", "UTF-8")+"="+URLEncoder.encode(holdAFLag,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}

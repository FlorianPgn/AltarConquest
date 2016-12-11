package com.example.florian.altarconquest.ServerInteractions;


import com.example.florian.altarconquest.View.EcranCreation_Partie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Florian on 19/11/2016.
 */

public class ServerSendGameProperties extends ServerSendData{

    public ServerSendGameProperties(){
        super();
    }

    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/sendgameproperties.php";
    }

    @Override
    public String encodeData() {
        String name = (String) params[0];
        String password = (String) params[1];
        String nbJoueursMax = (String) params[2];

        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name,"UTF-8");
            data += "&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            data += "&"+URLEncoder.encode("nbJoueursMax", "UTF-8")+"="+URLEncoder.encode(nbJoueursMax,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
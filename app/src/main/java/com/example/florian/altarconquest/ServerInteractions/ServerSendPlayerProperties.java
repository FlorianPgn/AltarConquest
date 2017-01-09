package com.example.florian.altarconquest.ServerInteractions;

import android.os.AsyncTask;
import android.util.Log;

import com.example.florian.altarconquest.View.EcranCreation_Partie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Florian on 07/12/2016.
 */

public class ServerSendPlayerProperties extends ServerSendData {

    public ServerSendPlayerProperties(){
        super();
    }


    @Override
    public String getScriptName() {
        return "send_player_properties.php";
    }

    @Override
    public String encodeData() {
        // création des données POST qui doivent être passées en paramètre
        String pseudo = (String) params[0];
        String gameId = (String) params[1];

        // creation de data sous la forme name=partieDeFlo&password=secret&nbJoueur=9
        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
            data += "&"+URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
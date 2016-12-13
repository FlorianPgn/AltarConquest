package com.example.florian.altarconquest.ServerInteractions;

import android.content.Context;
import android.util.Log;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.ServerInteractions.Parsers.GameParser;
import com.example.florian.altarconquest.View.EcranRejoindre_Partie;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Florian on 09/12/2016.
 */

public abstract class ServerReceptionData extends android.os.AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        Log.i("Serveur", "reception données");
        // creation de la connection HTTP
        URL url = null;
        try {
            url = new URL(getScriptUrl());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addPostParameters(conn, params);

        // attraper et concatener la réponse du serveur en un block
        BufferedReader bufferReader = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            bufferReader = new BufferedReader((new InputStreamReader(conn.getInputStream())));
            while((line = bufferReader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        doWhenTaskIsDone(s);

        // affiche la réponse du serveur dans le LogCat
        Log.i("Fin", "requete serveur");
        Log.i("Retour serveur", "Message serveur reçu = " + s);

    }

    public abstract String getScriptUrl();

    public abstract void addPostParameters(HttpURLConnection conn, String... params);

    public abstract void doWhenTaskIsDone(String s);
}
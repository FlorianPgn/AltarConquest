package com.example.florian.altarconquest.ServerInteractions;

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
 * Created by Florian on 04/12/2016.
 */

public class ServerReceptionGamesProperties extends android.os.AsyncTask<String,Void,String> {
    private String s;
    private EcranRejoindre_Partie ecranRejoindre_partie;


    public ServerReceptionGamesProperties(EcranRejoindre_Partie ecranRejoindre_partie){
        this.ecranRejoindre_partie = ecranRejoindre_partie;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("Serveur", "reception données");
        // creation de la connection HTTP
        URL url = null;
        try {
            url = new URL("http://altarconquest.hol.es/scripts/get_games.php");

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

        // attraper et concatener la réponse du serveur en un block
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader((new InputStreamReader(conn.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
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

        try {
            GameParser gameParser = new GameParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Game> resultats = gameParser.parse(stream);

            ecranRejoindre_partie.generateListContent(resultats);


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // affiche la réponse du serveur dans le LogCat
        Log.i("Fin", "requete serveur flags");
        Log.i("retour serveur", "serveurComRecevoirMessage=" + s);

    }
}
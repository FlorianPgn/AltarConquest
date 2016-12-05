package com.example.florian.altarconquest.ServerInteractions;

import android.util.Log;

import com.example.florian.altarconquest.Model.Flag;
import com.example.florian.altarconquest.ServerInteractions.Parsers.FlagParser;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.View.EcranJeu;
import com.google.android.gms.maps.model.MarkerOptions;

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
 * Created by Florian on 08/11/2016.
 */

public class ServeurReceptionFlags extends android.os.AsyncTask<String,Void,String> {
    public Game game;
    private String s;
    private EcranJeu ecranJeu;

    public ServeurReceptionFlags(Game game, EcranJeu ecranJeu){
        this.game = game;
        this.ecranJeu = ecranJeu;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("Serveur", "reception données");
        // creation de la connection HTTP
        URL url = null;
        try {
            url = new URL("http://altarconquest.hol.es/scripts/getflags.php");

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
            FlagParser flagParser = new FlagParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Flag> resultats = flagParser.parse(stream);

            for (Flag flag: resultats) {
                game.ajouterDrapeau(flag);
            }

            ecranJeu.initialisationObjetsLocalises(game);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // affiche la réponse du serveur dans le LogCat
        Log.i("Fin", "requete serveur flags");
        Log.i("retour serveur", "serveurComRecevoirMessage=" + s);

    }

    public String getS(){
        return s;
    }
}

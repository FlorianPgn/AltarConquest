package com.example.florian.altarconquest.ServerInteractions; /**
 * Created by Hugo on 08/12/2016.
 */

import android.util.Log;

import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.ServerInteractions.Parsers.PlayerParser;
import com.example.florian.altarconquest.View.EcranLobby_Partie;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

public class ServerReceptionTeamColor extends android.os.AsyncTask<String,Void,String> {
    private String s;
    private EcranLobby_Partie ecranLobbyPartie;


    public ServerReceptionTeamColor(EcranLobby_Partie ecranLobbyPartie) {
        this.ecranLobbyPartie = ecranLobbyPartie;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("Serveur", "reception données");
        // creation de la connection HTTP
        URL url = null;
        try {
            url = new URL("http://altarconquest.hol.es/scripts/get_players.php");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String gameId = params[0];
        String data = null;
        try {
            data = URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // envoyer la requete HTTP par le bon stream et fermer la connection
        OutputStream os = null;
        try {
            os = conn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferWriter = null;
        try {
            bufferWriter = new BufferedWriter((new OutputStreamWriter(os, "UTF-8")));
            bufferWriter.write(data);
            bufferWriter.flush();
            bufferWriter.close();
            os.close();
            conn.connect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
            PlayerParser playerParser = new PlayerParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Player> resultats = playerParser.parse(stream);

            ecranLobbyPartie.generateListContent(resultats);


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
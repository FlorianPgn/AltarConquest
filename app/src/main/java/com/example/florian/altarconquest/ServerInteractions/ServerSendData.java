package com.example.florian.altarconquest.ServerInteractions;

import android.content.Context;
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
 * Created by Florian on 09/12/2016.
 */

public abstract class ServerSendData extends AsyncTask<String, Void, String> {

    private Context context;

    protected String[] params;

    /**
     * Fonction qui se connect au serveur pour recevoir
     * tous les messages dans la BD
     * @param params rien
     */
    @Override
    protected String doInBackground(String... params) {

        this.params = params;

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
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String data = encodeData();


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


    /**
     * fonction qui se déclenche automatiquement quand le serveur a répondu,
     * @param   s   la réponse du serveur
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // affiche la réponse du serveur dans le LogCat
        Log.i("Retour serveur", "Message retourné = " + s);
    }

    public abstract String getScriptUrl();

    public abstract String encodeData();
}

package com.example.florian.altarconquest.ServerInteractions;

    import android.os.AsyncTask;
    import android.util.Log;

    import com.example.florian.altarconquest.View.EcranCreation_Partie;

    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.io.OutputStreamWriter;
    import java.io.UnsupportedEncodingException;
    import java.net.URLEncoder;


    /**
     * Created by Florian on 19/11/2016.
     */

    public class ServerSendGameProperties extends AsyncTask<String, Void, String>{

        public EcranCreation_Partie context;

    public ServerSendGameProperties(EcranCreation_Partie pContext){
        context = pContext;
    }
    /**
     * Fonction qui se connect au serveur pour recevoir
     * tous les messages dans la BD
     * @param params rien
     */
    @Override
    protected String doInBackground(String... params) {

        // creation de la connection HTTP
        URL url = null;
        try {
            url = new URL("http://altarconquest.hol.es/scripts/sendgameproperties.php");
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

        // création des données POST qui doivent être passées en paramètre
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
        Log.i("retour serveur", "serveurComEnvoieMessage=" + s);
    }
}

package com.example.florian.altarconquest.ServerInteractions;

import android.util.Log;

import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.ServerInteractions.Parsers.HoldFlagParser;
import com.example.florian.altarconquest.View.EcranJeu;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Florian on 12/01/2017.
 */

public class ServerReceptionHoldAFlag extends ServerReceptionData {

    private EcranJeu context;
    private Player player;
    private String scanContent;

    public ServerReceptionHoldAFlag(EcranJeu context, Player player, String scanContent){
        this.context = context;
        this.player = player;
        this.scanContent = scanContent;
    }

    @Override
    public String getScriptName() {
        return "get_hold_a_flag.php";
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {
        String gameId = params[0];
        String pseudo = params[1];
        Log.i("hf", gameId+ "  "+ pseudo);
        String data = null;
        try {
            data = URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
            data += "&"+URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
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
    }

    @Override
    public void doWhenTaskIsDone(String s) {
        try {
            HoldFlagParser playerParser = new HoldFlagParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<String> resultats = playerParser.parse(stream);

            if (resultats.get(0).equals("1")) {
                context.scanBaseAvecDrapeau(player, scanContent);
            } else {

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

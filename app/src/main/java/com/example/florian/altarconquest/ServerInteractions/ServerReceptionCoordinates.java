package com.example.florian.altarconquest.ServerInteractions;

import android.content.Context;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.ServerInteractions.Parsers.PlayerParser;
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

/**
 * Created by Florian on 12/12/2016.
 */

public class ServerReceptionCoordinates extends ServerReceptionData {

    Game game;

    public ServerReceptionCoordinates(Game game) {
       this.game = game;
    }

    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/get_players.php";
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {
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
    }

    @Override
    public void doWhenTaskIsDone(String s) {
        PlayerParser parser = new PlayerParser();
        InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

        try {
            game.updatePlayersCoordinates(parser.parse(stream));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

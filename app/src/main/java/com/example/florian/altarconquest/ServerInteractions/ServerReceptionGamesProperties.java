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

public class ServerReceptionGamesProperties extends ServerReceptionData {
    private EcranRejoindre_Partie context;

    public ServerReceptionGamesProperties(EcranRejoindre_Partie context){
        this.context = context;
    }

    public String getScriptName() {
        return "get_games.php";
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {

    }

    public void doWhenTaskIsDone(String s) {
        try {
            GameParser gameParser = new GameParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Game> resultats = gameParser.parse(stream);

            context.generateListContent(resultats);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
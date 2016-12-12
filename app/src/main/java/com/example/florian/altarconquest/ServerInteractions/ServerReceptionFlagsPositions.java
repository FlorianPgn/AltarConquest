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

public class ServerReceptionFlagsPositions extends ServerReceptionData {
    public Game game;
    private EcranJeu ecranJeu;

    public ServerReceptionFlagsPositions(Game game, EcranJeu ecranJeu){
        super(ecranJeu);
        this.game = game;
        this.ecranJeu = ecranJeu;
    }

    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/getflags.php";
    }

    @Override
    public void doWhenTaskIsDone(String s) {
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
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {

    }
}

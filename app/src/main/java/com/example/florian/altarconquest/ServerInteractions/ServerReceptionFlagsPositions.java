package com.example.florian.altarconquest.ServerInteractions;

import com.example.florian.altarconquest.Model.Flag;
import com.example.florian.altarconquest.ServerInteractions.Parsers.FlagParser;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.View.EcranJeu;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Florian on 08/11/2016.
 */

public class ServerReceptionFlagsPositions extends ServerReceptionData {
    public Game game;
    private EcranJeu ecranJeu;

    public ServerReceptionFlagsPositions(Game game, EcranJeu ecranJeu){
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

            ecranJeu.afficherDrapeaux(game);

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

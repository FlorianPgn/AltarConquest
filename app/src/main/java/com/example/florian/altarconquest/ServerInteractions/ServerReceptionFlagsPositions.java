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
    private EcranJeu ecranJeu;

    public ServerReceptionFlagsPositions(EcranJeu ecranJeu){
        this.ecranJeu = ecranJeu;
    }

    @Override
    public String getScriptName() {
        return "getflags.php";
    }

    @Override
    public void doWhenTaskIsDone(String s) {
        try {
            FlagParser flagParser = new FlagParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Flag> resultats = flagParser.parse(stream);

            for (Flag flag: resultats) {
                ecranJeu.getGame().ajouterDrapeau(flag);
            }

            ecranJeu.afficherDrapeaux();

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

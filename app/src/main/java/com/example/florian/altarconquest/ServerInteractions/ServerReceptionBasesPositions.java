package com.example.florian.altarconquest.ServerInteractions;

import android.util.Log;

import com.example.florian.altarconquest.Model.Base;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.ServerInteractions.Parsers.BaseParser;
import com.example.florian.altarconquest.View.EcranJeu;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Florian on 05/01/2017.
 */

public class ServerReceptionBasesPositions extends ServerReceptionData {

    private EcranJeu ecranJeu;
    public ServerReceptionBasesPositions(EcranJeu ecranJeu) {
        this.ecranJeu = ecranJeu;
    }

    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/get_bases.php";
    }

    @Override
    public void addPostParameters(HttpURLConnection conn, String... params) {

    }

    @Override
    public void doWhenTaskIsDone(String s) {
        try {
            BaseParser baseParser = new BaseParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Base> resultats = baseParser.parse(stream);

            for (Base base: resultats) {
                Log.i("tcb", ""+base);
                ecranJeu.getGame().ajouterBase(base);
            }

            ecranJeu.afficherBases();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

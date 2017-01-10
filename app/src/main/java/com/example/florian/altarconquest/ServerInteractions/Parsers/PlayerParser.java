package com.example.florian.altarconquest.ServerInteractions.Parsers;

import android.util.Log;
import android.util.Xml;

import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.Model.TeamColor;
import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 08/12/2016.
 */

public class PlayerParser {

    /**
     * Cette fonction parse le contenu d'un fichier et retourne le résultat
     * @param   in  le fichier contenant le XML
     * @return  une liste d'objet de type Entry
     */
    public List<Player> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readData(parser);
        } finally {
            in.close();
        }
    }


    /**
     * Cette fonction parse le contenu d'une balise <entry> et retourne le résultat
     * @param   parser   le bout de XML
     * @return  une liste d'objet de type Entry
     */
    private List<Player> readData(XmlPullParser parser) throws XmlPullParserException, IOException {
        // création d'une nouvelle liste d'objets de type Entry
        // que l'obn va remplir avec le contenu du

        List<Player> entries = new ArrayList<>();

        // le XML doit commencer par "<data>"
        parser.require(XmlPullParser.START_TAG, null, "data");

        // tant que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            // si cette balise est un <entry>, extraire le contenu de cette balise avec readEntry()
            // et ajouter le nouvel object Entry dans la liste entries
            if (name.equals("player")) {
                entries.add(readEntry(parser));
                // sinon, sauter la balise
            } else {
                skip(parser);
            }
        }
        // retourner toute la liste des Entry, qui viennent d'être remplis
        return entries;
    }


    /**
     * Cette fonction parse le contenu d'une balise <entry>
     * Si elle rencontre une balise de type <nom> ou <message>, elle fait appel à readTag() pour extraire le contenu de la balise
     * Sinon, elle saute la balise avec skip()
     * @param   parser  le bout de XML
     * @return  un object de type Entry qui contient les données extrait du XML
     */
    private Player readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        // le XML doit commencer par "<flag>"
        parser.require(XmlPullParser.START_TAG, null, "player");
        String pseudo = null;
        String latitude = null;
        String longitude = null;
        String teamColor = null;
        String score = null;


        // tand que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();

            // si cette balise est un <nom>, extraire le nom
            switch (tag) {
                case "pseudo":
                    pseudo = readTag(parser, "pseudo");
                    break;
                case "latitude":
                    latitude = readTag(parser, "latitude");
                    break;
                case "longitude":
                    longitude = readTag(parser, "longitude");
                    break;
                case "teamColor":
                    teamColor = (readTag(parser, "teamColor"));
                    break;
                case "score":
                    score = (readTag(parser, "score"));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        // retourner une nouvelle Entry avec le nom et message extrait du

        if (teamColor.equals("rouge")) {
            Player player = new Player(pseudo, new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), TeamColor.RED);
            player.setScore(Integer.valueOf(score));
            return player;
        }
        else if (teamColor.equals("bleue")) {
            Player player = new Player(pseudo, new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), TeamColor.BLUE);
            player.setScore(Integer.valueOf(score));
            return player;
        }
        else {
            Player player = new Player(pseudo, new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), null);
            player.setScore(Integer.valueOf(score));
            return player;
        }
    }


    /**
     * Cette fonction extrait le contenu d'une balise avec le titre tag
     * @param   parser  le bout de XML
     * @param   tag  le titre de la balise (ex: "nom", "message")
     * @return  le contenu String de la balise
     */
    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        String result = "";
        // le XML doit commencer par "<tag>"
        parser.require(XmlPullParser.START_TAG, null, tag);
        // si l'élément suivant est un text, sauvegarder le text dans result et passer à l'élément suivant
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        // le XML doit finir par "</tag>"
        parser.require(XmlPullParser.END_TAG, null, tag);
        return result;
    }


    /**
     * Cette fonction saute une balise entière, y compris les autres balises qui sont à l'interieur
     * @param   parser  le bout de XML
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}

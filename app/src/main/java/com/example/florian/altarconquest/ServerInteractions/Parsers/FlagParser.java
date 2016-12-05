package com.example.florian.altarconquest.ServerInteractions.Parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.example.florian.altarconquest.Model.Flag;
import com.example.florian.altarconquest.Model.TeamColor;

public class FlagParser {


    /**
     * Cette fonction parse le contenu d'un fichier et retourne le résultat
     * @param   in  le fichier contenant le XML
     * @return  une liste d'objet de type Entry
     */
    public List<Flag> parse(InputStream in) throws XmlPullParserException, IOException {
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
    private List<Flag> readData(XmlPullParser parser) throws XmlPullParserException, IOException {
        // création d'une nouvelle liste d'objets de type Entry
        // que l'obn va remplir avec le contenu du


        List<Flag> entries = new ArrayList<>();

        // le XML doit commencer par "<data>"
        parser.require(XmlPullParser.START_TAG, null, "data");

        // tand que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            // si cette balise est un <entry>, extraire le contenu de cette balise avec readEntry()
            // et ajouter le nouvel object Entry dans la liste entries
            if (name.equals("flag")) {
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
    private Flag readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        // le XML doit commencer par "<flag>"
        parser.require(XmlPullParser.START_TAG, null, "flag");
        String nom = null;
        double latitude = 0;
        double longitude = 0;
        TeamColor couleur = null;

        // tand que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            // si cette balise est un <nom>, extraire le nom
            switch (name) {
                case "nom":
                    nom = readTag(parser, "nom");

                    //si cette balise est un <message>, extraire le message
                    break;
                case "latitude":
                    latitude = Double.parseDouble(readTag(parser, "latitude"));

                    // sinon, sauter la balise
                    break;
                case "longitude":
                    longitude = Double.parseDouble(readTag(parser, "longitude"));

                    // sinon, sauter la balise
                    break;
                case "couleur":
                    if(readTag(parser, "couleur").equals("bleue"))
                        couleur = TeamColor.BLUE;
                    else
                        couleur = TeamColor.RED;
                    // sinon, sauter la balise
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        // retourner une nouvelle Entry avec le nom et message extrait du
        return new Flag(nom, latitude, longitude, couleur);
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

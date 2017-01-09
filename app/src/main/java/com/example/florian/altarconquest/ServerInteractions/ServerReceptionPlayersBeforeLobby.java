package com.example.florian.altarconquest.ServerInteractions;

import com.example.florian.altarconquest.Controller.JoinGameListener;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.ServerInteractions.Parsers.PlayerParser;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionData;
import com.example.florian.altarconquest.View.EcranLobby_Partie;

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
import java.util.ArrayList;
import java.util.List;

public class ServerReceptionPlayersBeforeLobby extends ServerReceptionData {
    private JoinGameListener listener;


    public ServerReceptionPlayersBeforeLobby(JoinGameListener listener) {
        this.listener = listener;
    }

    @Override
    public String getScriptName() {
        return "get_players.php";
    }

    @Override
    public void doWhenTaskIsDone(String s) {
        try {
            PlayerParser playerParser = new PlayerParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));

            List<Player> resultats = playerParser.parse(stream);

            listener.updateListJoueurs(resultats);


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
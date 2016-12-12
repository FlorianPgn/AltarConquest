package com.example.florian.altarconquest.ServerInteractions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 12/12/2016.
 */

public class ServerSendDeletedPlayer extends ServerSendData {
    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/send_deleted_player.php";
    }

    @Override
    public String encodeData() {
        String pseudo = (String) params[0];

        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}

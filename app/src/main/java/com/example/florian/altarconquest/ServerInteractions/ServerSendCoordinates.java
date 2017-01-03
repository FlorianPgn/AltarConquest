package com.example.florian.altarconquest.ServerInteractions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Florian on 03/01/2017.
 */

public class ServerSendCoordinates extends ServerSendData {

    @Override
    public String getScriptUrl() {
        return "http://altarconquest.hol.es/scripts/send_coordinates.php";
    }

    @Override
    public String encodeData() {
        String pseudo = (String) params[0];
        String gameId = (String) params[1];
        String lat = (String) params[2];
        String lng = (String) params[3];

        String data = null;
        try {
            data = URLEncoder.encode("pseudo", "UTF-8")+"="+URLEncoder.encode(pseudo,"UTF-8");
            data += "&"+URLEncoder.encode("gameId", "UTF-8")+"="+URLEncoder.encode(gameId,"UTF-8");
            data += "&"+URLEncoder.encode("lat", "UTF-8")+"="+URLEncoder.encode(lat,"UTF-8");
            data += "&"+URLEncoder.encode("lng", "UTF-8")+"="+URLEncoder.encode(lng,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}

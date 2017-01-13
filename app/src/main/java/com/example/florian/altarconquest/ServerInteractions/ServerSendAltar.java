package com.example.florian.altarconquest.ServerInteractions;

/**
 * Created by Florian on 11/01/2017.
 */

public class ServerSendAltar extends ServerSendData {
    @Override
    public String getScriptName() {
        return "send_altar.php";
    }

    @Override
    public String encodeData() {
        return null;
    }
}

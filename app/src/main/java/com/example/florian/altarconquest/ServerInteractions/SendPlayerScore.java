package com.example.florian.altarconquest.ServerInteractions;

/**
 * Created by Florian on 10/01/2017.
 */

public class SendPlayerScore extends ServerSendData {
    @Override
    public String getScriptName() {
        return "send_player_score.php";
    }

    @Override
    public String encodeData() {
        return null;
    }
}

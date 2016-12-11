package com.example.florian.altarconquest.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.ServerInteractions.ServerSendPlayerProperties;
import com.example.florian.altarconquest.ServerInteractions.ServerSendTeamColorProperties;
import com.example.florian.altarconquest.View.EcranLobby_Partie;

/**
 * Created by Hugo on 11/12/2016.
 */

public class ChoixEquipeListener implements View.OnClickListener {

    private String player;
    private String color;

    public ChoixEquipeListener(String player, String color){
        this.player = player;
        this.color = color;
    }

    @Override
    public void onClick(View v)
    {
        if(player != "" && color != "")
        {
            ServerSendTeamColorProperties ssp = new ServerSendTeamColorProperties();
            ssp.execute(player, color);
        }
    }
}

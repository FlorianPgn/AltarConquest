package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.os.Bundle;

import com.example.florian.altarconquest.R;

/**
 * Created by Florian on 04/12/2016.
 */

public class EcranLobbyPartie extends Activity {

    private String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                player = null;
            } else {
                player = extras.getString("STRING_PSEUDO");
            }
        } else {
            player = (String) savedInstanceState.getSerializable("STRING_PSEUDO");
        }
    }
}

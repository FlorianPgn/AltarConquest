package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Controller.ChoixEquipeListener;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionGamesProperties;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Florian on 04/12/2016.
 * Edited by Hugo on 07/12/2016
 * Edited by Hugo on 08/12/2016
 */

public class EcranLobby_Partie extends Activity {

    public String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton bouton_red = (ImageButton) findViewById(R.id.bouton_red);
        ImageButton bouton_blue = (ImageButton) findViewById(R.id.bouton_blue);

        Timer timer =  new Timer();

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

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionPlayersProperties srpp = new ServerReceptionPlayersProperties(EcranLobby_Partie.this);
                srpp.execute();
            }
        };

        timer.schedule(timerTask, 0, 1000 * 4);

        bouton_retour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ouvrirRejoindrePartie();
            }
        });

        bouton_red.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ChoixEquipeListener(player, "rouge");
            }
        });

        bouton_blue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ChoixEquipeListener(player, "bleu");
            }
        });
    }

    public void ouvrirRejoindrePartie()
    {
        Intent intent = new Intent(this, EcranRejoindre_Partie.class);
        startActivity(intent);

    }

    public void generateListContent(List<Player> list) {

        Log.i("generate","");
        //instantiate custom adapter
        MyListPlayerAdapter adapter = new MyListPlayerAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.liste_joueurs);
        lView.setAdapter(adapter);
    }
}


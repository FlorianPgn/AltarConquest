package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Controller.ChoixEquipeListener;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersProperties;
import com.example.florian.altarconquest.ServerInteractions.ServerSendDeletedPlayer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Florian on 04/12/2016.
 * Edited by Hugo on 07/12/2016
 * Edited by Hugo on 08/12/2016
 */

public class EcranLobby_Partie extends Activity {

    private String pseudo;
    private String gameId;
    private String nbJoueursMax;
    List<String> listeJoueurs;

    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton bouton_red = (ImageButton) findViewById(R.id.bouton_red);
        ImageButton bouton_blue = (ImageButton) findViewById(R.id.bouton_blue);

        timer =  new Timer();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                pseudo = null;
                gameId = null;
                nbJoueursMax = null;
            } else {
                pseudo = extras.getString("STRING_PSEUDO");
                gameId = extras.getString("STRING_GAMEID");
                nbJoueursMax = extras.getString("STRING_JMAX");
            }
        } else {
            pseudo = (String) savedInstanceState.getSerializable("STRING_PSEUDO");
            gameId = (String) savedInstanceState.getSerializable("STRING_GAMEID");
            nbJoueursMax = (String) savedInstanceState.getSerializable("STRING_JMAX");
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionPlayersProperties srpp = new ServerReceptionPlayersProperties(EcranLobby_Partie.this);
                srpp.execute(gameId);
            }
        };

        timer.schedule(timerTask, 0, 500);

        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirRejoindrePartie();
            }
        });

        bouton_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChoixEquipeListener(pseudo, "rouge");
            }
        });

        bouton_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChoixEquipeListener(pseudo, "bleue");
            }
        });
    }

    public void ouvrirRejoindrePartie() {
        ServerSendDeletedPlayer ssdp = new ServerSendDeletedPlayer();
        ssdp.execute(pseudo);
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

        if(Integer.parseInt(nbJoueursMax) == list.size()) {
            Intent intent = new Intent(this, EcranJeu.class);
            intent.putExtra("STRING_PSEUDO", pseudo);
            intent.putExtra("STRING_GAMEID", gameId);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}


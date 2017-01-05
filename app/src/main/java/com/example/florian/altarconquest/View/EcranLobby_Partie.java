package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Controller.ChoixEquipeListener;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersLobby;
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

    Timer timer;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton bouton_red = (ImageButton) findViewById(R.id.bouton_red);
        ImageButton bouton_blue = (ImageButton) findViewById(R.id.bouton_blue);

        timer =  new Timer();

        Bundle bundle = getIntent().getExtras();
        game = bundle.getParcelable("game");
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                pseudo = null;
            } else {
                pseudo = extras.getString("STRING_PSEUDO");
            }
        } else {
            pseudo = (String) savedInstanceState.getSerializable("STRING_PSEUDO");
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionPlayersLobby srpp = new ServerReceptionPlayersLobby(EcranLobby_Partie.this);
                srpp.execute(String.valueOf(game.getId()));
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

    public void generateListContent(List<Player> listPlayer) {
        //instantiate custom adapter
        MyListPlayerAdapter adapter = new MyListPlayerAdapter(listPlayer, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.liste_joueurs);
        lView.setAdapter(adapter);

        if(game.getNbJoueursMax() == listPlayer.size()) {
            boolean colorsAreSet = true;
            TeamColor teamColor = null;
            for (Player player:listPlayer) {
                if (player.getColor() == null) {
                    colorsAreSet = false;
                }
                if (player.getPseudo().equals(pseudo)) {
                    teamColor = player.getColor();
                }
            }

            if (colorsAreSet) {
                timer.cancel();

                Intent intent = new Intent(this, EcranJeu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("game", game);
                intent.putExtra("STRING_PSEUDO", pseudo);
                intent.putExtra("STRING_COLOR", teamColor.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        ouvrirRejoindrePartie();
    }
}


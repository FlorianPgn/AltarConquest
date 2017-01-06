package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Controller.ChoixEquipeListener;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersProperties;
import com.example.florian.altarconquest.ServerInteractions.ServerSendDeletedPlayer;

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

    private String pseudo;
    private int nbJoueursEquipeRouge, nbJoueursEquipeBleu;
    boolean colorsAreSet = true;

    Timer timer;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton bouton_red = (ImageButton) findViewById(R.id.bouton_red);
        ImageButton bouton_blue = (ImageButton) findViewById(R.id.bouton_blue);

        final AlertDialog.Builder equipeNonEquilibreBuilder = new AlertDialog.Builder(EcranLobby_Partie.this);
        equipeNonEquilibreBuilder.setTitle("Les équipes ne sont pas équilibrés:");
        equipeNonEquilibreBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

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
                ServerReceptionPlayersProperties srpp = new ServerReceptionPlayersProperties(EcranLobby_Partie.this);
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

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!(nbJoueursEquipeRouge == game.getNbJoueursMax()/2
                                && nbJoueursEquipeBleu == game.getNbJoueursMax()/2) && colorsAreSet){
                            equipeNonEquilibreBuilder.show();
                        }
                    }
                }, 2000);
            }
        });

        bouton_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChoixEquipeListener(pseudo, "bleue");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!(nbJoueursEquipeRouge == game.getNbJoueursMax()/2
                                && nbJoueursEquipeBleu == game.getNbJoueursMax()/2) && colorsAreSet){
                            equipeNonEquilibreBuilder.show();
                        }
                    }
                }, 2000);
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

        nbJoueursEquipeBleu = 0;
        nbJoueursEquipeRouge = 0;

        if(game.getNbJoueursMax() == listPlayer.size()) {
            TeamColor teamColor = null;
            for (Player player:listPlayer) {
                if (player.getColor() == null) {
                    colorsAreSet = false;
                }
                if (player.getPseudo().equals(pseudo)) {
                    teamColor = player.getColor();
                }
               if(player.getColor() == teamColor.BLUE){
                    nbJoueursEquipeBleu++;
                }
                if(player.getColor() == teamColor.RED){
                    nbJoueursEquipeRouge++;
                }

            }

            if (colorsAreSet) {
               if(nbJoueursEquipeRouge == game.getNbJoueursMax()/2 && nbJoueursEquipeBleu == game.getNbJoueursMax()/2) {

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


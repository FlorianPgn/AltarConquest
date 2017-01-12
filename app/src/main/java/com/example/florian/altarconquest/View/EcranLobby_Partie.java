package com.example.florian.altarconquest.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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
import com.example.florian.altarconquest.ServerInteractions.ServerSendGameEnCours;

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
    TeamColor teamColor;
    Context context = this;

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

        timer = new Timer();

        //On récupère la game serializé
        Bundle extras = getIntent().getExtras();
        game = extras.getParcelable("game");

        //On récupère le pseudo du joueur
        if (savedInstanceState == null) {
            if(extras == null) {
                pseudo = null;
            } else {
                pseudo = extras.getString("STRING_PSEUDO");
            }
        } else {
            pseudo = (String) savedInstanceState.getSerializable("STRING_PSEUDO");
        }

        //Requête de la liste des joueurs de la partie toutes les 0.5 sec
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionPlayersLobby srpp = new ServerReceptionPlayersLobby(EcranLobby_Partie.this);
                srpp.execute(String.valueOf(game.getId()));
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 500);


        //Initialisation des listeners des boutons
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
        //Si on quitte le lobby on est supprimé de la partie en BDD
        timer.cancel();

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
        colorsAreSet = true;

        //Lorsque la partie est compléte
        if(game.getNbJoueursMax() == listPlayer.size()) {
            teamColor = null;

            //On vérifie que tous les joueurs ont choisis leur team
            for (Player player:listPlayer) {
                Log.i("TestCouleur", "if");
                if (player.getPseudo().equals(pseudo)) { //On récupère notre couleur de team
                    teamColor = player.getColor();
                }

                if (player.getColor() == null) {
                    colorsAreSet = false;
                }

                else if(player.getColor().equals(teamColor.BLUE)){
                    Log.i("TestCouleur", "bleu++");
                    nbJoueursEquipeBleu++;
                }

                else if(player.getColor().equals(teamColor.RED)){
                    Log.i("TestCouleur", "rouge++");
                    nbJoueursEquipeRouge++;
                }

            }
            if (colorsAreSet && nbJoueursEquipeRouge == game.getNbJoueursMax()/2 && nbJoueursEquipeBleu == game.getNbJoueursMax()/2) {

                //Passe à l'écran de jeu le pseudo et la couleur de team

                lancerPartie();
            }
        }
    }

    public void lancerPartie() {
        timer.cancel();  //On arrête les requêtes serveur pour avoir les joueurs du lobby

        //Serialize l'objet game
        Bundle bundle = new Bundle();
        bundle.putParcelable("game", game);

        ServerSendGameEnCours ssgec = new ServerSendGameEnCours();
        ssgec.execute(String.valueOf(game.getId()));

        //Passe à l'écran de jeu le pseudo et la couleur de team
        Intent intent = new Intent(this, EcranJeu.class);
        intent.putExtra("STRING_PSEUDO", pseudo);
        intent.putExtra("STRING_COLOR", teamColor.toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        ouvrirRejoindrePartie();
    }
}


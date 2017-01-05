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

    Timer timer;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_lobby_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton bouton_red = (ImageButton) findViewById(R.id.bouton_red);
        ImageButton bouton_blue = (ImageButton) findViewById(R.id.bouton_blue);

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
                ServerReceptionPlayersProperties srpp = new ServerReceptionPlayersProperties(EcranLobby_Partie.this);
                srpp.execute(String.valueOf(game.getId()));
            }
        };
        timer =  new Timer();
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
        //Si on quitte le lobby on est supprimé de la partie en BDD
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

        //Lorsque la partie est compléte
        if(game.getNbJoueursMax() == listPlayer.size()) {
            boolean colorsAreSet = true;
            TeamColor teamColor = null;

            //On vérifie que tous les joueurs ont choisis leur team
            for (Player player:listPlayer) {
                if (player.getColor() == null) {
                    colorsAreSet = false;
                }
                if (player.getPseudo().equals(pseudo)) { //On récupère notre couleur de team
                    teamColor = player.getColor();
                }
            }

            if (colorsAreSet) {
                timer.cancel(); //On arrête les requêtes serveur pour avoir les joueurs du lobby

                //Serialize l'objet game
                Bundle bundle = new Bundle();
                bundle.putParcelable("game", game);

                //Passe à l'écran de jeu le pseudo et la couleur de team
                Intent intent = new Intent(this, EcranJeu.class);
                intent.putExtra("STRING_PSEUDO", pseudo);
                intent.putExtra("STRING_COLOR", teamColor.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        ouvrirRejoindrePartie();
    }
}


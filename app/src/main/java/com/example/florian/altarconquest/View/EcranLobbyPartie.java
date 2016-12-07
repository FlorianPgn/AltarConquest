package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.R;

import java.util.ArrayList;
import java.util.List;

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

        ListView listView = (ListView) findViewById(R.id.liste_joueurs);
        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);

        List<String> listeJoueurs = new ArrayList<String>();
        listeJoueurs.add("Nenet the God");
        listeJoueurs.add("Izou");
        listeJoueurs.add("Mamie");
        listeJoueurs.add("Yannou");
        listeJoueurs.add("Lafo");
        listeJoueurs.add("Momo");
        listeJoueurs.add("Oubah Oubah");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeJoueurs);

        listView.setAdapter(arrayAdapter);

        bouton_retour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ouvrirRejoindrePartie();
            }
        });
    }

    public void ouvrirRejoindrePartie()
    {
        Intent intent = new Intent(this, EcranRejoindre_Partie.class);
        startActivity(intent);
    }
}

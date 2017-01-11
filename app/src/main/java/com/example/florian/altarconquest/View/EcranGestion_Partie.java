package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

public class EcranGestion_Partie extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirAccueil();
            }
        });

        ImageButton bouton_creation_partie = (ImageButton) findViewById(R.id.bouton_creer_partie);
        bouton_creation_partie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { ouvrirCreation_Partie();
            }
        });

        ImageButton bouton_rejoindre_partie = (ImageButton) findViewById(R.id.bouton_rejoindre_partie);
        bouton_rejoindre_partie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {ouvrirRejoindre_Partie();
            }
        });
    }

    public void ouvrirAccueil()
    {
        Intent intent = new Intent(this, EcranAccueil.class);
        startActivity(intent);
    }

    public void ouvrirCreation_Partie()
    {
        Intent intent = new Intent(this, EcranCreation_Partie.class);
        startActivity(intent);
    }

    public void ouvrirRejoindre_Partie()
    {
        Intent intent = new Intent(this, EcranRejoindre_Partie.class);
        startActivity(intent);
    }
}
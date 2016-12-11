package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

public class EcranAccueil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        ImageButton bouton_jouer = (ImageButton) findViewById(R.id.bouton_jouer);
        ImageButton bouton_regles = (ImageButton) findViewById(R.id.bouton_regles);
        bouton_jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirCreationPartie();
            }
        });

        bouton_regles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirRegles();
            }
        });

    }

    public void ouvrirCreationPartie() {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirRegles() {
        Intent intent = new Intent(this, EcranRegles.class);
        startActivity(intent);
    }
}

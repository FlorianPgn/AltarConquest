package com.example.florian.altarconquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EcranChoix_Equipe extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_equipe);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton button_red_team = (ImageButton) findViewById(R.id.bouton_equipe_rouge);
        ImageButton button_blue_team = (ImageButton) findViewById(R.id.bouton_equipe_bleue);

        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirCreation_Partie();
            }
        });

        button_red_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ouvrirEcranJeu();
            }
        });

        button_blue_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ouvrirEcranJeu();
            }
        });
    }

    public void ouvrirCreation_Partie() {
        Intent intent = new Intent(this, EcranCreation_Partie.class);
        startActivity(intent);
    }

    public void ouvrirEcranJeu()
    {
        Intent intent = new Intent(this, EcranJeu.class);
        startActivity(intent);
    }
}
package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

public class EcranChoix_Equipe extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_equipe);

        ImageButton button_retour = (ImageButton) findViewById(R.id.bouton_retour);
        ImageButton button_red_team = (ImageButton) findViewById(R.id.bouton_equipe_rouge);
        ImageButton button_blue_team = (ImageButton) findViewById(R.id.bouton_equipe_bleue);

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirRejoinre_Partie();
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

    public void ouvrirRejoinre_Partie() {
        Intent intent = new Intent(this, EcranRejoindre_Partie.class);
        startActivity(intent);
    }

    public void ouvrirEcranJeu()
    {
        Intent intent = new Intent(this, EcranJeu.class);
        startActivity(intent);
    }
}
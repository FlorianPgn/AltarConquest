package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

public class EcranRejoindre_Partie extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejoindre_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirGestion_Partie();
            }
        });

        ImageButton bouton_rejoindre_partie = (ImageButton) findViewById(R.id.bouton_rejoindre_partie);
        bouton_rejoindre_partie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirChoix_Equipe();
            }
        });
    }

    public void ouvrirGestion_Partie()
    {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirChoix_Equipe()
    {
        Intent intent = new Intent(this, EcranChoix_Equipe.class);
        startActivity(intent);
    }
}